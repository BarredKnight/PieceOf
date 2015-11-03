package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by andrew on 11/3/15.
 */
public class InputDialog {  // there

    public static String call(){
        Stage stage = new Stage();

        TextField textField = new TextField();
        Button createButton = new Button("Create");
        Button buttonExit = new Button("Exit");
        HBox layout = new HBox(textField, createButton, buttonExit);

        Scene scene = new Scene(layout);
        stage.setScene(scene);

        stage.show();

        buttonExit.setOnAction( e -> stage.close());

        createButton.setOnAction( e -> {
            return textField.getText();
        });


    }
}
