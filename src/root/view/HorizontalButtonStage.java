package root.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import root.CLI;
import root.MyConfig;
import root.load.Uploader;
import root.utils.CompareHelper;
import root.utils.MixHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andrew on 11/4/15.
 */
public class HorizontalButtonStage {

    public static void call(Stage stage, String title, ArrayList<Button> buttons){
        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttons);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle(title);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public static void getButtonsForUploadingChoice(String title, MyConfig config, GHRepository repo){
        Stage stage = new Stage();
        stage.setTitle(title);
        Uploader uploader = new Uploader(config, repo);

        Button softUploadButton = new Button("Just upload");
        Button rudeUploadButton = new Button("Upload and delete trash");

        softUploadButton.setOnAction( e -> {
            try {
                uploader.uploadSoft();
                CLI.statusLabel.setText("o.o >Upload just completed");
            } catch (IOException e1) {
                CLI.refreshMainStageWithExplanation();
            }
            stage.close();

        });
        rudeUploadButton.setOnAction( e -> {
            try {
                uploader.uploadRude();
                CLI.statusLabel.setText("o.o >Upload with deleting completed");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.close();

        });

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(softUploadButton);
        buttons.add(rudeUploadButton);

        call(stage, title, buttons);
    }

}
