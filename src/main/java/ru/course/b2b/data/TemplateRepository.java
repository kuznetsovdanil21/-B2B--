package ru.course.b2b.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.course.b2b.model.Template;

import java.sql.*;

public class TemplateRepository {

    private static final String URL =
            "jdbc:sqlite:b2b.db";

    private static final ObservableList<Template> templates =
            FXCollections.observableArrayList();

    static {

        createTable();
        loadTemplates();

        if (templates.isEmpty()) {

            addTemplate(
                    new Template(
                            0,
                            "Анализ производителей матрасов",
                            "Поиск производителей продукции",
                            "ОКВЭД 31.03",
                            "Перечень компаний производителей"
                    )
            );

            addTemplate(
                    new Template(
                            0,
                            "Анализ рынка нетканых материалов",
                            "Поиск поставщиков продукции",
                            "ОКВЭД 13.95",
                            "Перечень поставщиков"
                    )
            );
        }
    }

    private static void createTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS templates(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    goal TEXT,
                    requirements TEXT,
                    expected_result TEXT
                )
                """;

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                Statement statement =
                        connection.createStatement()
        ) {

            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadTemplates() {

        templates.clear();

        String sql =
                "SELECT * FROM templates ORDER BY id";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                Statement statement =
                        connection.createStatement();

                ResultSet rs =
                        statement.executeQuery(sql)
        ) {

            while (rs.next()) {

                templates.add(
                        new Template(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("goal"),
                                rs.getString("requirements"),
                                rs.getString("expected_result")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Template> getTemplates() {
        return templates;
    }

    public static void addTemplate(
            Template template
    ) {

        String sql =
                "INSERT INTO templates(name, goal, requirements, expected_result) VALUES(?,?,?,?)";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    template.getName()
            );

            statement.setString(
                    2,
                    template.getGoal()
            );

            statement.setString(
                    3,
                    template.getRequirements()
            );

            statement.setString(
                    4,
                    template.getExpectedResult()
            );

            statement.executeUpdate();

            loadTemplates();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTemplate(
            Template template
    ) {

        String sql =
                """
                UPDATE templates
                SET name=?,
                    goal=?,
                    requirements=?,
                    expected_result=?
                WHERE id=?
                """;

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    template.getName()
            );

            statement.setString(
                    2,
                    template.getGoal()
            );

            statement.setString(
                    3,
                    template.getRequirements()
            );

            statement.setString(
                    4,
                    template.getExpectedResult()
            );

            statement.setInt(
                    5,
                    template.getId()
            );

            statement.executeUpdate();

            loadTemplates();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTemplate(
            Template template
    ) {

        String sql =
                "DELETE FROM templates WHERE id=?";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    template.getId()
            );

            statement.executeUpdate();

            loadTemplates();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}