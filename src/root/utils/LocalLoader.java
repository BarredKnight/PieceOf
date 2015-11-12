package root.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by andrew on 11/8/15.
 */
public class LocalLoader {

    public static void load(File source, File to){
        if (source.isDirectory()){

        }else if (source.isFile()){

        }
    }

    public static ArrayList<File> getFilesFromLocal(File root){
        ArrayList<String> allPaths = TreeHelper.fromArray((root.listFiles()));
        ArrayList<File> out = new ArrayList<>();
        for (String path : allPaths){
            File file = new File(path);
            out.add(file);
        }
        return out;
    }

    public static void loadMany(File source, File to) throws IOException {

        loadOne(source, to);
        if (source.isDirectory()) {
            ArrayList<String> allPaths = TreeHelper.fromArray(source.listFiles());
            for (String path : allPaths) {
                loadOne(new File(path), new File(to + TolerableHelper.getRight(innerPath(source.getAbsolutePath(), path))));
            }
        }else if (source.isFile()){
            loadOne(source, to);
        }
    }

    public static void loadOne(File source, File to) throws IOException {
        if (!to.exists()) {
            if (source.isDirectory())
            to.mkdir();
            else if (source.isFile())
                to.createNewFile();
        }
        byte[] sourceBytes = readFile(source);
        if (sourceBytes != null)
        writeToFile(sourceBytes, to);

    }

    private static String innerPath(String from, String end){
        return end.substring(from.length());
    }   // hz
    private static byte[] readFile(File path) throws IOException {
        if (path.isDirectory())
            return null;
        byte[] bytes;
        InputStream input;
            input = new FileInputStream(path);
            bytes = new byte[input.available()];
            input.read(bytes);
            input.close();
        return bytes;
    }

    private static void writeToFile(byte[] toWrite, File to) throws IOException {
        FileOutputStream fos;
            fos = new FileOutputStream(to);
            fos.write(toWrite);
            fos.close();
    }

}
