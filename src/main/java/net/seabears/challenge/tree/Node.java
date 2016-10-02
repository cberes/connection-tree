package net.seabears.challenge.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Typed Tree nodes
 */
public class Node {
    private String name;
    private Type type;
    private List<Node> children;

    /**
     * Creates a node with the specified name and type
     *
     * @param name node's name
     * @param type node's type
     */
    public Node(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Adds a child to the node
     *
     * @param child new child
     */
    public void addChild(Node child) {
        if (children == null) {
            children = new LinkedList<>();
        }
        children.add(child);
    }

    /**
     * Named of the node
     *
     * @return node's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the node's type
     *
     * @return node's type
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the list of the node's children (or null if no children)
     *
     * @return list of children or null
     */
    public List<Node> getChildren() {
        return children;
    }
}
