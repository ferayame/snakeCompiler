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
                "Snk_Begin", // KEYWORDPRSTR
                "Snk_End", // KEYWORDPREND
                "Snk_Int", // KEYWORDINT
                "Snk_Real", // KEYWORDFLT
                "Snk_Strg", // KEYWORDSTR
                "Snk_Print", // KEYWORDPRINT
                "Begin",
                "End",
                "from",
                "##.*", // COMMENT
                "#", // ENDING
                "If|Else", // CONDITION
                "\\[", // CONDITIONSTR
                "\\]", // CONDITIONEND
                "Set",
                "Get",
                "[a-zA-Z][a-zA-Z0-9_]*", // IDENTIFIER
                "[-+]?[0-9]+\\.?[0-9]*", // FLOAT
                // "[-+]?[0-9]+", // INTEGER
                "=|>|<|>=|<=", // RELOPERATOR
                ",", // Punctuation
                "\".*? \"", // MESSAGE
                ".*" // All the rest
        };

        TokenType[] tokenTypes = {
                TokenType.KEYWORDPRSTR,
                TokenType.KEYWORDPREND,
                TokenType.KEYWORDINT,
                TokenType.KEYWORDFLT,
                TokenType.KEYWORDSTR,
                TokenType.KEYWORDPRINT,
                TokenType.BEGIN,
                TokenType.END,
                TokenType.FROM,
                TokenType.COMMENT,
                TokenType.ENDING,
                TokenType.KEYWORDCND,
                TokenType.CONDITIONSTR,
                TokenType.CONDITIONEND,
                TokenType.SET,
                TokenType.GET,
                TokenType.IDENTIFIER,
                TokenType.NUMBER,
                TokenType.RELOPERATOR,
                TokenType.PUNCTUATION,
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