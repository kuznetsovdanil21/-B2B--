package ru.course.b2b;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ru.course.b2b.model.Product;
import ru.course.b2b.model.TechnicalTask;
import ru.course.b2b.service.ProductService;
import ru.course.b2b.service.TechnicalTaskService;
import javafx.stage.FileChooser;
import ru.course.b2b.service.ExportService;
import ru.course.b2b.service.ExcelExportService;
import java.io.File;
import java.io.IOException;
import ru.course.b2b.service.HistoryService;
import ru.course.b2b.service.DaDataService;
import ru.course.b2b.model.CompanyInfo;
import ru.course.b2b.model.Template;
import ru.course.b2b.service.TemplateService;
import ru.course.b2b.service.JsonExportService;
import ru.course.b2b.service.DocxExportService;
import ru.course.b2b.model.GeneratedTask;
import ru.course.b2b.service.GeneratedTaskService;
import javafx.scene.control.TextArea;

public class MainApp extends Application {

    private final ProductService productService = new ProductService();
    private final TechnicalTaskService technicalTaskService = new TechnicalTaskService();
    private final ExportService exportService =
            new ExportService();
    private final JsonExportService jsonExportService =
            new JsonExportService();
    private final GeneratedTaskService generatedTaskService =
            new GeneratedTaskService();
    private final DocxExportService docxExportService =
            new DocxExportService();
    private final TemplateService templateService =
            new TemplateService();

    private final HistoryService historyService =
            new HistoryService();
    private final ExcelExportService excelExportService =
            new ExcelExportService();
    private final DaDataService daDataService =
            new DaDataService();
    @Override
    public void start(Stage stage) {

        TabPane tabPane = new TabPane();
        Tab productsTab = new Tab("Справочник продукции");
        productsTab.setClosable(false);
        Tab createTaskTab = new Tab("Создание ТЗ");
        createTaskTab.setClosable(false);

        Tab templatesTab = new Tab("Шаблоны ТЗ");
        templatesTab.setClosable(false);

        Tab historyTab = new Tab("История");
        historyTab.setClosable(false);

        Tab companyTab = new Tab("Поиск компании");
        companyTab.setClosable(false);
        productsTab.setContent(createProductsPane());
        createTaskTab.setContent(createTaskPane());
        templatesTab.setContent(createTemplatesPane());
        historyTab.setContent(createHistoryPane());
        companyTab.setContent(createCompanyPane());

        tabPane.getTabs().addAll(
                createTaskTab,
                productsTab,
                templatesTab,
                historyTab,
                companyTab
        );

        Scene scene = new Scene(tabPane, 1300, 800);

        stage.setTitle("Система формирования ТЗ для анализа B2B-конкурентов");
        stage.setScene(scene);
        stage.show();
    }

    private BorderPane createTaskPane() {

        ComboBox<Product> productBox = new ComboBox<>();
        productBox.setItems(productService.getAllProducts());
        ComboBox<Template> templateBox =
                new ComboBox<>();

        templateBox.setItems(
                templateService.getAllTemplates()
        );
        templateBox.getItems().add(
                0,
                new Template(
                        -1,
                        "<Не выбран>",
                        "",
                        "",
                        ""
                )
        );

        templateBox.getSelectionModel()
                .selectFirst();

        TextField regionField = new TextField();

        ComboBox<String> companyTypeBox = new ComboBox<>();

        companyTypeBox.getItems().addAll(
                "Производители",
                "Поставщики",
                "Дистрибьюторы"
        );

        TextField categoryField = new TextField();
        categoryField.setEditable(false);

        TextField okvedField = new TextField();
        okvedField.setEditable(false);

        TextArea keywordsArea = new TextArea();

        keywordsArea.setEditable(false);
        keywordsArea.setPrefHeight(120);

        TextArea resultArea = new TextArea();
        resultArea.setWrapText(true);

        productBox.setOnAction(e -> {

            Product product = productBox.getValue();

            if (product == null) {
                return;
            }

            categoryField.setText(
                    product.getCategory()
            );

            okvedField.setText(
                    product.getOkved()
            );

            keywordsArea.setText(
                    product.getKeywords()
            );
        });


        Button generateButton =
                new Button("Сформировать ТЗ");
        Button exportButton =
                new Button("Экспортировать ТЗ");
        Button exportDocxButton =
                new Button("Экспорт DOCX");

        exportDocxButton.setMaxWidth(
                Double.MAX_VALUE
        );
        Button exportJsonButton =
                new Button("Экспорт JSON");

        exportJsonButton.setMaxWidth(
                Double.MAX_VALUE
        );

        exportButton.setMaxWidth(Double.MAX_VALUE);

        generateButton.setMaxWidth(Double.MAX_VALUE);

        generateButton.setOnAction(e -> {

            Product product =
                    productBox.getValue();
            Template template =
                    templateBox.getValue();

            if (product == null) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);
                alert.setContentText(
                        "Выберите продукцию."
                );

                alert.showAndWait();

                return;
            }
            if (template == null) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);

