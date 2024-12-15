package analyzers.Semantic_Analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ENUMERATORS.TokenType;
import Exceptions.SemanticError;
import analyzers.Lexical_Analyzer.Token;

public class SemanticAnalyzer {
    private Map<String, TokenType> symbolTable;
    private List<Token> tokens;
    private int currentPosition;

    public SemanticAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
        this.symbolTable = new HashMap<>();
        this.currentPosition = 0;
    }

    public SemanticAnalyzer() {

    }

    public void analyze() {
        while (currentPosition < tokens.size()) {
            Token currentToken = tokens.get(currentPosition);
            if (currentToken.getType() == TokenType.Snk_Int || currentToken.getType() == TokenType.Snk_Real
                    || currentToken.getType() == TokenType.Snk_Strg) {
                processDeclaration();
            } else if (currentToken.getType() == TokenType.SET || currentToken.getType() == TokenType.GET) {
                processAssignment();
            } else if (currentToken.getType() == TokenType.Snk_Print) {
                processPrinting();
            }
            currentPosition++;
        }
    }

    private void processDeclaration() {
        Token token = tokens.get(currentPosition);
        TokenType tokenType = token.getType();
        currentPosition++;
        token = tokens.get(currentPosition);
        while (currentPosition < tokens.size() && token.getType() != TokenType.EndOfInstruction) {
            if (token.getType() == TokenType.IDENTIFIER) {
                if (symbolTable.containsKey(token.getValue())) {
                    throw new SemanticError("Variable '" + token.getValue() + "' is already declared",
                            token.getLineNumber());
                }
                if (tokenType == TokenType.Snk_Int) {
                    symbolTable.put(token.getValue(), TokenType.INTEGER);
                } else if (tokenType == TokenType.Snk_Real){
                    symbolTable.put(token.getValue(), TokenType.FLOAT);
                } else {
                    symbolTable.put(token.getValue(), TokenType.MESSAGE);
                }
                currentPosition++;
            } else if (token.getType() == TokenType.COMMA) {
                currentPosition++;
            }
            token = tokens.get(currentPosition);
        }
    }

    private void processAssignment() {
        Token operation = tokens.get(currentPosition);
        currentPosition++;
        Token identifier = tokens.get(currentPosition);

        if (!symbolTable.containsKey(identifier.getValue())) {
            throw new SemanticError("Variable '" + identifier.getValue() + "' is not declared",
                    identifier.getLineNumber());
        }

        if(operation.getType() == TokenType.SET){
            currentPosition++;
            Token value = tokens.get(currentPosition);
            if (symbolTable.get(identifier.getValue()) != value.getType()) {
                throw new SemanticError(symbolTable.get(identifier.getValue()),value.getType(), identifier.getLineNumber());
            }
        }

        if (operation.getType() == TokenType.GET) {
            currentPosition += 2;
            Token fromIdentifier = tokens.get(currentPosition);
            if (!symbolTable.containsKey(fromIdentifier.getValue())) {
                throw new SemanticError("Variable '" + fromIdentifier.getValue() + "' is not declared",
                        fromIdentifier.getLineNumber());
            } else if (symbolTable.get(identifier.getValue()) != symbolTable.get(fromIdentifier.getValue())) {
                throw new SemanticError(symbolTable.get(identifier.getValue()), symbolTable.get(fromIdentifier.getValue()), identifier.getLineNumber());
            }
        }
    }

    private void processPrinting() {
        currentPosition++;
        while (currentPosition < tokens.size() && tokens.get(currentPosition).getType() != TokenType.EndOfInstruction) {
            Token token = tokens.get(currentPosition);
            if (token.getType() == TokenType.IDENTIFIER) {
                if (!symbolTable.containsKey(token.getValue())) {
                    throw new SemanticError("Variable '" + token.getValue() + "' is not declared",
                            token.getLineNumber());
                }
            }
            currentPosition++;
            if (tokens.get(currentPosition).getType() == TokenType.COMMA) {
                currentPosition++;
            }
        }
    }
}
