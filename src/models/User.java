package models;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isVIP;

    public User(String userId, String firstName, String lastName, String username, String password,
            Boolean isVIP) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isVIP = isVIP;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isVIP() {
        return isVIP;
    }
}
