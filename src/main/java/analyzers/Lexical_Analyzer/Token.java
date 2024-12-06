package analyzers.Lexical_Analyzer;

import ENUMERATORS.TokenType;

public class Token {
    private TokenType type;
    private String value;
    private int lineNumber;

    public Token(TokenType type, String value, int lineNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        switch (type) {
            case TokenType.KEYWORDPRSTR:
                return value + ": Start of the program " + "[Ln " + lineNumber + "]";

            case TokenType.KEYWORDPREND:
                return value + ": End of the program " + "[Ln " + lineNumber + "]";

            case TokenType.KEYWORDINT:
                return value + ": Integer declaration " + "[Ln " + lineNumber + "]";

            case TokenType.KEYWORDFLT:
                return value + ": Float declaration " + "[Ln " + lineNumber + "]";

            case TokenType.KEYWORDSTR:
                return value + ": String declaration " + "[Ln " + lineNumber + "]";

            case TokenType.COMMENT:
                return value + ": Comment " + "[Ln " + lineNumber + "]";

            case TokenType.ENDING:
                return value + ": End of the instruction " + "[Ln " + lineNumber + "]";

            case TokenType.KEYWORDCND:
                return value + ": Condition keyword " + "[Ln " + lineNumber + "]";

            case TokenType.CONDITIONSTR:
                return value + ": Start of the condition " + "[Ln " + lineNumber + "]";

            case TokenType.CONDITIONEND:
                return value + ": End of the condition " + "[Ln " + lineNumber + "]";

            case TokenType.SET:
                return value + ": Affectation of a value " + "[Ln " + lineNumber + "]";

            case TokenType.GET:
                return value + ": Restoring a value " + "[Ln " + lineNumber + "]";

            case TokenType.FROM:
                return value + ": Keyword " + "[Ln " + lineNumber + "]";

            case TokenType.IDENTIFIER:
                return value + ": Identifier " + "[Ln " + lineNumber + "]";

            case TokenType.NUMBER:
                return value + ": A number " + "[Ln " + lineNumber + "]";

            case TokenType.RELOPERATOR:
                return value + ": Relational Operation " + "[Ln " + lineNumber + "]";

            case TokenType.PUNCTUATION:
                return value + ": Separator " + "[Ln " + lineNumber + "]";

            case TokenType.KEYWORDPRINT:
                return value + ": Printing keyword " + "[Ln " + lineNumber + "]";

            case TokenType.MESSAGE:
                return value + ": Message to be printed " + "[Ln " + lineNumber + "]";

            case TokenType.BEGIN:
                return value + ": Start of the condition bloc " + "[Ln " + lineNumber + "]";

            case TokenType.END:
                return value + ": End of the condition bloc " + "[Ln " + lineNumber + "]";

            default:
                return "Unknown character: " + value + " [Ln " + lineNumber + "]";
        }
    }
}
