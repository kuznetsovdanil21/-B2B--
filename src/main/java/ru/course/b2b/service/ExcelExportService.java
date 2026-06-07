package ru.course.b2b.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.course.b2b.HistoryRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExportService {

    public void exportHistory(
            List<HistoryRecord> records,
            File file
    ) throws IOException {

        XSSFWorkbook workbook =
                new XSSFWorkbook();

        Sheet sheet =
                workbook.createSheet("История");

        Row header =
                sheet.createRow(0);

        header.createCell(0)
                .setCellValue("Дата");

        header.createCell(1)
                .setCellValue("Продукция");

        header.createCell(2)
                .setCellValue("Регион");

        header.createCell(3)
                .setCellValue("Тип компании");

        int rowNum = 1;

        for (HistoryRecord record : records) {

            Row row =
                    sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(record.getDate());

            row.createCell(1)
                    .setCellValue(record.getProduct());

            row.createCell(2)
                    .setCellValue(record.getRegion());

            row.createCell(3)
                    .setCellValue(record.getCompanyType());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        try (
                FileOutputStream out =
                        new FileOutputStream(file)
        ) {

            workbook.write(out);
        }

        workbook.close();
    }
}