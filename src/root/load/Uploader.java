package root.load;

import org.kohsuke.github.GHRepository;
import root.MyConfig;
import root.utils.CompareHelper;
import root.utils.TreeHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Uploader {

    final MyConfig config;
    final GHRepository repo;

    public Uploader(final MyConfig config, final GHRepository repo){
            this.config = config;
            this.repo = repo;
        }

    public void upload() throws IOException {
        final ArrayList<String> localTree;
        final ArrayList<String> gitTree;
        localTree = TreeHelper.fromArray((new File(config.wayToRepo + repo.getName())).listFiles(), repo.getName(), config.wayToRepo);
        gitTree = TreeHelper.fromGHTree(repo.getTreeRecursive("/", 1).getTree(), repo.getName(), config.wayToRepo);

        ArrayList<String> toCreate = CompareHelper.getMissed(localTree, gitTree);

    }

    private void createFiles(ArrayList<String> toLoad){ //They're getting sorted
        for (String string : toLoad){

        }
    }


}
