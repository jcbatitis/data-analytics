package accounts;
public class Accounts {
    private String userId;
    private Integer role;
    private String username;
    private String password;

    public Accounts(String userId, Integer role, String username, String password) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.password = password;

    }

    public String getUserId() {
        return userId;
    }

    public Integer getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPasword() {
        return password;
    }
}
