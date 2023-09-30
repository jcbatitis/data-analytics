package models;

public class User {
    private String user_id;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private Boolean is_vip;

    public User(String user_id, String first_name, String last_name, String username, String password,
            Boolean is_vip) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.is_vip = is_vip;
    }

    public String getUserId() {
        return user_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getFullName() {
        return String.format("%s %s", first_name, last_name);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isVIP() {
        return is_vip;
    }
}
