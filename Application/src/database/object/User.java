package database.object;

public class User {

    private int id;
    private String username;
    private String password;
    private String profileName;

    public User(int id, String username, String password, String profileName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileName = profileName;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileName() {
        return profileName;
    }

}
