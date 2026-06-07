package ru.course.b2b.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.course.b2b.model.Product;

import java.sql.*;

public class ProductRepository {

    private static final String URL = "jdbc:sqlite:b2b.db";

    private static final ObservableList<Product> products =
            FXCollections.observableArrayList();

    static {

        System.out.println("=== ProductRepository initialized ===");

        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver NOT loaded");
            e.printStackTrace();
        }

        createTable();
        loadProducts();

        System.out.println("Loaded products: " + products.size());

        if (products.isEmpty()) {

            System.out.println("Adding default products");

            addProduct(
                    new Product(
                            "Матрасы",
                            "Мебельная промышленность",
                            "31.03",
                            "матрас, ортопедический матрас, беспружинный матрас"
                    )
            );

            addProduct(
                    new Product(
                            "Нетканые материалы",
                            "Текстильная промышленность",
                            "13.95",
                            "нетканые материалы, спанбонд, геотекстиль"
                    )
            );

            addProduct(
                    new Product(
                            "Сокосодержащие напитки",
                            "Пищевая промышленность",
                            "11.07",
                            "сокосодержащий напиток, фруктовый напиток, напиток из сока"
                    )
            );
        }
    }

    private static void createTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS products(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    category TEXT NOT NULL,
                    okved TEXT NOT NULL,
                    keywords TEXT
                )
                """;

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                Statement statement =
                        connection.createStatement()
        ) {

            statement.execute(sql);
            System.out.println("Table created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadProducts() {

        products.clear();

        String sql = "SELECT * FROM products";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                Statement statement =
                        connection.createStatement();

                ResultSet rs =
                        statement.executeQuery(sql)
        ) {

            while (rs.next()) {

                products.add(
                        new Product(
                                rs.getString("name"),
                                rs.getString("category"),
                                rs.getString("okved"),
                                rs.getString("keywords")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Product> getProducts() {

        System.out.println(
                "getProducts called. Size = "
                        + products.size()
        );

        return products;
    }

    public static void addProduct(Product product) {

        String sql =
                "INSERT INTO products(name, category, okved, keywords) VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setString(3, product.getOkved());
            statement.setString(4, product.getKeywords());

            statement.executeUpdate();

            products.add(product);

            System.out.println("Added product: " + product.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeProduct(Product product) {

        String sql =
                "DELETE FROM products WHERE name = ?";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, product.getName());

            statement.executeUpdate();

            products.remove(product);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateProduct(Product product) {

        String sql =
                "UPDATE products " +
                        "SET category = ?, okved = ?, keywords = ? " +
                        "WHERE name = ?";

        try (
                Connection connection =
                        DriverManager.getConnection(URL);

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    product.getCategory()
            );

            statement.setString(
                    2,
                    product.getOkved()
            );

            statement.setString(
                    3,
                    product.getKeywords()
            );

            statement.setString(
                    4,
                    product.getName()
            );

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}