package ru.course.b2b.service;

import org.json.JSONObject;
import ru.course.b2b.model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonExportService {

    public void exportToJson(
            Product product,
            String region,
            String companyType,
            File file
    ) throws IOException {

        JSONObject json =
                new JSONObject();

        json.put(
                "product",
                product.getName()
        );

        json.put(
                "category",
                product.getCategory()
        );

        json.put(
                "okved",
                product.getOkved()
        );

        json.put(
                "keywords",
                product.getKeywords()
        );

        json.put(
                "region",
                region
        );

        json.put(
                "companyType",
                companyType
        );

        try (
                FileWriter writer =
                        new FileWriter(file)
        ) {

            writer.write(
                    json.toString(4)
            );
        }
    }
}