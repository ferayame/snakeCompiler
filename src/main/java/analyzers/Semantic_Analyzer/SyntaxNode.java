package analyzers.Semantic_Analyzer;

import java.util.ArrayList;
import java.util.List;
import ENUMERATORS.NodeType;

public class SyntaxNode {
    private NodeType type;
    private String value;
    private List<SyntaxNode> children;

    public SyntaxNode(NodeType type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public NodeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public List<SyntaxNode> getChildren() {
        return children;
    }

    public SyntaxNode getChild(int index) {
        return children.get(index);
    }

    public void addChild(SyntaxNode child) {
        children.add(child);
    }
}