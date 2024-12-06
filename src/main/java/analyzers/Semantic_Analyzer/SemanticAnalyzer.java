package analyzers.Semantic_Analyzer;


// import java.util.HashMap;
// import java.util.Map;

// public class SemanticAnalyzer {
//     private Map<String, Symbol> symbolTable;

//     public SemanticAnalyzer() {
//         this.symbolTable = new HashMap<>();
//     }

//     public void analyze(SyntaxNode root) {
//         // Start analyzing from the root node
//         analyzeNode(root);
//     }

//     private void analyzeNode(SyntaxNode node) {
//         switch (node.getType()) {
//             case ASSIGNMENT:
//                 analyzeAssignment(node);
//                 break;
//             case IF_STATEMENT:
//                 analyzeIfStatement(node);
//                 break;
//             case WHILE_LOOP:
//                 analyzeWhileLoop(node);
//                 break;
//             case EXPRESSION:
//                 analyzeExpression(node);
//                 break;
//             // Add more cases as needed
//         }
//     }

//     private void analyzeAssignment(SyntaxNode node) {
//         String identifier = node.getChild(0).getValue();
//         if (!symbolTable.containsKey(identifier)) {
//             // symbolTable.put(identifier, new Symbol(identifier, node.getChild(1).getType()));
//         }
//         analyzeNode(node.getChild(1)); // Analyze the expression
//     }

//     private void analyzeIfStatement(SyntaxNode node) {
//         analyzeNode(node.getChild(0)); // Analyze the condition
//         analyzeNode(node.getChild(1)); // Analyze the statement list
//     }

//     private void analyzeWhileLoop(SyntaxNode node) {
//         analyzeNode(node.getChild(0)); // Analyze the condition
//         analyzeNode(node.getChild(1)); // Analyze the statement list
//     }

//     private void analyzeExpression(SyntaxNode node) {
//         for (SyntaxNode child : node.getChildren()) {
//             analyzeNode(child);
//         }
//         // Additional checks for type compatibility, etc.
//     }
// }



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ENUMERATORS.TokenType;
import Exceptions.SemanticError;
import analyzers.Lexical_Analyzer.Token;

public class SemanticAnalyzer {
    private Map<String, TokenType> symbolTable;

    public SemanticAnalyzer() {
        this.symbolTable = new HashMap<>();
    }

    public void analyze(List<Token> tokens) {
        for (Token token : tokens) {
            if (token.getType() == TokenType.IDENTIFIER) {
                if (!symbolTable.containsKey(token.getValue())) {
                    throw new SemanticError("Undeclared variable: " + token.getValue(), token.getLineNumber());
                }
            } else if (token.getType() == TokenType.KEYWORDINT || token.getType() == TokenType.KEYWORDFLT) {
                symbolTable.put(token.getValue(), token.getType());
            }
        }
    }
}
