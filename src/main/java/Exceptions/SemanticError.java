package Exceptions;

import ENUMERATORS.TokenType;

public class SemanticError extends RuntimeException {
    String message;
    TokenType type1, type2;
    int lineNumber;

    public SemanticError(String message, int lineNumber) {
        this.message = message;
        this.lineNumber = lineNumber;
    }

    public SemanticError(TokenType type1, TokenType type2, int lineNumber) {
        this.type1 = type1;
        this.type2 = type2;
        this.lineNumber = lineNumber;
    }

    public String getMessage() {
        if (type1 != null && type2 != null) {
            return "Type missmatch at line " + lineNumber + " : one is " + type1 + " while the other is " + type2;
        }
        return "Semantic error at line " + lineNumber + " : " + message;
    }
}