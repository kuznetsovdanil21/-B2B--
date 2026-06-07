package ru.course.b2b.model;

public class Product {

    private String name;
    private String category;
    private String okved;
    private String keywords;

    public Product(
            String name,
            String category,
            String okved,
            String keywords
    ) {
        this.name = name;
        this.category = category;
        this.okved = okved;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOkved() {
        return okved;
    }

    public void setOkved(String okved) {
        this.okved = okved;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return name;
    }
}