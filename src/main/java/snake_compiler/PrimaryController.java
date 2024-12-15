package snake_compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import analyzers.Lexical_Analyzer.Lexer;
import analyzers.Lexical_Analyzer.Token;
import analyzers.Semantic_Analyzer.SemanticAnalyzer;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private Tab content, lextab, syntaxtab, sytab;
    @FXML
    private Label titleBar;
    @FXML
    private TextFlow lexical, semantic, syntax;
    @FXML
    private TabPane tabPane;

    double x, y;

    List<Token> allTokens = new LinkedList<>();
    SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();

    Text noError = new Text();

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
                tabPane.getSelectionModel().select(content);
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

    void gettingTokens() {
        Lexer lexer = new Lexer();
        allTokens.clear();
        String[] lines = original.getText().split("\n");
        for (String line : lines) {
            lexer = new Lexer(line);
            List<Token> tokens = lexer.tokenize();
            for (Token token : tokens) {
                allTokens.add(token);
            }
        }
    }

    @FXML
    void lexicalAnalysis(Event event) {
        lexical.getChildren().clear();

        if (lextab.isSelected()) {
            gettingTokens();
            for (Token token : allTokens) {
                Text text = new Text(token + "\n");
                if (text.getText().contains("Unknown")) {
                    text.setFill(Color.RED);
                } else
                    text.setFill(Color.WHITE);
                lexical.getChildren().add(text);
            }
        }
    }

    @FXML
    void sytaxicAnalysis(Event event) {
        syntax.getChildren().clear();
        if (syntaxtab.isSelected()) {
            try {
                gettingTokens();
                if (!allTokens.isEmpty()) {
                    syntaxAnalyzer = new SyntaxAnalyzer(allTokens);
                    syntaxAnalyzer.parse();

                    noError = new Text("--------- NO SYNTAX ERROR ---------");
                    noError.setFill(Color.BEIGE);
                    noError.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 13));
                    syntax.getChildren().add(noError);
                }
            } catch (RuntimeException e) {
                noError.setText("");
                Text text = new Text(e.toString());
                text.setFill(Color.RED);
                text.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 13));
                syntax.getChildren().add(text);
            }
        }

    }

    @FXML
    void semanticAnalysis(Event event) {
        semantic.getChildren().clear();
        if (!noError.getText().isEmpty()) {
            if (sytab.isSelected()) {
                try {
                    gettingTokens();
                    semanticAnalyzer = new SemanticAnalyzer(allTokens);
                    semanticAnalyzer.analyze();

                    Text text = new Text("--------- NO PROBLEM WERE FOUND ---------");
                    text.setFill(Color.BEIGE);
                    text.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 13));
                    semantic.getChildren().add(text);

                } catch (RuntimeException e) {
                    Text text = new Text(e.getMessage());
                    text.setFill(Color.RED);
                    text.setFont(Font.font("Bahnschrift", FontWeight.MEDIUM, 13));
                    semantic.getChildren().add(text);
                }
            }
        } else {
            Text text = new Text("CANNOT PROCEED WITH THE SEMANTIC ANALYSIS, CHECK THE SYNTAX FIRST");
            text.setFill(new Color(0.69, 0.71, 1, 1));
            text.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 13));
            semantic.getChildren().add(text);
        }
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
