package snake_compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import analyzers.Lexical_Analyzer.Lexer;
import analyzers.Lexical_Analyzer.Token;
import analyzers.Syntax_Analyzer.SyntaxAnalyzer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrimaryController {

    @FXML
    private Button upload, close;
    @FXML
    private TextField upload_directory;
    @FXML
    private TextArea original;
    @FXML
    private Tab lextab, syntaxtab, sytab;
    @FXML
    private Label titleBar;
    @FXML
    private TextFlow lexical, semanticm, syntax;

    double x, y;

    @FXML
    void upload(MouseEvent event) {
        final Stage stage = (Stage) upload.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Snake files (*.snk)", "*.snk",
                "*.SNK");

        fileChooser.getExtensionFilters().add(extFilter);
        upload.setOnAction(e -> {
            original.clear();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                upload_directory.setText(file.getAbsolutePath());
                try {
                    Scanner reader = new Scanner(file);
                    lextab.setDisable(false);
                    sytab.setDisable(false);
                    syntaxtab.setDisable(false);
                    while (reader.hasNext()) {
                        original.appendText(reader.nextLine() + "\n");
                    }
                    reader.close();
                } catch (FileNotFoundException e1) {

                }
            } else {
                showMessageWarning();
                original.clear();
                upload_directory.clear();
            }
        });

    }

    @FXML
    void lexicalAnalysis(Event event) {
        lexical.getChildren().clear();
        Lexer lexer = new Lexer();

        if (lextab.isSelected()) {
            String[] lines = original.getText().split("\n");
            for (String line : lines) {
                lexer = new Lexer(line);
                List<Token> tokens = lexer.tokenize();

                for (Token token : tokens) {
                    // lexical.appendText(token + "\n");
                    Text text = new Text(token + "\n");
                    if (text.getText().contains("Unknown")) {
                        text.setFill(Color.RED);
                    } else
                        text.setFill(Color.WHITE);
                    lexical.getChildren().add(text);
                }
            }
        }
    }

    @FXML
    void sytaxicAnalysis(Event event) {
        syntax.getChildren().clear();
        if (syntaxtab.isSelected()) {
            try {
                String[] lines = original.getText().split("\n");
                for (String line : lines) {
                    Lexer lexer = new Lexer(line);
                    List<Token> tokens = lexer.tokenize();

                    SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(tokens);
                    syntaxAnalyzer.parse();
                }
            } catch (RuntimeException e) {
                Text text = new Text(e.toString());
                text.setFill(Color.RED);
                syntax.getChildren().add(text);
            }
        }
    }

    @FXML
    void semanticAnalysis(Event event) {
        // symantic.setText("NOT YET");
        // SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        // // Assume you have a method to convert the parse tree to a SyntaxNode
        // SyntaxNode root = convertParseTreeToSyntaxNode(syntaxAnalyzer);
        // semanticAnalyzer.analyze(root);
    }

    @FXML
    void enableAll(KeyEvent event) {
        String text = original.getText().trim();
        if (!text.isEmpty()) {
            lextab.setDisable(false);
            sytab.setDisable(false);
            syntaxtab.setDisable(false);
        } else {
            lextab.setDisable(true);
            sytab.setDisable(true);
            syntaxtab.setDisable(true);
        }
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void showMessageWarning() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/snake_compiler/errorWindow.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
