package ru.course.b2b.model;

public class CompanyInfo {

    private final String name;
    private final String inn;
    private final String address;
    private final String okved;

    public CompanyInfo(
            String name,
            String inn,
            String address,
            String okved
    ) {
        this.name = name;
        this.inn = inn;
        this.address = address;
        this.okved = okved;
    }

    public String getName() {
        return name;
    }

    public String getInn() {
        return inn;
    }

    public String getAddress() {
        return address;
    }

    public String getOkved() {
        return okved;
    }
}