package root.test;

import org.kohsuke.github.*;
import root.utils.TreeHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by andrew on 10/30/15.
 */
public class Test5 {
    final static String userLogin = "maksimenko.natal@yandex.ru";
    final static String username = "maksimenko-natal";
    final static String password = "";  // deleted
    final static String localRepo = "/home/andrew/ForTests/new-repository-1";
    final static String localWay = "/home/andrew/ForTests";
    static GHRepository repo;
    static GHContent content;

    public static void main(String[] args) {
//        ArrayList<String> fullWays = TreeHelper.fromArray(new File(localRepo).listFiles(), "new-repository-1", "/home/andrew/ForTests/new-repository-1");
//        ArrayList<String> fromRepotoFileExclusive = TreeHelper.waysFromRepoToFileExclusive(fullWays, localWay, "new-repository-1");
//        for (String s : fromRepotoFileExclusive){
//            System.out.println(s);
//        }
//        ArrayList<String> names = TreeHelper.getNames(fullWays);
//        for (String name : names){
//            System.out.println(name);
//        }
    }


}
