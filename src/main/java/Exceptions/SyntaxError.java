package Exceptions;

import ENUMERATORS.TokenType;
import analyzers.Lexical_Analyzer.Token;

public class SyntaxError extends RuntimeException {
	private String errme;
	private int ln;
	private TokenType type;
	private Token token;

	public SyntaxError(Token token, TokenType type) {
		this.token = token;
		this.type = type;
	}

	public SyntaxError(String errme, int ln) {
		this.errme = errme;
		this.ln = ln;
	}

	public SyntaxError(TokenType type, int ln) {
		this.type = type;
		this.ln = ln;
	}

	public SyntaxError(Token token) {
		this.token = token;
		type = token.getType();
	}

	public SyntaxError(TokenType type) {
		this.type = type;
	}

	public String toString() {
		if (errme != null) {
			return errme + "[Ln " + ln + "]\n";
		} else {
			switch (type) {
				case TokenType.Snk_Begin:
					return "The program must start with \'Snk_Begin\' [Ln " + token.getLineNumber() + "]\n";

				case TokenType.Snk_End:
					return "The program must end with \'Snk_End\' [Ln " + token.getLineNumber() + "]\n";

				case TokenType.FLOAT:
				case TokenType.INTEGER:
					return "Syntax Error, The assignment must be in the form (Set Identifier Number#) [Ln "
							+ token.getLineNumber()
							+ "]\n";

				case TokenType.IDENTIFIER:
					return "Syntax Error, insert a variable declaration to complete the variable declaration [Ln "
							+ token.getLineNumber() + "]\n";

				case TokenType.Snk_Int:
				case TokenType.Snk_Strg:
				case TokenType.Snk_Real:
					return "Syntax Error, the declaration must follow this format 'Snk_Int/Snk_Real/Snk_Strg id1, id2,... #' to complete the variable declaration [Ln "
							+ ln + "]\n";

				case TokenType.EndOfInstruction:
					return "Syntax Error, insert \'#\' to complete the statement" + " [Ln "
							+ (token.getLineNumber() - 1)
							+ "]\n";

				case TokenType.IfElse:
					return "Expected \'If/Else\', but found " + token.getValue() + " [Ln " + token.getLineNumber()
							+ "]\n";

				case TokenType.OpenSBraquets:
					return "Syntax Error on token \"If\", [ expected after it [Ln " + token.getLineNumber() + "]\n";

				case TokenType.ClosedSBraquets:
					return "Syntax Error, ] expected after a condition [Ln " + token.getLineNumber() + "]\n";

				case TokenType.RELOPERATOR:
					return "A relational Operation is expected [Ln " + token.getLineNumber() + "]\n";

				case TokenType.MESSAGE:
					return "Missing a double-quote [Ln " + token.getLineNumber() + "]\n";
				case TokenType.END:
					return "Syntax Error, Insert 'End' to complete the block [Ln " + token.getLineNumber() + "]\n";
				case TokenType.UNKNOWN:
					return "Syntax Error, You have to verify [Ln " + token.getLineNumber() + "]\n";

				case TokenType.GET:
				case TokenType.SET:
					return "Expected identifier after " + type + " [Ln " + token.getLineNumber() + "]\n";

				case TokenType.Snk_Print:
					return "Expected identifier or a message or a value after " + type + " [Ln " + ln + "]\n";

				default:
					return "Syntax Error  [Ln " + token.getLineNumber() + "] " + token.getType() + "\n";
			}
		}
	}
}
