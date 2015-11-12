package root.load;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTreeEntry;
import root.CLI;
import root.MyConfig;
import root.utils.CompareHelper;
import root.utils.TreeHelper;

import java.io.*;
import java.util.ArrayList;

public class Uploader {

    final MyConfig config;
    final GHRepository repo;

    public Uploader(final MyConfig config, final GHRepository repo){
            this.config = config;
            this.repo = repo;
        }

    public void uploadSoft() throws IOException {
        final ArrayList<String> localTree;
        final ArrayList<String> gitTree;
        final ArrayList<String> filesFromLocalTree;
        localTree = TreeHelper.fromArray((new File(config.wayToRepos + "/" + repo.getName())).listFiles());
        filesFromLocalTree = TreeHelper.getOnlyFiles(localTree);
        gitTree = TreeHelper.fromGHTree(repo.getTreeRecursive(repo.getDefaultBranch(), 1).getTree(), repo.getName(), config.wayToRepos);


        ArrayList<String> toCreate = CompareHelper.getMissed(filesFromLocalTree, gitTree);
        createFiles(toCreate);

        ArrayList<String> toUpload = TreeHelper.getOnlyFiles(localTree);
        upload(toUpload);
    }

    public void uploadRude() throws IOException {
        final ArrayList<String> localTree;
        final ArrayList<String> gitTree;
        final ArrayList<String> filesFromLocalTree;
        final ArrayList<String> additionalFilesInHub;
        localTree = TreeHelper.fromArray((new File(config.wayToRepos + "/" + repo.getName())).listFiles());
        filesFromLocalTree = TreeHelper.getOnlyFiles(localTree);
        gitTree = TreeHelper.fromGHTree(TreeHelper.getOnlyFilesFromHub(repo.getTreeRecursive(repo.getDefaultBranch(), 1).getTree()), repo.getName(), config.wayToRepos);

        additionalFilesInHub = CompareHelper.getMissed(gitTree, filesFromLocalTree);    //will there be directories?

        deleteFiles(additionalFilesInHub);

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

    private void deleteFiles(ArrayList<String> fullWays){
        for (String fullWay : fullWays){
            String name = TreeHelper.getName(fullWay);
            String innerPath = TreeHelper.getInnerPath(fullWay, config.wayToRepos, repo.getName());

                if (innerPath.equals("")) {
                    try {
                        repo.getFileContent(innerPath + "" + name).delete("deleted");
                    } catch (IOException e) {
                        if (CLI.consoleLog)
                            System.out.println("Error while getting or deleting " + name + ".");
                        if (CLI.consoleExcep)
                            e.printStackTrace();
                        CLI.refreshMainStage("OnO >Seems that there was unexpected error during deleting files. Try later");

                    }
                } else {
                    try {
                        repo.getFileContent(innerPath + "/" + name).delete("deleted");
                    } catch (IOException e) {
                        if (CLI.consoleLog)
                            System.out.println("Error while getting or deleting " + name + ".");
                        if (CLI.consoleExcep)
                            e.printStackTrace();
                        CLI.refreshMainStage("OnO >Seems that there was unexpected error during deleting files. Try later");
                    }
                }
        }
    }

    private void upload(ArrayList<String> toBeUploaded) throws IOException {
        for (String path : toBeUploaded) {

                String gitPath = TreeHelper.getInnerPath(path, config.wayToRepos, repo.getName()) + "/" + TreeHelper.getName(path);
                byte[] bytes;
                InputStream input = new FileInputStream(path);
                bytes = new byte[input.available()];
                input.read(bytes);
            try {
                GHContent content = repo.getFileContent(gitPath);
                content.update(bytes, "atop");
            }
            catch (IOException e){
                if (CLI.consoleLog)
                System.out.println("File "  + TreeHelper.getName(path) + " more than 1MB. Can't do anything with it.");
                if (CLI.consoleExcep)
                    e.printStackTrace();
            }catch (OutOfMemoryError e){
                if (CLI.consoleLog)
                    System.out.println("File " + TreeHelper.getName(path) + "is really big. Can't upload it.");
                if (CLI.consoleExcep)
                    e.printStackTrace();
            }

        }
    }   // Should be


}
