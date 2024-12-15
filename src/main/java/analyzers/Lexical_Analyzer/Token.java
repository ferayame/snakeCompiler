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
            case TokenType.Snk_Begin:
                return value + ": Start of the program " + "[Ln " + lineNumber + "]";

            case TokenType.Snk_End:
                return value + ": End of the program " + "[Ln " + lineNumber + "]";

            case TokenType.Snk_Int:
                return value + ": Integer declaration " + "[Ln " + lineNumber + "]";

            case TokenType.Snk_Real:
                return value + ": Float declaration " + "[Ln " + lineNumber + "]";

            case TokenType.Snk_Strg:
                return value + ": String declaration " + "[Ln " + lineNumber + "]";

            case TokenType.COMMENT:
                return value + ": Comment " + "[Ln " + lineNumber + "]";

            case TokenType.EndOfInstruction:
                return value + ": End of the instruction " + "[Ln " + lineNumber + "]";

            case TokenType.IfElse:
                return value + ": Condition keyword " + "[Ln " + lineNumber + "]";

            case TokenType.OpenSBraquets:
                return value + ": Start of the condition " + "[Ln " + lineNumber + "]";

            case TokenType.ClosedSBraquets:
                return value + ": End of the condition " + "[Ln " + lineNumber + "]";

            case TokenType.SET:
                return value + ": Affectation of a value " + "[Ln " + lineNumber + "]";

            case TokenType.GET:
                return value + ": Restoring a value " + "[Ln " + lineNumber + "]";

            case TokenType.FROM:
                return value + ": Keyword " + "[Ln " + lineNumber + "]";

            case TokenType.IDENTIFIER:
                return value + ": Identifier " + "[Ln " + lineNumber + "]";

            case TokenType.INTEGER:
                return value + ": An Integer " + "[Ln " + lineNumber + "]";

            case TokenType.FLOAT:
                return value + ": A Float " + "[Ln " + lineNumber + "]";

            case TokenType.RELOPERATOR:
                return value + ": Relational Operation " + "[Ln " + lineNumber + "]";

            case TokenType.COMMA:
                return value + ": Separator " + "[Ln " + lineNumber + "]";

            case TokenType.Snk_Print:
                return value + ": Printing keyword " + "[Ln " + lineNumber + "]";

            case TokenType.MESSAGE:
                return value + ": Message to be printed " + "[Ln " + lineNumber + "]";

            case TokenType.BEGIN:
                return value + ": Start of the block inside the IF/Else " + "[Ln " + lineNumber + "]";

            case TokenType.END:
                return value + ": End of the condition bloc " + "[Ln " + lineNumber + "]";

            default:
                return "Unknown character: " + value + " [Ln " + lineNumber + "]";
        }
    }
}
