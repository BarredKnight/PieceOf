package root.test;


import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTree;
import org.kohsuke.github.GitHub;
import root.MyConfig;
import root.load.Downloader;
import root.utils.CompareHelper;
import root.utils.TreeHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Starter {
    final static String userLogin = "maksimenko.natal@yandex.ru";
    final static String username = "maksimenko-natal";
    static String password;
    final static MyConfig config = new MyConfig("", "", "", "/home/andrew/ForTests", "");
    static GHRepository repo;


    public static void main(String[] args) throws IOException {
        initialize();
        testDownload();
    }

    private static void initialize(){
        System.out.print("Password:");
        password =  (new Scanner(System.in)).nextLine();
    }

    private static void testFromGHTreeToArrayList() throws IOException {
        GitHub github = GitHub.connectUsingPassword(userLogin, password);
        System.out.println(github.isCredentialValid());
        repo = github.getRepository(username + "/new-repository-1");
        MyConfig config = new MyConfig("", "", "", "/home/me/", "");

        for (String s : TreeHelper.fromGHTree(repo.getTreeRecursive(repo.getDefaultBranch(), 1).getTree(), repo.getName(), config.wayToRepos)){
            System.out.println(s);
        }
    }   //OK

    private static void testFromArrayToArrayList() throws IOException {
        GitHub github = GitHub.connectUsingPassword(userLogin, password);
        repo = github.getRepository(username + "/new-repository-1");
        MyConfig config = new MyConfig("", "", "", "/home/andrew/ForTests/", "");

        File destination = new File(config.wayToRepos + repo.getName());
        System.out.println(destination.getPath());
        System.out.println(destination.isDirectory());
        for (String s : TreeHelper.fromArray(destination.listFiles())){
            System.out.println(s);
        }
    }   //Seems ok

    private static void testGetAndCompare() throws IOException {
        {
            GitHub github = GitHub.connectUsingPassword(userLogin, password);
            repo = github.getRepository(username + "/new-repository-1");

            GHTree tree = repo.getTreeRecursive(repo.getDefaultBranch(), 1);
            ArrayList<String> hubTree = TreeHelper.fromGHTree(tree.getTree(), repo.getName(), config.wayToRepos);

            File pointToRepo = new File(config.wayToRepos + "/" +repo.getName());
            ArrayList<String> localTree = TreeHelper.fromArray(pointToRepo.listFiles());

            //Printing
            for (String s : hubTree) {
                System.out.println(s);
            }
            System.out.println("-------------------------");
            for (String s : localTree){
                System.out.println(s);
            }

            System.out.println("============================");

            ArrayList<String> missedInLocal = CompareHelper.getMissed(hubTree, localTree);

            for (String s : missedInLocal){
                System.out.println(s);
            }

        }
    }

    private static void testDownload() throws IOException {

        GitHub github = GitHub.connectUsingPassword(userLogin, password);
        repo = github.getRepository(username + "/new-repository-1");
        MyConfig config = new MyConfig("", "", "", "/home/andrew/ForTests", "");

        Downloader downloader = new Downloader(config, repo);
        downloader.download();
    }
}