                alert.setContentText(
                        "Выберите шаблон ТЗ."
                );

                alert.showAndWait();

                return;
            }

            String region =
                    regionField.getText();

            String companyType =
                    companyTypeBox.getValue();

            if (companyType == null) {
                companyType = "Компании";
            }

            historyService.addRecord(
                    new HistoryRecord(
                            java.time.LocalDateTime.now()
                                    .toString(),
                            product.getName(),
                            region,
                            companyType
                    )
            );

            TechnicalTask task =
                    technicalTaskService.generateTask(
                            template,
                            product,
                            region,
                            companyType
                    );

            resultArea.setText(
                    task.getContent()
            );

            generatedTaskService.addTask(
                    new GeneratedTask(
                            0,
                            java.time.LocalDateTime.now()
                                    .toString(),
                            product.getName(),
                            task.getContent()
                    )
            );
        });
        exportButton.setOnAction(e -> {

            if (resultArea.getText().isBlank()) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);
                alert.setContentText(
                        "Сначала сформируйте техническое задание."
                );

                alert.showAndWait();

                return;
            }

            FileChooser fileChooser =
                    new FileChooser();

            fileChooser.setTitle(
                    "Сохранение ТЗ"
            );

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Текстовые файлы",
                            "*.txt"
                    )
            );

            fileChooser.setInitialFileName(
                    "technical_task.txt"
            );

            File file =
                    fileChooser.showSaveDialog(
                            null
                    );

            if (file == null) {
                return;
            }

            try {

                exportService.exportToTxt(
                        resultArea.getText(),
                        file
                );

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);
                alert.setContentText(
                        "Файл успешно сохранен."
                );

                alert.showAndWait();

            } catch (IOException ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText(null);
                alert.setContentText(
                        ex.getMessage()
                );

                alert.showAndWait();
            }
        });
        exportDocxButton.setOnAction(e -> {

            if (resultArea.getText().isBlank()) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);

                alert.setContentText(
                        "Сначала сформируйте техническое задание."
                );

                alert.showAndWait();

                return;
            }

            FileChooser fileChooser =
                    new FileChooser();

            fileChooser.setTitle(
                    "Сохранить DOCX"
            );

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Word (*.docx)",
                            "*.docx"
                    )
            );

            fileChooser.setInitialFileName(
                    "technical_task.docx"
            );

            File file =
                    fileChooser.showSaveDialog(
                            null
                    );

            if (file == null) {
                return;
            }

            try {

                docxExportService.exportTask(
                        resultArea.getText(),
                        file
                );

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);

                alert.setContentText(
                        "DOCX успешно сохранен."
                );

                alert.showAndWait();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText("Ошибка");

                alert.setContentText(
                        ex.getMessage()
                );

                alert.showAndWait();
            }
        });
        exportJsonButton.setOnAction(e -> {

            Product product =
                    productBox.getValue();

            if (product == null) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);

                alert.setContentText(
                        "Выберите продукцию."
                );

                alert.showAndWait();

                return;
            }

            String region =
                    regionField.getText();

            String companyType =
                    companyTypeBox.getValue();

            if (companyType == null) {
                companyType = "";
            }

            FileChooser fileChooser =
                    new FileChooser();

            fileChooser.setTitle(
                    "Сохранить JSON"
            );

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "JSON (*.json)",
                            "*.json"
                    )
            );

            fileChooser.setInitialFileName(
                    "technical_task.json"
            );

            File file =
                    fileChooser.showSaveDialog(
                            null
                    );

            if (file == null) {
                return;
            }

            try {

                jsonExportService.exportToJson(
                        product,
                        region,
                        companyType,
                        file
                );

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);

                alert.setContentText(
                        "JSON успешно сохранен."
                );

                alert.showAndWait();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText("Ошибка");

                alert.setContentText(
                        ex.getMessage()
                );

                alert.showAndWait();
            }
        });

        VBox leftPanel = new VBox(10);

        leftPanel.setPadding(
                new Insets(15)
        );

        leftPanel.setPrefWidth(350);

        leftPanel.getChildren().addAll(

                new Label("Шаблон ТЗ"),
                templateBox,

                new Label("Продукция"),
                productBox,

                new Label("Регион"),
                regionField,

                new Label("Тип компаний"),
                companyTypeBox,

                new Label("Категория рынка"),
                categoryField,

                new Label("ОКВЭД"),
                okvedField,

                new Label("Ключевые слова"),
                keywordsArea,

                generateButton,
                exportButton,
                exportDocxButton,
                exportJsonButton
        );

        VBox rightPanel = new VBox(10);

        rightPanel.setPadding(
                new Insets(15)
        );

        Label title =
                new Label(
                        "Техническое задание"
                );

        title.setStyle(
                "-fx-font-size: 18px; -fx-font-weight: bold;"
        );

        VBox.setVgrow(
                resultArea,
                Priority.ALWAYS
        );

        rightPanel.getChildren().addAll(
                title,
                resultArea
        );

        SplitPane splitPane =
                new SplitPane();

        splitPane.getItems().addAll(
                leftPanel,
                rightPanel
        );

        splitPane.setDividerPositions(
                0.3
        );

        BorderPane root =
                new BorderPane();

        root.setCenter(splitPane);

        return root;
    }
    private BorderPane createProductsPane() {

        TableView<Product> table =
                new TableView<>();

        table.setItems(
                productService.getAllProducts()
        );

        TableColumn<Product, String> nameCol =
                new TableColumn<>("Продукция");

        TableColumn<Product, String> categoryCol =
                new TableColumn<>("Категория");

        TableColumn<Product, String> okvedCol =
                new TableColumn<>("ОКВЭД");

        nameCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getName()
                )
        );

        categoryCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getCategory()
                )
        );

        okvedCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getOkved()
                )
        );

        nameCol.setPrefWidth(300);
        categoryCol.setPrefWidth(350);
        okvedCol.setPrefWidth(150);

        table.getColumns().addAll(
                nameCol,
                categoryCol,
                okvedCol
        );

        Button addButton =
                new Button("Добавить");

        Button editButton =
                new Button("Редактировать");

        Button deleteButton =
                new Button("Удалить");

        addButton.setOnAction(e -> {

            Product product =
                    ProductDialog.showDialog();

            if (product != null) {

                productService.addProduct(
                        product
                );
            }
        });

        editButton.setOnAction(e -> {

            Product selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);

                alert.setContentText(
                        "Выберите продукцию."
                );

                alert.showAndWait();

                return;
            }

            Product updated =
                    ProductDialog.showDialog(
                            selected
                    );

            if (updated != null) {

                productService.updateProduct(
                        updated
                );

                table.refresh();
            }
        });

        deleteButton.setOnAction(e -> {

            Product selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected != null) {

                productService.removeProduct(
                        selected
                );
            }
        });

        HBox buttons =
                new HBox(
                        10,
                        addButton,
                        editButton,
                        deleteButton
                );

        buttons.setPadding(
                new Insets(10)
        );

        BorderPane root =
                new BorderPane();

        root.setCenter(table);
        root.setBottom(buttons);

        return root;
    }
    private BorderPane createTemplatesPane() {

        TableView<Template> table =
                new TableView<>();

        table.setItems(
                templateService.getAllTemplates()
        );

        TableColumn<Template, String> nameCol =
                new TableColumn<>("Название");

        TableColumn<Template, String> goalCol =
                new TableColumn<>("Цель");

        TableColumn<Template, String> resultCol =
                new TableColumn<>("Результат");

        nameCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getName()
                )
        );

        goalCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getGoal()
                )
        );

        resultCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getExpectedResult()
                )
        );

        nameCol.setPrefWidth(300);
        goalCol.setPrefWidth(400);
        resultCol.setPrefWidth(300);

        table.getColumns().addAll(
                nameCol,
                goalCol,
                resultCol
        );

        Button addButton =
                new Button("Добавить");

        Button editButton =
                new Button("Редактировать");

        Button deleteButton =
                new Button("Удалить");

        addButton.setOnAction(e -> {

            TemplateDialog dialog =
                    new TemplateDialog(null);

            dialog.showAndWait()
                    .ifPresent(
                            templateService::addTemplate
                    );
        });

        editButton.setOnAction(e -> {

            Template selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);

                alert.setContentText(
                        "Выберите шаблон."
                );

                alert.showAndWait();

                return;
            }

            TemplateDialog dialog =
                    new TemplateDialog(selected);

            dialog.showAndWait()
                    .ifPresent(
                            templateService::updateTemplate
                    );
        });

        deleteButton.setOnAction(e -> {

            Template selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected != null) {

                templateService.deleteTemplate(
                        selected
                );
            }
        });

        HBox buttons =
                new HBox(
                        10,
                        addButton,
                        editButton,
                        deleteButton
                );

        buttons.setPadding(
                new Insets(10)
        );

        BorderPane root =
                new BorderPane();

        root.setCenter(table);
        root.setBottom(buttons);

        return root;
    }

    private BorderPane createHistoryPane() {

        TableColumn<HistoryRecord, String> dateCol =
                new TableColumn<>("Дата");

        dateCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getDate()
                )
        );

        dateCol.setPrefWidth(220);

        TableView<HistoryRecord> table = new TableView<>();

        TableColumn<HistoryRecord, String> productCol =
                new TableColumn<>("Продукция");

        TableColumn<HistoryRecord, String> regionCol =
                new TableColumn<>("Регион");

        TableColumn<HistoryRecord, String> typeCol =
                new TableColumn<>("Тип компании");

        productCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getProduct()
                )
        );

        regionCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getRegion()
                )
        );

        typeCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getCompanyType()
                )
        );

        table.getColumns().addAll(
                dateCol,
                productCol,
                regionCol,
                typeCol
        );

        table.setItems(
                historyService.getHistory()
        );

        Button exportExcelButton =
                new Button("Экспорт в Excel");

        exportExcelButton.setOnAction(e -> {

            FileChooser fileChooser =
                    new FileChooser();

            fileChooser.setTitle(
                    "Сохранить историю"
            );

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Excel (*.xlsx)",
                            "*.xlsx"
                    )
            );

            fileChooser.setInitialFileName(
                    "history.xlsx"
            );

            File file =
                    fileChooser.showSaveDialog(null);

            if (file == null) {
                return;
            }

            try {

                excelExportService.exportHistory(
                        historyService.getHistory(),
                        file
                );

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);
                alert.setContentText(
                        "История успешно экспортирована."
                );

                alert.showAndWait();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText("Ошибка");

                alert.setContentText(
                        ex.getMessage()
                );

                alert.showAndWait();
            }
        });

        HBox buttons =
                new HBox(exportExcelButton);

        buttons.setPadding(
                new Insets(10)
        );

        BorderPane root =
                new BorderPane();

        root.setCenter(table);
        root.setBottom(buttons);

        return root;
    }
    private BorderPane createCompanyPane() {

        TextField innField =
                new TextField();

        TextField nameField =
                new TextField();

        TextField addressField =
                new TextField();

        TextField okvedField =
                new TextField();

        nameField.setEditable(false);
        addressField.setEditable(false);
        okvedField.setEditable(false);

        Button searchButton =
                new Button("Получить данные");

        searchButton.setOnAction(e -> {

            try {

                CompanyInfo company =
                        daDataService.findCompanyByInn(
                                innField.getText()
                        );

                if (company == null) {

                    Alert alert =
                            new Alert(Alert.AlertType.WARNING);

                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Компания не найдена."
                    );

                    alert.showAndWait();

                    return;
                }

                nameField.setText(
                        company.getName()
                );

                addressField.setText(
                        company.getAddress()
                );

                okvedField.setText(
                        company.getOkved()
                );

            } catch (Exception ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText("Ошибка");

                alert.setContentText(
                        ex.getMessage()
                );

                alert.showAndWait();
            }
        });

        GridPane grid =
                new GridPane();

        grid.setPadding(
                new Insets(20)
        );

        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(
                new Label("ИНН"),
                0,
                0
        );

        grid.add(
                innField,
                1,
                0
        );

        grid.add(
                searchButton,
                2,
                0
        );

        grid.add(
                new Label("Компания"),
                0,
                1
        );

        grid.add(
                nameField,
                1,
                1
        );

        grid.add(
                new Label("Адрес"),
                0,
                2
        );

        grid.add(
                addressField,
                1,
                2
        );

        grid.add(
                new Label("ОКВЭД"),
                0,
                3
        );

        grid.add(
                okvedField,
                1,
                3
        );

        BorderPane root =
                new BorderPane();

        root.setCenter(
                grid
        );

        return root;
    }

    public static void main(String[] args) {
        launch();
    }
}