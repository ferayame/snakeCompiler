package Exceptions;

public class SemanticError extends RuntimeException {
    public SemanticError(String message, int lineNumber) {
        super("Semantic error at line " + lineNumber + ": " + message);
    }
}