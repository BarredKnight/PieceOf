package root.test;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Test3 { // DOWNLOADING

    final static String userLogin = "maksimenko.natal@yandex.ru";
    final static String username = "maksimenko-natal";
    final static String password = "";  // deleted
    final static String localFile = "/home/andrew/ForTests/fromGit.txt";
    static GHRepository repo;
    static GHContent content;

    public static void main(String[] args) throws IOException {
        GitHub github = GitHub.connectUsingOAuth("");   // deleted
        repo = github.getRepository(username + "/new-repository-1");
        content = repo.getFileContent("test-folder-2/data.txt");

        byte[] bytes = new byte[100];
        URL website = new URL(content.getDownloadUrl());
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(localFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

}
