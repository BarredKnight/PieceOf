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
import root.utils.TolerableHelper;
import root.utils.ViewHelper;
import root.view.HorizontalButtonStage;
import root.view.InputDialog;
import root.view.Preconfigurator;
import root.view.VerticalButtonStage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.CodeSource;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by andrew on 11/1/15.
 */
public class CLI extends Application{
    public static MyConfig config = new MyConfig(null, null, null, null, null);
    public static Stage primaryStage;
    public static Label statusLabel;
    public static TabPane tabPane;
    public static ToggleButton logButton;
    public static boolean consoleLog = true;
    public static boolean consoleExcep = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CLI.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e -> setPrimaryStageOnClose());

        statusLabel =  new Label("o.o >hello!");
        config = ConfigCarer.giveMeMyFuckingConfig();

        showMainStage(primaryStage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void showMainStage(Stage primaryStage){
        ArrayList<Tab> tabs = ViewHelper.getCurrentTabs(config);
        tabPane = new TabPane();
        tabPane.getTabs().addAll(tabs);

        Button newButton = new Button("New");
        Button openButton = new Button("Open");
        Button downButton = new Button("Download");
        Button upButton = new Button("Upload");
        Button extractButton = new Button("Extract");
        Button extractRepoButton = new Button("Extract repo");
        logButton = new ToggleButton("On");
        logButton.setSelected(true);

        logButton.setOnAction( e -> logButtonCall());
        newButton.setOnAction( e -> newButtonCall());
        openButton.setOnAction( e -> openButtonCall());
        upButton.setOnAction( e -> upButtonCall());
        extractButton.setOnAction( e -> VerticalButtonStage.giveMeButtonsForExtract());

        SplitPane layout = new SplitPane();

        layout.setMinSize(500, 300);
        layout.setOrientation(Orientation.HORIZONTAL);
        primaryStage.setMinWidth(600);

        HBox hBox1 = new HBox(newButton, openButton);
        HBox hBox2 = new HBox(upButton, downButton);
        VBox vBox1 = new VBox(hBox1, hBox2, new HBox(extractButton, extractRepoButton), CLI.statusLabel, logButton);
        vBox1.maxWidth(200);
        primaryStage.setMinWidth(900);
        layout.getItems().addAll(tabPane, vBox1);
        layout.getItems().get(0).maxWidth(200); //doesn't work

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
        printIntoGUI(message);
        showMainStage(primaryStage);
    }

    public static void refreshMainStageWithExplanation(Exception e, boolean GUI, String pre){
        if (CLI.consoleExcep)
        e.printStackTrace();

        if (GUI)
        printIntoGUI(" OnO >" + pre + " - was some errors");
        CLI.primaryStage.close();
        showMainStage(primaryStage);
    }

    public static void printIntoConsole(String message){
        LocalDateTime dataTime = LocalDateTime.now();
        System.out.println(dataTime.getMinute() + ":" + dataTime.getSecond() + ">" + message);
    }

    public static void printIntoGUI(String message){
        LocalDateTime dataTime = LocalDateTime.now();
        CLI.statusLabel.setText(dataTime.getMinute() + ":" + dataTime.getSecond() + " " + message);
    }

    private static void setPrimaryStageOnClose(){
        try {
            ConfigCarer.saveConf(config);
        } catch (UnsupportedEncodingException e1) {
            printIntoConsole("Error while saving config");
        }
    }

    private static void newButtonCall(){
        String incorrectName = InputDialog.call("Name of new directory");
        String repoName = TolerableHelper.getRight(incorrectName);
        if (repoName != null && !repoName.equals("")){
            GitHub gitHub = null;
            try {
                gitHub = GitHub.connectUsingPassword(config.userLogin, config.password);

                boolean contains = false;
                try {
                    for (GHRepository currentRepo : gitHub.getMyself().getAllRepositories().values()){
                        if (currentRepo.getName().equals(repoName)){
                            contains = true;
                        }
                        System.out.println(currentRepo.getName());  // for tests. THERE
                    }

                    if (!contains){
                        try {
                            RepoMaster.createRepo(incorrectName, gitHub, config);
                            config.openedRepos.add(incorrectName);
                            refreshMainStage("New created.");
                        } catch (IOException e1) {
                            printIntoConsole("Can't create repo.");
                            refreshMainStageWithExplanation(e1, true, "Creating repo");
                        }
                    }

                } catch (IOException e1) {
                    printIntoConsole("Can't login.");
                    refreshMainStageWithExplanation(e1, true, "Login");
                } catch (NullPointerException e1){

                }
            } catch (IOException e1) {
                printIntoConsole("Can't login.");
                refreshMainStageWithExplanation(e1, true, "Login");
            }


        }
    }

    private static void upButtonCall(){

        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        GitHub gitHub = null;
        GHRepository repo;

        try {
            gitHub = GitHub.connectUsingPassword(config.userLogin, config.password);
        } catch (Exception e1) {
            printIntoConsole("Can't login.");
            refreshMainStageWithExplanation(e1, true, "Login");
        }

        try {
            repo = gitHub.getRepository(config.userLogin + "/" + selectedTab.getText());
            HorizontalButtonStage.getButtonsForUploadingChoice("How will be uploading ?", config, repo);
        } catch (IOException e1) {
            try {
                RepoMaster.createRepo(selectedTab.getText(), gitHub, config);
                upButtonCall();
            }catch (UnknownHostException e2){
                printIntoConsole("Can't login.");
                refreshMainStageWithExplanation(e2, true, "Login");
            }catch (IOException e2) {
                printIntoConsole("Exception during creating repo from tab.");
                refreshMainStageWithExplanation(e2, true, "Creating new repo");
            }
        }
    }

    private static void logButtonCall(){
        CLI.consoleLog = !CLI.consoleLog;
        if (logButton.getText().equals("On")){
            logButton.setText("Off");
        }else
            logButton.setText("On");
    }

    private static void openButtonCall(){
        VerticalButtonStage.giveMeButtonsForOpen("Choose repo to open", config);
    }

    private static String getParentDirectoryOfClass(){
        CodeSource codeSource = CLI.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            printIntoConsole("Exception while getting parent directory");
            refreshMainStageWithExplanation(e, true, "Getting parent directory");
        }
        return jarFile.getParentFile().getPath();
    }


    }