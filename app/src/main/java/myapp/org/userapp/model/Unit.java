package myapp.org.userapp.model;

public class Unit {
    private String id;
    private String subjectId;
    private int unitNumber;
    private String unitName;
    private String title;
    private String description;
    private int sortOrder;
    private long createdAt;

    public Unit() {
    }

    public Unit(String id, String subjectId, int unitNumber, String unitName, String title, String description, int sortOrder, long createdAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.unitNumber = unitNumber;
        this.unitName = unitName;
        this.title = title;
        this.description = description;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public int getUnitNumber() { return unitNumber; }
    public void setUnitNumber(int unitNumber) { this.unitNumber = unitNumber; }
    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
