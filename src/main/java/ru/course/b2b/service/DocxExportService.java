package ru.course.b2b.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DocxExportService {

    public void exportTask(
            String content,
            File file
    ) throws IOException {

        XWPFDocument document =
                new XWPFDocument();

        XWPFParagraph paragraph =
                document.createParagraph();

        paragraph.createRun()
                .setText(content);

        try (
                FileOutputStream out =
                        new FileOutputStream(file)
        ) {
            document.write(out);
        }

        document.close();
    }
}