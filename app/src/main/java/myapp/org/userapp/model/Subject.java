package myapp.org.userapp.model;

public class Subject {
    private String id;
    private String key;
    private String title;
    private String description;
    private String category;
    private int pdfCount;
    private boolean isActive;
    private long createdAt;
    private long updatedAt;

    public Subject() {
    }

    public Subject(String id, String key, String title, String description, String category, int pdfCount, boolean isActive, long createdAt, long updatedAt) {
        this.id = id;
        this.key = key;
        this.title = title;
        this.description = description;
        this.category = category;
        this.pdfCount = pdfCount;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getPdfCount() { return pdfCount; }
    public void setPdfCount(int pdfCount) { this.pdfCount = pdfCount; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
