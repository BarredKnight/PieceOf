package root;

import org.kohsuke.github.GHRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class MyConfig implements Serializable {

    public String userLogin, username, token, wayToRepos, password;
    public ArrayList<String> openedRepos;

    public MyConfig(final String userLogin, final String username, final String token, final String wayToRepos, final String password){
        this.userLogin = userLogin;
        this.username = username;
        this.token = token;
        this.wayToRepos = wayToRepos;
        this.password = password;
    }
}
