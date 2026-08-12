package myapp.org.userapp.model;

public class Quiz {
    private String id;
    private String subjectId;
    private String title;
    private String description;
    private int questionCount;
    private int timeLimit;
    private boolean isActive;
    private long createdAt;

    public Quiz() {
    }

    public Quiz(String id, String subjectId, String title, String description, int questionCount, int timeLimit, boolean isActive, long createdAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.questionCount = questionCount;
        this.timeLimit = timeLimit;
        this.isActive = isActive;
        this.createdAt = createdAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
