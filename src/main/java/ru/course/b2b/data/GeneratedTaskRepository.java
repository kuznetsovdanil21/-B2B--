package ru.course.b2b.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.course.b2b.model.GeneratedTask;

import java.sql.*;

public class GeneratedTaskRepository {

    private static final String URL =
            "jdbc:sqlite:b2b.db";

    private static final ObservableList<GeneratedTask> tasks =
            FXCollections.observableArrayList();

    static {
        createTable();
        loadTasks();
    }

    private static void createTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS generated_tasks(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    created_date TEXT,
                    title TEXT,
                    content TEXT
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

    private static void loadTasks() {

        tasks.clear();

        String sql =
                "SELECT * FROM generated_tasks ORDER BY id DESC";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                Statement statement =
                        connection.createStatement();

                ResultSet rs =
                        statement.executeQuery(sql)
        ) {

            while (rs.next()) {

                tasks.add(
                        new GeneratedTask(
                                rs.getInt("id"),
                                rs.getString("created_date"),
                                rs.getString("title"),
                                rs.getString("content")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<GeneratedTask> getTasks() {
        return tasks;
    }

    public static void addTask(
            GeneratedTask task
    ) {

        String sql =
                "INSERT INTO generated_tasks(created_date,title,content) VALUES(?,?,?)";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    task.getCreatedDate()
            );

            statement.setString(
                    2,
                    task.getTitle()
            );

            statement.setString(
                    3,
                    task.getContent()
            );

            statement.executeUpdate();

            loadTasks();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}