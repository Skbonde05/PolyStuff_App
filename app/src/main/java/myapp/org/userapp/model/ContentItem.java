package myapp.org.userapp.model;

public class ContentItem {
    private String id;
    private String unitId;
    private String subjectId;
    private String contentType;
    private String title;
    private String description;
    private String fileUrl;
    private long fileSizeBytes;
    private int pageCount;
    private int durationSeconds;
    private boolean isDownloadable;
    private boolean isPremium;
    private int viewCount;
    private int downloadCount;
    private int sortOrder;
    private long createdAt;
    private long updatedAt;

    public ContentItem() {
    }

    public ContentItem(String id, String unitId, String subjectId, String contentType, String title, String description, String fileUrl, long fileSizeBytes, int pageCount, int durationSeconds, boolean isDownloadable, boolean isPremium, int viewCount, int downloadCount, int sortOrder, long createdAt, long updatedAt) {
        this.id = id;
        this.unitId = unitId;
        this.subjectId = subjectId;
        this.contentType = contentType;
        this.title = title;
        this.description = description;
        this.fileUrl = fileUrl;
        this.fileSizeBytes = fileSizeBytes;
        this.pageCount = pageCount;
        this.durationSeconds = durationSeconds;
        this.isDownloadable = isDownloadable;
        this.isPremium = isPremium;
        this.viewCount = viewCount;
        this.downloadCount = downloadCount;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUnitId() { return unitId; }
    public void setUnitId(String unitId) { this.unitId = unitId; }
    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public long getFileSizeBytes() { return fileSizeBytes; }
    public void setFileSizeBytes(long fileSizeBytes) { this.fileSizeBytes = fileSizeBytes; }
    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
    public int getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }
    public boolean isDownloadable() { return isDownloadable; }
    public void setDownloadable(boolean downloadable) { isDownloadable = downloadable; }
    public boolean isPremium() { return isPremium; }
    public void setPremium(boolean premium) { isPremium = premium; }
    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public int getDownloadCount() { return downloadCount; }
    public void setDownloadCount(int downloadCount) { this.downloadCount = downloadCount; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
