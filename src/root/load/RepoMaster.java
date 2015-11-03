package root.load;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import root.MyConfig;

import java.io.File;
import java.io.IOException;

/**
 * Created by andrew on 11/3/15.
 */
public class RepoMaster {

    public static GHRepository createRepo(String repoName, GitHub github, MyConfig config) throws IOException {
        String localWay = config.wayToRepos + repoName;

        File file = new File(localWay);
        file.mkdir();
        return github.createRepository(repoName, "", "", true);
    }

    public static void createLocal(String repoName, MyConfig config){
        File file = new File(config.wayToRepos + "/" + repoName);
        file.mkdir();
    }


}
