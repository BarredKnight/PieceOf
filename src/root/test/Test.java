package root.test;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import root.MyConfig;
import root.utils.TreeHelper;
import view.Preconfigurator;

public class Test extends Application {
    TreeView<Button> tree;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Preconfigurator.preconfigure(new MyConfig("", "", "", "", ""));


        //layout
        /////////////////
        TreeView<Button> treeView = TreeHelper.getButtonTree("/home/andrew/ForTests", "new-repository-1");

        SplitPane layout = new SplitPane();

        TabPane tabPane = new TabPane(new Tab("First"), new Tab("Second"));
        tabPane.getTabs().get(0).setContent(treeView);

        // and another
        layout.setOrientation(Orientation.HORIZONTAL);
        HBox hBox = new HBox(new Button("New local"), new Button("Open local"));
        layout.getItems().addAll(tabPane, new VBox(hBox, new Button("Upload"), new Label("here")));

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
