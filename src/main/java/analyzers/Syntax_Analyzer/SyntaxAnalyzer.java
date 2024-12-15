package analyzers.Syntax_Analyzer;

import java.util.List;

import ENUMERATORS.TokenType;
import Exceptions.SyntaxError;
import analyzers.Lexical_Analyzer.Token;

public class SyntaxAnalyzer {
    private List<Token> tokens;
    private int currentPosition;

    public SyntaxAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
        this.currentPosition = 0;
    }

    public SyntaxAnalyzer() {

    }

    public void parse() {
        program();
    }

    private void program() {
        expect(TokenType.Snk_Begin);
        ensureEndOfLine();
        statementList();
        expect(TokenType.Snk_End);
    }

    private void statementList() {
        while (currentPosition < tokens.size() && tokens.get(currentPosition).getType() != TokenType.Snk_End
                && tokens.get(currentPosition).getType() != TokenType.END) {
            statement();
            ensureEndOfLine();
        }
    }

    private void statement() {
        Token currentToken = tokens.get(currentPosition);
        if (currentToken.getType() == TokenType.IfElse && currentToken.getValue().equals("If")) {
            ifStatement();
        } else if (currentToken.getType() == TokenType.Snk_Int || currentToken.getType() == TokenType.Snk_Real
                || currentToken.getType() == TokenType.Snk_Strg) {
            declaration();
            expect(TokenType.EndOfInstruction);
        } else if (currentToken.getType() == TokenType.SET) {
            assignmentVal();
            expect(TokenType.EndOfInstruction);
        } else if (currentToken.getType() == TokenType.GET) {
            assignmentVar();
            expect(TokenType.EndOfInstruction);
        } else if (currentToken.getType() == TokenType.Snk_Print) {
            printing();
            expect(TokenType.EndOfInstruction);
        } else if (currentToken.getType() == TokenType.COMMENT) {
            comment();
        } else {
            throw new SyntaxError(currentToken, currentToken.getType());
        }
    }

    private void comment() {
        expect(TokenType.COMMENT);
    }

    private void printing() {
        expect(TokenType.Snk_Print);
        do {
            if (match(TokenType.MESSAGE)) {
                Token string = expect(TokenType.MESSAGE);
            } else if (match(TokenType.IDENTIFIER)) {
                Token identifier = expect(TokenType.IDENTIFIER);
            } else if (match(TokenType.INTEGER)) {
                Token number = expect(TokenType.INTEGER);
            } else if (match(TokenType.FLOAT)) {
                Token number = expect(TokenType.FLOAT);
            } else {
                throw new SyntaxError(TokenType.Snk_Print, tokens.get(currentPosition).getLineNumber());
            }
        } while (matchThenInc(TokenType.COMMA));
    }

    private void declaration() {
        if (matchThenInc(TokenType.Snk_Int) || matchThenInc(TokenType.Snk_Real) || matchThenInc(TokenType.Snk_Strg)) {
            do {
                Token identifier = expect(TokenType.IDENTIFIER);
            } while (matchThenInc(TokenType.COMMA));
        }
    }

    private void assignmentVal() {
        expect(TokenType.SET);
        Token identifier = expect(TokenType.IDENTIFIER);
        if (match(TokenType.INTEGER)) {
            Token value = expect(TokenType.INTEGER);
        } else if (match(TokenType.FLOAT)) {
            Token value = expect(TokenType.FLOAT);
        } else {
            Token value = expect(TokenType.MESSAGE);
        }
    }

    private void assignmentVar() {
        expect(TokenType.GET);
        Token identifier = expect(TokenType.IDENTIFIER);
        expect(TokenType.FROM);
        Token fromIdentifier = expect(TokenType.IDENTIFIER);
    }

    private void ifStatement() {
        expect(TokenType.IfElse, "If");
        expect(TokenType.OpenSBraquets);
        expression();
        expect(TokenType.ClosedSBraquets);
        if (matchThenInc(TokenType.BEGIN)) {
            statementList();
            expect(TokenType.END);
        } else {
            statement();
        }
        if (match(TokenType.IfElse, "Else")) {
            if (matchThenInc(TokenType.BEGIN)) {
                statementList();
                expect(TokenType.END);
            } else {
                statement();
            }
        }
    }

    private void expression() {
        if (match(TokenType.IDENTIFIER)) {
            Token identifier = expect(TokenType.IDENTIFIER);
        } else if (match(TokenType.MESSAGE)) {
            Token msg = expect(TokenType.MESSAGE);
        } else if(match(TokenType.INTEGER)){
            Token val = expect(TokenType.INTEGER);
        } else {
            Token val = expect(TokenType.FLOAT);
        }
        expect(TokenType.RELOPERATOR);
        if (match(TokenType.IDENTIFIER)) {
            Token id = expect(TokenType.IDENTIFIER);
        } else if(match(TokenType.INTEGER)){
            Token val = expect(TokenType.INTEGER);
        } else if(match(TokenType.FLOAT)){
            Token val = expect(TokenType.FLOAT);
        } else {
            Token var = expect(TokenType.MESSAGE);
        }
    }

    private Token expect(TokenType type) {
        Token currentToken = tokens.get(currentPosition);
        Token previousToken = getPreviousToken(currentPosition);

        if (currentToken.getType() == type) {
            currentPosition++;
            return currentToken;
        }
        if (previousToken != null) {
            if (previousToken.getType() == TokenType.GET || previousToken.getType() == TokenType.SET) {
                throw new SyntaxError(currentToken, previousToken.getType());
            } else if (previousToken.getType() == TokenType.FROM) {
                throw new SyntaxError("An identifier was expected after the token 'from' ",
                        currentToken.getLineNumber());
            } else if (previousToken.getType() == TokenType.Snk_Strg || previousToken.getType() == TokenType.Snk_Int
                    || previousToken.getType() == TokenType.Snk_Real) {
                throw new SyntaxError(previousToken.getType(), currentToken.getLineNumber());
            } else if (previousToken.getType() == TokenType.COMMA) {
                throw new SyntaxError("Delete the ',' or add another identifier to complete the declaration", currentToken.getLineNumber());
            }
        }
        throw new SyntaxError(currentToken, type);
    }

    Token getPreviousToken(int currentPosition) {
        if (currentPosition > 0) {
            return tokens.get(currentPosition - 1);
        }
        return null;
    }

    private Token expect(TokenType type, String value) {
        Token currentToken = tokens.get(currentPosition);
        if (currentToken.getType() == type && currentToken.getValue().equals(value)) {
            currentPosition++;
            return currentToken;
        } else {
            throw new SyntaxError(currentToken, type);
        }
    }

    private boolean match(TokenType type) {
        Token currentToken = tokens.get(currentPosition);
        if (currentToken.getValue().contains("\"") && currentToken.getType() == TokenType.UNKNOWN) {
            throw new SyntaxError(currentToken, TokenType.MESSAGE);
        } else {
            if (currentToken.getType() == type) {
                return true;
            }
            return false;
        }
    }

    private boolean matchThenInc(TokenType type) {
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

    private void ensureEndOfLine() {
        if (currentPosition < tokens.size()) {
            Token currentToken = tokens.get(currentPosition);
            Token previousToken = tokens.get(currentPosition - 1);
            if (currentToken.getLineNumber() == previousToken.getLineNumber()) {
                throw new SyntaxError("Expected end of line but found additional tokens", currentToken.getLineNumber());
            }
        }
    }
}