package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.kohsuke.github.GitHub;
import root.MyConfig;

import java.io.File;
import java.io.IOException;

public class Preconfigurator {

    public static boolean preconfigure(MyConfig config){
        choosePlaceForRepos(config);
        enterLoginAndPass(config);

        return true;
    }   // OK

    public static boolean lazyCheck(MyConfig config){
        if (config.userLogin == null || config.password == null || config.username == null || config.wayToRepos == null){
            return false;
        }
        return true;
    }   // OK

    private static void choosePlaceForRepos(MyConfig config){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(new Stage());
        config.wayToRepos = file.getAbsolutePath();
    } // OK

    private static void enterLoginAndPass(MyConfig config){
        Stage stage = new Stage();
        Scene scene;

        TextField loginField = new TextField();
        TextField passField = new TextField();
        Label status = new Label("be like home");

        Button buttonA = new Button("Let me in!");
        buttonA.setOnAction( e -> {
            config.userLogin = loginField.getText();
            config.password = passField.getText();
            try {
                if((GitHub.connectUsingPassword(config.userLogin, config.password)).isCredentialValid()){
                    status.setText("ok");
                    stage.close();
                    setUsername(config);
                }else{
                    status.setText("Sorry, try again.");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        VBox vBox = new VBox(new Label("Login:"), loginField, new Label("Password:"), passField, buttonA, status);
        scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }   // OK

    private static void setUsername(MyConfig config){
        try {
            GitHub github = GitHub.connectUsingPassword(config.userLogin, config.password);
            config.username = github.getMyself().getLogin();
            System.out.println(config.username); // trash
        } catch (IOException e) {
            e.printStackTrace();
        }

    }    // OK
}
