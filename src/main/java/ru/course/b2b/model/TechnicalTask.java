package ru.course.b2b.model;

public class TechnicalTask {

    private final String title;
    private final String content;

    public TechnicalTask(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}