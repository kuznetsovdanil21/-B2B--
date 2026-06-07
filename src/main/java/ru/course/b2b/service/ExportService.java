package ru.course.b2b.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportService {

    public void exportToTxt(
            String content,
            File file
    ) throws IOException {

        try (FileWriter writer =
                     new FileWriter(file)) {

            writer.write(content);
        }
    }
}