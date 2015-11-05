package root.utils;

import org.kohsuke.github.GHContent;
import root.MyConfig;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by andrew on 11/4/15.
 */
public class TolerableHelper {

    public static ArrayList<String> getPathsOfLocalRepos(MyConfig config){
        ArrayList<String> allPaths = new ArrayList<>();
        File[] rootContent = new File(config.wayToRepos).listFiles();
        for (File file : rootContent){
            if (file.isDirectory()){
                allPaths.add(file.getAbsolutePath());
            }
        }
        return allPaths;
    }

    public static ArrayList<File> getLocalRepos(MyConfig config){
        ArrayList<File> allRepos = new ArrayList<>();
        File[] rootContent = new File(config.wayToRepos).listFiles();
        if (rootContent != null)
        for (File file : rootContent){
            if (file.isDirectory()){
                allRepos.add(file);
            }
        }
        return allRepos;
    }

    public static ArrayList<String> getNamesOfLocalRepos(MyConfig config){
        ArrayList<String> allPaths = getPathsOfLocalRepos(config);
        ArrayList<String> namesOfLocalRepos = new ArrayList<>();
        for (String path : allPaths){
            File repo = new File(path);
            namesOfLocalRepos.add(repo.getName());
        }
        return namesOfLocalRepos;
    }

    public static ArrayList<String> getNamesOfHubRepos(MyConfig config){
        return null;
    }
}
