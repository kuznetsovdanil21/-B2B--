package ru.course.b2b;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.course.b2b.model.Product;

public class ProductDialog {

    public static Product showDialog() {
        return showDialog(null);
    }

    public static Product showDialog(Product existingProduct) {

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        boolean editMode =
                existingProduct != null;

        stage.setTitle(
                editMode
                        ? "Редактирование продукции"
                        : "Добавление продукции"
        );

        TextField nameField =
                new TextField();

        TextField categoryField =
                new TextField();

        TextField okvedField =
                new TextField();

        TextArea keywordsArea =
                new TextArea();

        keywordsArea.setPrefRowCount(5);

        if (editMode) {

            nameField.setText(
                    existingProduct.getName()
            );

            categoryField.setText(
                    existingProduct.getCategory()
            );

            okvedField.setText(
                    existingProduct.getOkved()
            );

            keywordsArea.setText(
                    existingProduct.getKeywords()
            );
        }

        Button saveButton =
                new Button("Сохранить");

        Button cancelButton =
                new Button("Отмена");

        final Product[] result =
                new Product[1];

        saveButton.setOnAction(e -> {

            if (nameField.getText().trim().isEmpty()
                    || categoryField.getText().trim().isEmpty()
                    || okvedField.getText().trim().isEmpty()) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.WARNING
                        );

                alert.setHeaderText(null);
                alert.setContentText(
                        "Заполните обязательные поля."
                );

                alert.showAndWait();

                return;
            }

            if (editMode) {

                existingProduct.setName(
                        nameField.getText().trim()
                );

                existingProduct.setCategory(
                        categoryField.getText().trim()
                );

                existingProduct.setOkved(
                        okvedField.getText().trim()
                );

                existingProduct.setKeywords(
                        keywordsArea.getText().trim()
                );

                result[0] = existingProduct;

            } else {

                result[0] =
                        new Product(
                                nameField.getText().trim(),
                                categoryField.getText().trim(),
                                okvedField.getText().trim(),
                                keywordsArea.getText().trim()
                        );
            }

            stage.close();
        });

        cancelButton.setOnAction(
                e -> stage.close()
        );

        VBox root = new VBox(12);

        root.setPadding(
                new Insets(20)
        );

        root.getChildren().addAll(

                new Label(
                        "Наименование продукции"
                ),
                nameField,

                new Label(
                        "Категория рынка"
                ),
                categoryField,

                new Label(
                        "ОКВЭД"
                ),
                okvedField,

                new Label(
                        "Ключевые слова"
                ),
                keywordsArea
        );

        HBox buttons =
                new HBox(
                        10,
                        saveButton,
                        cancelButton
                );

        buttons.setAlignment(
                Pos.CENTER_RIGHT
        );

        root.getChildren().add(
                buttons
        );

        Scene scene =
                new Scene(
                        root,
                        650,
                        450
                );

        stage.setScene(scene);

        stage.showAndWait();

        return result[0];
    }
}