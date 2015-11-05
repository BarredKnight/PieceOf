package root;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import root.load.RepoMaster;
import root.utils.ConfigCarer;
import root.utils.ViewHelper;
import root.view.HorizontalButtonStage;
import root.view.InputDialog;
import root.view.Preconfigurator;
import root.view.VerticalButtonStage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.CodeSource;
import java.util.ArrayList;

/**
 * Created by andrew on 11/1/15.
 */
public class CLI extends Application{
    static MyConfig config = new MyConfig(null, null, null, null, null);
    static Stage primaryStage;
    public static Label statusLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CLI.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            try {
                ConfigCarer.saveConf(config);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        });
        statusLabel = new Label("o.o >hello!");

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
        ArrayList<Tab> tabs = ViewHelper.getCurrentTabs(config);
        TabPane tabPane = new TabPane();    // use it when adding new repo
        tabPane.getTabs().addAll(tabs);

        primaryStage.setMinWidth(600);
        SplitPane layout = new SplitPane();
        layout.setMinSize(500, 300);
        // and another
        layout.setOrientation(Orientation.HORIZONTAL);

        // making buttons
        Button newButton = new Button("New");
        Button newLocalButton = new Button("New local");
        Button openButton = new Button("Open");
        Button downButton = new Button("Download");
        Button upButton = new Button("Upload");

        newLocalButton.setOnAction( e ->{
            String repoName = InputDialog.call("Name of new local directory");
            if (repoName != null){
                RepoMaster.createLocal(repoName, config);
                config.openedRepos.add(repoName);
            }
        }); // seems ok

        newButton.setOnAction( e -> {
            String repoName = InputDialog.call("Name of new directory");
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

        openButton.setOnAction( e -> {
            VerticalButtonStage.giveMeButtonsForOpen("Choose repo to open", config);
        });

        upButton.setOnAction( e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            GHRepository repo = null;
            try {
                repo = GitHub.connectUsingPassword(config.userLogin, config.password).getRepository(config.userLogin + "/" +selectedTab.getText());
            } catch (IOException e1) {
                CLI.refreshMainStageWithExplanation();
            }
            HorizontalButtonStage.getButtonsForUploadingChoice("How will be uploading ?", config, repo);
        });

        HBox hBox1 = new HBox(newButton, openButton);
        HBox hBox2 = new HBox(upButton, downButton);
        layout.getItems().addAll(tabPane, new VBox(hBox1, hBox2, CLI.statusLabel));

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void refreshMainStage(){
        CLI.primaryStage.close();
        showMainStage(primaryStage);
    }

    public static void refreshMainStage(String message){
        CLI.primaryStage.close();
        CLI.statusLabel.setText(message);
        showMainStage(primaryStage);
    }

    public static void refreshMainStageWithExplanation(){
        CLI.primaryStage.close();
        CLI.statusLabel.setText("OnO >There was an unexpected error and that's serious. You can't do that");
        showMainStage(primaryStage);
    }




}
