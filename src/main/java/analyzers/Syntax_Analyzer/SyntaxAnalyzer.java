package analyzers.Syntax_Analyzer;

import java.util.List;

import ENUMERATORS.TokenType;
import Exceptions.SyntaxError;
import analyzers.Lexical_Analyzer.Token;

public class SyntaxAnalyzer {
    private List<Token> tokens;
    private int currentPosition;
    private int lineNumber;

    public SyntaxAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
        this.currentPosition = 0;
        this.lineNumber = 1;
    }

    public void parse() {
        program();
    }

    private void program() {
        expect(TokenType.KEYWORDPRSTR);
        statementList();
        expect(TokenType.KEYWORDPREND);
    }

    private void statementList() {
        while (currentPosition < tokens.size() && tokens.get(currentPosition).getType() != TokenType.KEYWORDPREND) {
            statement();
        }
    }

    private void statement() {
        Token currentToken = tokens.get(currentPosition);
        if (currentToken.getType() == TokenType.KEYWORDCND && currentToken.getValue().equals("If")) {
            ifStatement();
        } else if (currentToken.getType() == TokenType.KEYWORDINT || currentToken.getType() == TokenType.KEYWORDFLT) {
            declaration();
        } else if (currentToken.getType() == TokenType.SET) {
            assignmentInt();
        } else if (currentToken.getType() == TokenType.GET) {
            assignmentVar();
        } else if (currentToken.getType() == TokenType.KEYWORDPRINT) {
            printing();
        } else if (currentToken.getType() == TokenType.COMMENT) {
            comment();
        } else {
            throw new SyntaxError("Unexpected token: " + currentToken, currentToken.getLineNumber());
        }
        expect(TokenType.ENDING);
    }

    private void comment() {
        expect(TokenType.COMMENT);
    }

    // DONE
    private void printing() {
        expect(TokenType.KEYWORDPRINT);
        do {
            if (match(TokenType.MESSAGE)) {
                Token string = expect(TokenType.MESSAGE);
            } else {
                Token identifier = expect(TokenType.IDENTIFIER);
            }
        } while (match(TokenType.PUNCTUATION));
    }

    // DONE
    private void declaration() {
        Token type = expect(TokenType.KEYWORDINT, TokenType.KEYWORDFLT);
        do {
            Token identifier = expect(TokenType.IDENTIFIER);
        } while (match(TokenType.PUNCTUATION));

    }

    // DONE
    private void assignmentInt() {
        expect(TokenType.SET);
        Token identifier = expect(TokenType.IDENTIFIER);
        Token value = expect(TokenType.NUMBER);
    }

    // DONE 
    private void assignmentVar() {
        expect(TokenType.GET);
        Token identifier = expect(TokenType.IDENTIFIER);
        expect(TokenType.FROM);
        Token fromIdentifier = expect(TokenType.IDENTIFIER);
    }

    // DONE
    private void ifStatement() {
        expect(TokenType.CONDITIONEND, "If");
        expect(TokenType.CONDITIONSTR);
        expression();
        expect(TokenType.CONDITIONEND);
        if (match(TokenType.BEGIN)) {
            statementList();
            expect(TokenType.END);
        } else {
            statement();
        }
        if (match(TokenType.CONDITIONEND, "Else")) {
            if (match(TokenType.BEGIN)) {
                statementList();
                expect(TokenType.END);
            } else {
                statement();
            }
        }
    }

    private void expression() {
        Token identifier = expect(TokenType.IDENTIFIER);
        expect(TokenType.RELOPERATOR);
        if(match(TokenType.IDENTIFIER)){
            Token id = expect(TokenType.IDENTIFIER);
        } else{
            Token val = expect(TokenType.NUMBER);
        }
    }

    private Token expect(TokenType... types) {
        Token currentToken = tokens.get(currentPosition);
        for (TokenType type : types) {
            if (currentToken.getType() == type) {
                currentPosition++;
                return currentToken;
            }
        }
        throw new SyntaxError("Expected token of type " + types + " but found " + currentToken,
                currentToken.getLineNumber());

    }

    private Token expect(TokenType type, String value) {
        Token currentToken = tokens.get(currentPosition);
        if (currentToken.getType() == type && currentToken.getValue().equals(value)) {
            currentPosition++;
            return currentToken;
        } else {
            throw new RuntimeException(
                    "Expected token of type " + type + " with value " + value + " but found " + currentToken);
        }
    }

    private boolean match(TokenType type) {
        Token currentToken = tokens.get(currentPosition);
        if (currentToken.getType() == type) {
            currentPosition++;
            return true;
        }
        return false;
    }
    private boolean match(TokenType type, String value) {
        Token currentToken = tokens.get(currentPosition);
        if (currentToken.getType() == type && currentToken.getValue().equals(value)) {
            currentPosition++;
            return true;
        }
        return false;
    }
}