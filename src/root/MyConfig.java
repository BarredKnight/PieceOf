package root;

public class MyConfig {

    public final String userLogin, username, token, wayToRepo;

    public MyConfig(final String userLogin, final String username, final String token, final String wayToRepo){
        this.userLogin = userLogin;
        this.username = username;
        this.token = token;
        this.wayToRepo = wayToRepo;
    }
}
