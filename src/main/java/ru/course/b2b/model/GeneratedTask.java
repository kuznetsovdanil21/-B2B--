package ru.course.b2b.model;

public class GeneratedTask {

    private final int id;

    private final String createdDate;
    private final String title;
    private final String content;

    public GeneratedTask(
            int id,
            String createdDate,
            String title,
            String content
    ) {
        this.id = id;
        this.createdDate = createdDate;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}