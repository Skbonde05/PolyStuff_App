package myapp.org.userapp.model;

public class Flashcard {
    private String id;
    private String subjectId;
    private String question;
    private String answer;
    private String category;
    private int difficulty;
    private long createdAt;

    public Flashcard() {
    }

    public Flashcard(String id, String subjectId, String question, String answer, String category, int difficulty, long createdAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.difficulty = difficulty;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
