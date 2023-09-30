package models;

public class User {
    private String id;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private Boolean is_vip;

    public User(String id, String first_name, String last_name, String username, String password,
            Boolean is_vip) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.is_vip = is_vip;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
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