module snake_compiler {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens snake_compiler to javafx.fxml;
    exports snake_compiler;
}