package myapp.org.userapp;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String country;
    private String imageUrl;

    // Empty constructor (required for Firebase)
    public User() {
    }

    // Constructor with parameters
    public User(String userId, String name, String email, String password, String phone, String city, String country, String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    // Include getters and setters for all fields
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Add getters and setters for other fields
}

