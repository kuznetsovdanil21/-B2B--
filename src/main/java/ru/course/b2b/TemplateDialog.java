package ru.course.b2b;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ru.course.b2b.model.Template;

public class TemplateDialog extends Dialog<Template> {

    public TemplateDialog(Template template) {

        setTitle("Шаблон ТЗ");

        ButtonType saveButton =
                new ButtonType(
                        "Сохранить",
                        ButtonBar.ButtonData.OK_DONE
                );

        getDialogPane()
                .getButtonTypes()
                .addAll(
                        saveButton,
                        ButtonType.CANCEL
                );

        TextField nameField =
                new TextField();

        TextArea goalArea =
                new TextArea();

        TextArea requirementsArea =
                new TextArea();

        TextArea resultArea =
                new TextArea();

        goalArea.setPrefRowCount(3);
        requirementsArea.setPrefRowCount(4);
        resultArea.setPrefRowCount(3);

        if (template != null) {

            nameField.setText(
                    template.getName()
            );

            goalArea.setText(
                    template.getGoal()
            );

            requirementsArea.setText(
                    template.getRequirements()
            );

            resultArea.setText(
                    template.getExpectedResult()
            );
        }

        GridPane pane =
                new GridPane();

        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(
                new Insets(10)
        );

        pane.add(
                new Label("Название"),
                0,
                0
        );

        pane.add(
                nameField,
                1,
                0
        );

        pane.add(
                new Label("Цель"),
                0,
                1
        );

        pane.add(
                goalArea,
                1,
                1
        );

        pane.add(
                new Label("Требования"),
                0,
                2
        );

        pane.add(
                requirementsArea,
                1,
                2
        );

        pane.add(
                new Label("Ожидаемый результат"),
                0,
                3
        );

        pane.add(
                resultArea,
                1,
                3
        );

        getDialogPane().setContent(
                pane
        );

        setResultConverter(button -> {

            if (button == saveButton) {

                return new Template(
                        template == null
                                ? 0
                                : template.getId(),

                        nameField.getText(),

                        goalArea.getText(),

                        requirementsArea.getText(),

                        resultArea.getText()
                );
            }

            return null;
        });
    }
}