package analyzers.Lexical_Analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ENUMERATORS.TokenType;

public class Lexer {
    private String input;
    private int currentPosition;
    private static int lineNumber = 0;
    

    public Lexer(String input) {
        this.input = input;
        this.currentPosition = 0;
        lineNumber += 1;
    }

    public Lexer(){
        this.currentPosition = 0;
        lineNumber = 0;
    }
   
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (currentPosition < input.length()) {
            char currentChar = input.charAt(currentPosition);

            if (Character.isWhitespace(currentChar)) {
                currentPosition++;
                continue;
            }
            Token token = nextToken();
            tokens.add(token);

        }
        return tokens;
    }

    private Token nextToken() {
        if (currentPosition >= input.length()) {
            return null;
        }

        String[] tokenPatterns = {
                "Snk_Begin", // Snk_Begin
                "Snk_End", // Snk_End
                "Snk_Int", // Snk_Int
                "Snk_Real", // Snk_Real
                "Snk_Strg", // Snk_Strg
                "Snk_Print", // Snk_Print
                "Begin",
                "End",
                "from",
                "##.*", // COMMENT
                "#", // EndOfInstruction
                "If|Else", // CONDITION
                "\\[", // OpenSBraquets
                "\\]", // ClosedSBraquets
                "Set",
                "Get",
                "[a-zA-Z][a-zA-Z0-9_]*", // IDENTIFIER
                "[-+]?[0-9]\\.[0-9]*", // not considered
                "[-+]?[0-9]{2}\\.[0-9]*", // Float
                "[-+]?[0-9]+", // INTEGER
                "=|>|<|>=|<=|!=", // RELOPERATOR
                ",", // COMMA
                "\".*?\"", // MESSAGE
                ".*" // All the rest
        };

        TokenType[] tokenTypes = {
                TokenType.Snk_Begin,
                TokenType.Snk_End,
                TokenType.Snk_Int,
                TokenType.Snk_Real,
                TokenType.Snk_Strg,
                TokenType.Snk_Print,
                TokenType.BEGIN,
                TokenType.END,
                TokenType.FROM,
                TokenType.COMMENT,
                TokenType.EndOfInstruction,
                TokenType.IfElse,
                TokenType.OpenSBraquets,
                TokenType.ClosedSBraquets,
                TokenType.SET,
                TokenType.GET,
                TokenType.IDENTIFIER,
                TokenType.UNKNOWN,
                TokenType.FLOAT,
                TokenType.INTEGER,
                TokenType.RELOPERATOR,
                TokenType.COMMA,
                TokenType.MESSAGE,
                TokenType.UNKNOWN
        };

        for (int i = 0; i < tokenPatterns.length; i++) {
            Pattern pattern = Pattern.compile("^" + tokenPatterns[i]);
            Matcher matcher = pattern.matcher(input.substring(currentPosition));

            if (matcher.find()) {
                String value = matcher.group();
                currentPosition += value.length();
                return new Token(tokenTypes[i], value, lineNumber);
            }
        }
        return null;
    }

}