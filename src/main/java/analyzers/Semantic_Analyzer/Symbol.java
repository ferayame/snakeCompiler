package analyzers.Semantic_Analyzer;

import ENUMERATORS.TokenType;

public class Symbol {
    private String name;
    private TokenType type;

    public Symbol(String name, TokenType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TokenType getType() {
        return type;
    }
}
