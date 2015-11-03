package root.test;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.*;

public class Test2 {
    final static String userLogin = "maksimenko.natal@yandex.ru";
    final static String username = "maksimenko-natal";
    final static String password = ""; // deleted
    static GHRepository repo;

    public static void main(String[] args) throws IOException {
        GitHub github = GitHub.connectUsingPassword(userLogin, password);
        System.out.println(github.isCredentialValid());
        repo = github.getRepository(username + "/new-repository-1");

        String localFile = "/home/andrew/ForTests/new-repository-1/test-folder-2/data.txt";



        byte[] bytes;
        InputStream input = new FileInputStream(localFile);
        bytes = new byte[input.available()];
        input.read(bytes);

        repo.getFileContent("test-folder-2/data.txt").update(bytes, "atop");


    }
}
