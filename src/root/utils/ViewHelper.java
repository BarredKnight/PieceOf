package root.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import root.CLI;
import root.MyConfig;

import java.io.File;
import java.util.ArrayList;

public class ViewHelper {

    public static ArrayList<Tab> getCurrentTabs(MyConfig config){
        ArrayList<Tab> tabs = new ArrayList<>();
        for (String path : config.openedRepos){
            File repoDirectory = new File(path);
            TreeView<Button> tree = getButtonTree(config.wayToRepos, repoDirectory.getName());
            Tab tab = new Tab(repoDirectory.getName(), tree);
            tab.setOnClosed(e -> {
                config.openedRepos.remove(path);
                CLI.statusLabel.setText("o.o >" + tab.getText() + " no more with us...");
            });
            tabs.add(tab);
        }
        return tabs;
    }

    public static TreeView<Button> getButtonTree (String localWay, String repoName){
        File repoFolder = new File(localWay + "/" + repoName);
        ArrayList<String> paths = TreeHelper.fromArray(repoFolder.listFiles(), repoName, localWay);
        TreeView<Button> output;
        TreeItem<Button> root = new TreeItem<>();

        makeUpNodeOfTreeAndGiveFurther(repoFolder.listFiles(), root);
        output = new TreeView<>(root);
        output.setShowRoot(false);
        return output;
        }



    private static void makeUpNodeOfTreeAndGiveFurther(File[] filesForMakeUp, TreeItem<Button> task){
        for (File file: filesForMakeUp){
            Button button;
            if (file.isDirectory()){
                button = new Button(file.getName() + "/");
            }else{
                button = new Button(file.getName());
            }
            TreeItem<Button> item = new TreeItem<>(button); //
            item.setExpanded(true);
            task.getChildren().add(item);

            if (file.listFiles() != null)
                makeUpNodeOfTreeAndGiveFurther(file.listFiles(), item);
        }
    }
}




