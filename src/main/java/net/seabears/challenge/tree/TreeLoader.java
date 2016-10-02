package net.seabears.challenge.tree;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads a Typed Tree (represented by the root {@link Node}) from lines of CSV input
 */
public class TreeLoader {
    private final List<String> inputData;
    private final Map<String, Node> nodes;
    private Node root;

    private static List<String> fileLines(String file) {
        try {
            return Files.readAllLines(new File(file).toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates a loader for a tree with the specified file
     *
     * @param csv path to CSV file
     */
    public TreeLoader(String csv) {
        this(fileLines(csv));
    }

    /**
     * Creates a loader for a tree with the specified lines of input data
     *
     * @param inputData input data lines
     */
    public TreeLoader(List<String> inputData) {
        this.inputData = inputData;
        this.nodes = new HashMap<>();
    }

    /**
     * Returns the root node (if {@link #load() was not called, null is returned}
     *
     * @return root node (or null if empty or not loaded)
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Loads the tree from the input data. Has no effect if the tree has been loaded already
     */
    public void load() {
        if (root == null) {
            inputData.stream().filter(input -> !isComment(input)).forEach(this::loadInput);
        }
    }

    private boolean isComment(String line) {
        return line.charAt(0) == '#';
    }

    private void loadInput(String input) {
        Node parent = getParentNode(input);
        Node child = getChildNode(input);
        parent.addChild(child);
        nodes.put(child.getName(), child);
    }

    private Node getParentNode(String input) {
        final String name = input.split(",", 2)[0];
        Node node = nodes.get(name);
        if (node == null) {
            // not in node map; must be the root node
            errorIfTwoRoots(name);
            root = node = new Node(name, Type.ALL);
            nodes.put(name, node);
        }
        return node;
    }

    private void errorIfTwoRoots(String name) {
        if (root != null) {
            throw new IllegalStateException("multiple root nodes: " + root.getName() + " and " + name);
        }
    }

    private Node getChildNode(String input) {
        // line is split elsewhere, but splitting here allows for better cohesion
        final String[] edgeParts = input.split(",", 3);
        final String name = edgeParts[1];
        errorIfNodeDefinedTwice(name);
        final Type type = Type.valueOf(edgeParts[2]);
        return new Node(name, type);
    }

    private void errorIfNodeDefinedTwice(String name) {
        if (nodes.containsKey(name)) {
            if (root != null) {
                throw new IllegalStateException("node defined twice: " + name);
            }
        }
    }
}
