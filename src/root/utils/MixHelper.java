package root.utils;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTreeEntry;
import root.MyConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MixHelper {

    public static ArrayList<GHTreeEntry> getNeededEntriest(GHRepository repo, ArrayList<String> pathsForGetting, MyConfig config) throws IOException {
        ArrayList<GHTreeEntry> out = new ArrayList<>();
        final String convertedRepoPath = config.wayToRepos + "/" + repo.getName() + "/";
        for(String localWay : pathsForGetting) {
            for (GHTreeEntry entry : repo.getTreeRecursive(repo.getDefaultBranch(), 1).getTree()) {
                if ((convertedRepoPath + entry.getPath()).equals(localWay)) {
                    out.add(entry);
                }
            }
        }
        return out;
    }

    public static List<GHContent> getHubRepoContent(GHRepository repo) throws IOException {
        return repo.getDirectoryContent("");
    }   //not sure
}
