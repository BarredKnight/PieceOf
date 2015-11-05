package root.test;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import root.MyConfig;
import root.load.Uploader;

import java.io.IOException;

/**
 * Created by andrew on 11/2/15.
 */
public class Test6 {
    final static String userLogin = "maksimenko.natal@yandex.ru";
    final static String username = "maksimenko-natal";
    final static String p = "through all this shit";  // deleted
    final static String localRepo = "/home/andrew/ForTests/new-repository-3";
    final static String repoName = "new-repository-3";
    final static String localWay = "/home/andrew/ForTests";
    static GHRepository repo;
    static GHContent content;

    public static void main(String[] args) throws IOException {

            MyConfig config = new MyConfig(userLogin, username, null, localWay, p);
            GitHub hub = GitHub.connectUsingPassword(userLogin, p);
            GHRepository repo = hub.getRepository(username + "/" +repoName);

            Uploader uploader = new Uploader(config, repo);
            uploader.uploadSoft();
    }
}
