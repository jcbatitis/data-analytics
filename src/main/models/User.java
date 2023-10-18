package main.models;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;

    private Boolean isVIP;

    public User() {

    }

    public User(String userId, String firstName, String lastName, String username, String password,
            Boolean isVIP) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isVIP = isVIP;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsVip(Boolean isVip) {
        this.isVIP = isVip;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
