package root.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import root.CLI;
import root.MyConfig;
import root.utils.TolerableHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by andrew on 11/4/15.
 */
public class VerticalButtonStage {

    public static void call(Stage stage, String title, ArrayList<Button> buttons){
        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttons);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle(title);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public static void giveMeButtonsForOpen(String title, MyConfig config){
        ArrayList<File> localRepos = TolerableHelper.getLocalRepos(config);
        ArrayList<Button> buttons = new ArrayList<>();
        Stage stage = new Stage();

        for (File localRepo : localRepos){
            Button button = new Button(localRepo.getName());
            button.setOnAction( e -> {
                if (!config.openedRepos.contains(localRepo.getName())){
                    config.openedRepos.add(localRepo.getName());
                    stage.close();
                    CLI.refreshMainStage();
                    CLI.statusLabel.setText("o.o >You just opened " + localRepo.getName());
                }else{
                    CLI.statusLabel.setText("o.o >It seems, this repo's already open");
                }
            });
            buttons.add(button);
        }
        call(stage, title, buttons);
    }

    public static void giveMeButtonsForExtract(){
        Stage stage = new Stage();
        Button extrFileButton = new Button("File");
        Button extrDirButton = new Button("Directory");

        extrFileButton.setOnAction( e -> {
            stage.close();
            ExtractStage.callForFile();

        });
        extrDirButton.setOnAction( e-> {
            ExtractStage.callForDir();
            stage.close();
        });

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(extrFileButton);
        buttons.add(extrDirButton);
        call(stage, "What we're extracting ?", buttons);
    }

}
