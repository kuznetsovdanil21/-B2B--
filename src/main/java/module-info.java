module ru.course.b2b {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.net.http;

    requires org.xerial.sqlitejdbc;

    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    requires org.json;

    opens ru.course.b2b;
    opens ru.course.b2b.model;

    exports ru.course.b2b;
    exports ru.course.b2b.model;
}