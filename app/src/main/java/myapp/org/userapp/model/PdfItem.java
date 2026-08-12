package myapp.org.userapp.model;

public class PdfItem {
    private String id;
    private String subjectId;
    private String title;
    private String url;
    private String fileName;
    private long fileSize;
    private int pageCount;
    private String uploadedBy;
    private long createdAt;
    private boolean isActive;

    public PdfItem() {
    }

    public PdfItem(String id, String subjectId, String title, String url, String fileName, long fileSize, int pageCount, String uploadedBy, long createdAt, boolean isActive) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.url = url;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.pageCount = pageCount;
        this.uploadedBy = uploadedBy;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
