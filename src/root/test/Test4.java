package root.test;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.IOException;

/**
 * Created by andrew on 10/29/15.
 */
public class Test4 {
    final static String userLogin = "maksimenko.natal@yandex.ru";
    final static String username = "maksimenko-natal";
    final static String password = "";  // deleted
    final static String localFile = "/home/andrew/ForTests/fromGit.txt";
    static GHRepository repo;
    static GHContent content;

    public static void main(String[] args) throws IOException {
        GitHub github = GitHub.connectUsingPassword(userLogin, password);  // deleted
        repo = github.getRepository(username + "/new-repository-1");

        // without russian leters
        // spaces

        // what ok - dots
        // comma


        // there are huge files - videos

//        repo.createContent("", "Got it", "test-folderrrr/tha,t---.txt");
        File file = new File("/home/andrew/Downloads/astah-community_7.0.0.846701-0_all.deb");
        System.out.println("Length : " + file.length());
        System.out.println("Tatal space: " + file.getTotalSpace());
        System.out.println("Used space : " + file.getUsableSpace());
        System.out.println("Free space : " + file.getFreeSpace());


    }
}
