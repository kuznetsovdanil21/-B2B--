package ru.course.b2b;

public class HistoryRecord {

    private final String date;
    private final String product;
    private final String region;
    private final String companyType;

    public HistoryRecord(
            String date,
            String product,
            String region,
            String companyType
    ) {
        this.date = date;
        this.product = product;
        this.region = region;
        this.companyType = companyType;
    }

    public String getDate() {
        return date;
    }

    public String getProduct() {
        return product;
    }

    public String getRegion() {
        return region;
    }

    public String getCompanyType() {
        return companyType;
    }
}