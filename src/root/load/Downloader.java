package root.load;

import org.kohsuke.github.*;
import root.MyConfig;
import root.utils.CompareHelper;
import root.utils.MixHelper;
import root.utils.TreeHelper;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class Downloader {

    final MyConfig config;
    final GHRepository repo;

    public Downloader(final MyConfig config, final GHRepository repo) {
        this.config = config;
        this.repo = repo;
    }

    public void download() throws IOException {
        ArrayList<String> converted;
        //local tree
        File[] localFiles = (new File(config.wayToRepos + "/" + repo.getName()).listFiles());
        ArrayList<String> localTree = TreeHelper.fromArray(localFiles, repo.getName(), config.wayToRepos);
        //hub tree
        ArrayList<String> hubTree = TreeHelper.fromGHTree(repo.getTreeRecursive(repo.getDefaultBranch(), 1).getTree(), repo.getName(), config.wayToRepos);
        //missed
        ArrayList<String> missed = CompareHelper.getMissed(hubTree, localTree);
        ArrayList<GHTreeEntry> toDownload = MixHelper.getNeededEntriest(repo, missed, config);
        //creating
        createThemAll(toDownload);
        //updating
        updateThemAll(toDownload);





    }   //Seems ok

    private void createThemAll(ArrayList<GHTreeEntry> pathsToBeCreated) throws IOException {
        for (GHTreeEntry entry : pathsToBeCreated){
            String stringPath = config.wayToRepos + "/" + repo.getName() + "/" + entry.getPath();
            if (entry.getType().equals("blob")){
                (new File(stringPath)).createNewFile();
            }else
            {
                (new File(stringPath)).mkdir();
            }
        }
    }   //Seems ok

    private void updateThemAll(ArrayList<GHTreeEntry> pathsToBeUpdated) throws IOException {

        for (GHTreeEntry entry : pathsToBeUpdated){
            if (!entry.getType().equals("tree")){


            URL website = new URL(repo.getFileContent(entry.getPath()).getDownloadUrl());
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(config.wayToRepos + "/" + repo.getName() + "/" + entry.getPath());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        }
    }   //Seems ok
}
