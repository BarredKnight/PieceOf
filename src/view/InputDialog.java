package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by andrew on 11/3/15.
 */
public class InputDialog {  // make title for it
    static String result;

    public static String call(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField textField = new TextField();
        Button createButton = new Button("Create");
        Button buttonExit = new Button("Exit");
        HBox layout = new HBox(textField, createButton, buttonExit);

        Scene scene = new Scene(layout);
        stage.setScene(scene);



        buttonExit.setOnAction( e -> {
            result = null;
            stage.close();
        });

        createButton.setOnAction( e -> {
            result = textField.getText();
            stage.close();
        });
        stage.showAndWait();
        return result;
    }
}
