package ru.course.b2b.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.course.b2b.HistoryRecord;

import java.sql.*;

public class HistoryRepository {

    private static final String URL =
            "jdbc:sqlite:b2b.db";

    private static final ObservableList<HistoryRecord> history =
            FXCollections.observableArrayList();

    static {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        createTable();
        loadHistory();
    }

    private static void createTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS history(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    created_date TEXT,
                    product TEXT,
                    region TEXT,
                    company_type TEXT
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

    private static void loadHistory() {

        history.clear();

        String sql =
                "SELECT * FROM history ORDER BY id DESC";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                Statement statement =
                        connection.createStatement();

                ResultSet rs =
                        statement.executeQuery(sql)
        ) {

            while (rs.next()) {

                history.add(
                        new HistoryRecord(
                                rs.getString("created_date"),
                                rs.getString("product"),
                                rs.getString("region"),
                                rs.getString("company_type")
                        )
                );
            }

            System.out.println(
                    "Загружено записей истории: "
                            + history.size()
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<HistoryRecord> getHistory() {
        return history;
    }

    public static void addRecord(
            HistoryRecord record
    ) {

        System.out.println(
                "Добавляем запись в историю: "
                        + record.getProduct()
        );

        String sql =
                "INSERT INTO history(created_date, product, region, company_type) VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    record.getDate()
            );

            statement.setString(
                    2,
                    record.getProduct()
            );

            statement.setString(
                    3,
                    record.getRegion()
            );

            statement.setString(
                    4,
                    record.getCompanyType()
            );

            int rows =
                    statement.executeUpdate();

            System.out.println(
                    "Добавлено строк: " + rows
            );

            history.add(
                    0,
                    record
            );

            System.out.println(
                    "Текущий размер истории: "
                            + history.size()
            );

        } catch (SQLException e) {

            System.out.println(
                    "Ошибка при сохранении истории"
            );

            e.printStackTrace();
        }
    }
}