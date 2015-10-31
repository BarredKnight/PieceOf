package root.test;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Test extends Application {
    TreeView<Button> tree;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TreeItem<Button> root, bucky, megan, thenewboston, YouTube, Chicken;
        root = new TreeItem<>();
        root.setExpanded(true);

        //Bucky
        bucky = makeBranch("Bucky", root);
        thenewboston = makeBranch("thenewboston", bucky);
        YouTube = makeBranch("YouTube", thenewboston);
        Chicken = makeBranch("Chicken", YouTube);
        //Megan
        megan = makeBranch("Megan", root);
        makeBranch("Glitter", megan);
        makeBranch("Makeup", megan);

        //Create tree
        tree = new TreeView<>(root);
//        moveThroughAndDoUpload(tree.getRoot());


        //layout

        SplitPane layout = new SplitPane();

        TabPane tabPane = new TabPane(new Tab("First"), new Tab("Second"));
        tabPane.getTabs().get(0).setContent(tree);

        // and another
        layout.setOrientation(Orientation.HORIZONTAL);
        layout.getItems().addAll(tabPane, new VBox(new Button("new"), new ToggleButton("adding mode"), new Button("Upload")));


        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //Create brunches
    public TreeItem<Button> makeBranch(String title, TreeItem<Button> parent){
        TreeItem<Button> item = new TreeItem<>(new Button(title));
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
