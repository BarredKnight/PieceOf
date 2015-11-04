import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import root.MyConfig;
import root.load.RepoMaster;
import root.utils.ConfigCarer;
import root.utils.TreeHelper;
import view.InputDialog;
import view.Preconfigurator;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;

/**
 * Created by andrew on 11/1/15.
 */
public class CLI extends Application{
    static MyConfig config = new MyConfig(null, null, null, null, null);

    @Override
    public void start(Stage primaryStage) throws Exception {
        showMainStage(primaryStage);

        CodeSource codeSource = CLI.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();
        System.out.println(jarDir);
        config = ConfigCarer.loadConf();    //
        if (!Preconfigurator.lazyCheck(config)) {
            Preconfigurator.preconfigure(config);
            ConfigCarer.saveConf(config);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void showMainStage(Stage primaryStage){
        TreeView<Button> treeView = TreeHelper.getButtonTree("/home/andrew/ForTests", "new-repository-1");

        SplitPane layout = new SplitPane();

        TabPane tabPane = new TabPane(new Tab("First"), new Tab("Second"));
        tabPane.getTabs().get(0).setContent(treeView);

        // and another
        layout.setOrientation(Orientation.HORIZONTAL);

        // making buttons
        Button newButton = new Button("New");
        Button newLocalButton = new Button("New local");
        Button openButton = new Button("Open");
        Button downButton = new Button("Download");
        Button upButton = new Button("Upload");
        Label statusLabel = new Label("...");


        newLocalButton.setOnAction( e ->{
            String repoName = InputDialog.call();
            if (repoName != null){
                RepoMaster.createLocal(repoName, config);
                config.openedRepos.add(repoName);
            }
        }); // seems ok

        newButton.setOnAction( e -> {
            String repoName = InputDialog.call();
            if (repoName != null && !repoName.equals("")){
                GitHub gitHub = null;
                try {
                    gitHub = GitHub.connectUsingPassword(config.userLogin, config.password);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                boolean contains = false;
                try {
                    for (GHRepository currentRepo : gitHub.getMyself().getAllRepositories().values()){
                        if (currentRepo.getName().equals(repoName)){
                            contains = true;
                        }
                        System.out.println(currentRepo.getName());  // for tests
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (!contains){
                    try {
                        RepoMaster.createRepo(repoName, gitHub, config);
                        config.openedRepos.add(repoName);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }); // seems ok

        upButton.setOnAction( e -> {

        });

        HBox hBox1 = new HBox(newLocalButton, newButton);
        HBox hBox2 = new HBox(upButton, downButton);
        layout.getItems().addAll(tabPane, new VBox(hBox1, hBox2, openButton, statusLabel));

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




}
