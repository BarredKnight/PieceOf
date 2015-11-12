package root.utils;

import javafx.scene.control.*;
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

    public static ArrayList<GHTreeEntry> getOnlyFilesFromHub(List<GHTreeEntry> entries){
        ArrayList<GHTreeEntry> out = new ArrayList<>();
        for (GHTreeEntry entry : entries){
            if (!entry.getType().equals("tree"))
                out.add(entry);
        }
        return out;
    }

    public static ArrayList<String> fromArray(File[] files){
        ArrayList<String> output = new ArrayList<>();
        writeItAndGiveFurther(files, output);
        return output;
    }

    public static ArrayList<String> getNames(ArrayList<String> fullWays){
        ArrayList<String> out = new ArrayList<>();
        for (String string : fullWays){
            File file = new File(string);
            out.add(file.getName());
        }
        return out;
    }   // OK

    public static ArrayList<String> getOnlyFiles(ArrayList<String> allWays){
        ArrayList<String> output = new ArrayList<>();
        for (String way : allWays){
            File file = new File(way);
            if (file.isFile()){
                output.add(way);
            }
        }
        return output;
    }   // for local only

    public static String getInnerPath(String fullPath, String localWay, String repoName){
        int size = localWay.length() + repoName.length() + 2;
        String outString;

        int sizeOfName = (new File(fullPath)).getName().length();
        int outSubstringPoint = fullPath.length() - sizeOfName + 1;

        try {
            outString = fullPath.substring(size, outSubstringPoint - 2);
        }catch (StringIndexOutOfBoundsException e){
            outString = fullPath.substring(size, outSubstringPoint - 1);
        }

        return outString;
    }

    public static String getName(String fullWay){
            File file = new File(fullWay);
            return file.getName();
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
