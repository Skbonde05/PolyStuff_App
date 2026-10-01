package myapp.org.userapp.model;

public class UserProfile {
    private String id;
    private String name;
    private String email;
    private String username;
    private String imageUrl;
    private boolean isAdmin;
    private String role;
    private long createdAt;

    public UserProfile() {
    }

    public UserProfile(String id, String name, String email, String username, boolean isAdmin, String role, long createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.isAdmin = isAdmin;
        this.role = role;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
