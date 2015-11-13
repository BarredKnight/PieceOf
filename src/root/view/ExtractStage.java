package root.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import root.CLI;
import root.utils.LocalLoader;
import root.utils.TolerableHelper;

import java.io.File;
import java.io.IOException;

/**
 * Created by andrew on 11/8/15.
 */
public class ExtractStage {

    private static TextField fromField = new TextField("Path to extracting file");
    private static TextField toField = new TextField("Path to destination");
    private static TextField nameOfTheRepo = new TextField("");
    private static Button openFromChooser = new Button("...");
    private static Button openToChooser = new Button("...");
    private static FileChooser fromFileChooser = new FileChooser();
    private static DirectoryChooser fromDirChooser = new DirectoryChooser();
    private static DirectoryChooser toChooser = new DirectoryChooser();
    private static Label statusLabel = new Label("Status: choose paths");
    private static Button okButton = new Button("Ok");
    private static Button cancelButton = new Button("Cancel");
    private static ToggleButton inclusiveButton = new ToggleButton("With root");
    private static ToggleButton extractRepo = new ToggleButton("Whole repo");

    private static HBox hBox1 = new HBox(fromField, openFromChooser);
    private static HBox hBox2 = new HBox(toField, openToChooser);
    private static HBox hBox3 = new HBox(statusLabel, okButton, cancelButton, extractRepo);
    private static VBox vBox = new VBox(hBox1, hBox2, hBox3);

    private static Scene scene = new Scene(vBox);
    private static Stage stage = new Stage();


    static {
        fromField.setMinWidth(500);
        toField.setMinWidth(500);
        inclusiveButton.setSelected(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

    }
    public static void callForFile(){
        File currentRepo = new File(CLI.config.wayToRepos + "/" + CLI.tabPane.getSelectionModel().getSelectedItem().getText());
        toChooser.setInitialDirectory(currentRepo);
        toField.setText(currentRepo.getAbsolutePath());
        stage.setTitle("Extracting file");
        File directory = getDirectory(new File(fromField.getText()));

        if (directory != null)
        fromFileChooser.setInitialDirectory(directory);
        fromFileChooser.setTitle("File to be extracted");

        openFromChooser.setOnAction( e -> {
            File fileFromChooser = fromFileChooser.showOpenDialog(new Stage());
            fromField.setText(fileFromChooser.getAbsolutePath());

            File correctFile = substitude(toField.getText(), fileFromChooser.getName());
            toField.setText(correctFile.getAbsolutePath());
        });
        openToChooser.setOnAction( e -> {
            File fileFromChooser = toChooser.showDialog(new Stage());
            File correctFile = substitude(fileFromChooser.getAbsolutePath(), new File(fromField.getText()).getName());
            toField.setText(correctFile.getAbsolutePath());
        });
        okButton.setOnAction( e -> {
            try {
                LocalLoader.loadOne(new File(fromField.getText()), new File(toField.getText()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.close();
        });
        cancelButton.setOnAction( e -> stage.close());
        stage.showAndWait();
    }

    public  static void callForDir(){
        File currentRepo = new File(CLI.config.wayToRepos + "/" + CLI.tabPane.getSelectionModel().getSelectedItem().getText());
        toChooser.setInitialDirectory(currentRepo);
        toField.setText(currentRepo.getAbsolutePath());
        stage.setTitle("Extracting directory");
        File directory = getDirectory(new File(fromField.getText()));

        if (directory != null)
            fromDirChooser.setInitialDirectory(directory);
        fromDirChooser.setTitle("Dir to be extracted");

        openFromChooser.setOnAction( e -> {
            File dirFromChooser = fromDirChooser.showDialog(new Stage());
            fromField.setText(dirFromChooser.getAbsolutePath());
            if (extractRepo.isSelected()){
                nameOfTheRepo.setText(getRightNameOfFutureRepo());
            }
            File correctDir = substitude(toField.getText(), dirFromChooser.getName());
            toField.setText(correctDir.getAbsolutePath());
        });
        openToChooser.setOnAction( e ->{
            File dirFromChooser = toChooser.showDialog(new Stage());
            File correctFile = substitude(dirFromChooser.getAbsolutePath(), new File(fromField.getText()).getName());
            toField.setText(correctFile.getAbsolutePath());
        });

        extractRepo.setOnAction( e -> {
            if (!extractRepo.isSelected()){
                makeExtractWindow();
            }else{
                makeRepoExtractWindow();
            }

        });
        okButton.setOnAction( e -> {
            try {
                LocalLoader.loadMany(new File(fromField.getText()), new File(toField.getText()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            CLI.refreshMainStage("Extracted successfully.");
            stage.close();
        });


        cancelButton.setOnAction( e -> stage.close());
        stage.showAndWait();
    }

    private static File getDirectory(File file){
        if (file.isDirectory())
            return file;
        else if (file.isFile()){
                File out = new File(file.getParent());
            return out;
            }
        return null;
    }

    private static File substitude(String fullPath, String incorrectName){
        String correctName = TolerableHelper.convertToCorrectName(TolerableHelper.transliterate(incorrectName));
        String correctPath = getDirectory(new File(fullPath)) + "/" + correctName;
        return new File(correctPath);
    }

    private static void makeRepoExtractWindow(){    //We've done it
        nameOfTheRepo.setText(getRightNameOfFutureRepo());
        vBox = new VBox(hBox1, nameOfTheRepo, hBox3);
        stage.close();
        stage = new Stage();
        refreshGUI();
        callForDir();
    }

    private static void makeExtractWindow(){
        vBox = new VBox(hBox1, new HBox(toField, openToChooser), hBox3);
        stage.close();
        refreshGUI();
        callForDir();
    }

    private static void refreshGUI(){
        scene = new Scene(vBox);
        stage.setScene(scene);
    }

    private static String getRightNameOfFutureRepo(){
        File sourcePath = new File(fromField.getText());
        if (sourcePath.exists()) {
            String incorrectName = sourcePath.getName();
            String correctName = TolerableHelper.getRight(incorrectName);
            return correctName;
        }
        return null;
    }
}


