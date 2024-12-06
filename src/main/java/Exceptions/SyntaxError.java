package Exceptions;

import ENUMERATORS.TokenType;
import analyzers.Lexical_Analyzer.Token;

public class SyntaxError extends RuntimeException {
	private String errorName;
	private int ln;
	private TokenType type;
	private Token token;
	
	public SyntaxError(String errorName, int ln) {
		this.errorName = errorName;
		this.ln = ln + 1;
	}
	public SyntaxError(TokenType type, Token token){
		this.token = token;
		this.type = type;
	}
	
	// public String toString() {
	// 	return "Error: " + errorName + " at line " + ln + "\n";
	// }
	public String toString() {
		return "Expected token of type " + type + " but found " + token;
	}
}
