package root.load;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import root.MyConfig;
import root.utils.CompareHelper;
import root.utils.TreeHelper;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
        final ArrayList<String> filesFromLocalTree;
        localTree = TreeHelper.fromArray((new File(config.wayToRepos + "/" + repo.getName())).listFiles(), repo.getName(), config.wayToRepos);
        filesFromLocalTree = TreeHelper.getOnlyFiles(localTree);
        gitTree = TreeHelper.fromGHTree(repo.getTreeRecursive(repo.getDefaultBranch(), 1).getTree(), repo.getName(), config.wayToRepos);


        ArrayList<String> toCreate = CompareHelper.getMissed(filesFromLocalTree, gitTree);
        createFiles(toCreate);

        ArrayList<String> toUpload = TreeHelper.getOnlyFiles(localTree);
        upload(toUpload);
    }


    // without spaces
    private void createFiles(ArrayList<String> fullWays){ //They're getting sorted
        for (String fullWay : fullWays){
            String name = TreeHelper.getName(fullWay);
            String innerPath = TreeHelper.getInnerPath(fullWay, config.wayToRepos, repo.getName());
            try {
                if (innerPath.equals(""))
                repo.createContent("", "new", innerPath + "" + name);
                else
                    repo.createContent("", "new", innerPath + "/" + name);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }   // Need to be tested

    private void upload(ArrayList<String> toBeUploaded) throws IOException {
        // there

        for (String path : toBeUploaded) {

                String gitPath = TreeHelper.getInnerPath(path, config.wayToRepos, repo.getName()) + "/" + TreeHelper.getName(path);
                byte[] bytes;
                InputStream input = new FileInputStream(path);
                bytes = new byte[input.available()];
                input.read(bytes);

                repo.getFileContent(gitPath).update(bytes, "atop");


        }
    }   // Should be


}
