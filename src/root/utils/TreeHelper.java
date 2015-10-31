package root.utils;

import org.kohsuke.github.GHTreeEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TreeHelper {

    public static ArrayList<String> fromGHTree(List<GHTreeEntry> tree, String repoName, String localWay){
        ArrayList<String> outputArray = new ArrayList<>();

        for (GHTreeEntry entry : tree){
            outputArray.add(localWay + "/" + repoName + "/" + entry.getPath());
        }
        return outputArray;
    } //OK

    public static ArrayList<String> fromArray(File[] files, String repoName, String localWay){
        ArrayList<String> output = new ArrayList<>();
        writeItAndGiveFurther(files, output);
        return output;
    }

    private static void writeItAndGiveFurther(File[] filesToWrite, ArrayList<String> filesForWrite){
        for (File file: filesToWrite){
            filesForWrite.add(file.getAbsolutePath());
            if (file.listFiles() != null)
            writeItAndGiveFurther(file.listFiles(), filesForWrite);
        }


    }

    private static GHTreeEntry getNeededEntry(List<GHTreeEntry> tree, String pathToFind, String pathToLocal, String repoName){
        for (GHTreeEntry entry : tree){
            final String fromGH = pathToLocal + repoName + "/" + entry.getPath();
            if (pathToFind.equals(fromGH)){
                return entry;
            }
        }
        return null;
    }

}
