package myapp.org.userapp;

public class HelperClass {

    private String name;
    private String email;
    private String username;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public HelperClass(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
    }

    public HelperClass(String name, String email, String username, String imageUrl) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public HelperClass() {
    }
}