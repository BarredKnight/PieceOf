import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kohsuke.github.GHRepository;
import root.MyConfig;
import root.load.RepoMaster;
import root.utils.ConfigCarer;
import root.utils.TreeHelper;
import view.Preconfigurator;

import java.io.File;
import java.security.CodeSource;

/**
 * Created by andrew on 11/1/15.
 */
public class CLI extends Application{
    MyConfig config = new MyConfig(null, null, null, null, null);

    @Override
    public void start(Stage primaryStage) throws Exception {
        CodeSource codeSource = CLI.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();
        System.out.println(jarDir);
        config = ConfigCarer.loadConf();    //
        if (!Preconfigurator.lazyCheck(config)) {
            Preconfigurator.preconfigure(config);
            ConfigCarer.saveConf(config);
        }

        showMainStage(primaryStage);
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

        GridPane pan = new GridPane();

        // making buttons
        Button newButton = new Button("New");
        Button newLocalButton = new Button("New local");
        Button openButton = new Button("Open");
        Button downButton = new Button("Download");
        Button upButton = new Button("Upload");
        Label statusLabel = new Label("...");


        newLocalButton.setOnAction( e ->{

        };
        newButton.setOnAction( e -> {

//            GHRepository createdRepo = RepoMaster.createRepo()
        });

        upButton.setOnAction( e -> {

        };

        HBox hBox = new HBox(new Button("New local"), new Button("Open local"), new Button("Download"));
        layout.getItems().addAll(tabPane, new VBox(hBox, new Button("Upload"), new Label("here")));

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
