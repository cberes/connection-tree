package net.seabears.challenge.tree;

import java.util.*;

public class TypedTree {
    private final Node root;

    public TypedTree(String filename) {
        final TreeLoader loader = new TreeLoader(filename);
        loader.load();
        root = loader.getRoot();
    }

    /**
     * After the tree is built, it can be printed.
     * Tree looks like the following (including the comment):
     * <pre>
     *     # Parent -> Child ChildType
     *     A->B HUMAN
     *     B->C ALL
     *     C->D BUSINESS
     *     D->E HUMAN
     *     A->F ALL
     *     F->G BUSINESS
     * </pre>
     *
     * @return tree in text format
     */
    public String displayTree() {
        if (root == null) {
            return "# Empty";
        } else {
            StringBuilder display = new StringBuilder("# Parent -> Child ChildType");
            displayTree(root, display);
            return display.toString();
        }
    }

    private void displayTree(Node node, StringBuilder acc) {
        List<Node> children = node.getChildren();
        if (children != null) {
            children.forEach(child -> displayEdge(node, child, acc));
            children.forEach(child -> displayTree(child, acc));
        }
    }

    private void displayEdge(Node parent, Node child, StringBuilder acc) {
        // appending EOL first with each new entry ensures no trailing EOL
        acc.append(System.lineSeparator())
                .append(parent.getName())
                .append("->")
                .append(child.getName())
                .append(' ')
                .append(child.getType());
    }

    /**
     * Given a fully 'typed' tree, filter out irrelevant sections.
     * A few examples:
     * (A)ALL -> (B)HUMAN -> (C)ALL -> (D)BUSINESS -> (E)HUMAN
     * -> (F)ALL -> (G)BUSINESS
     * - Filtered by ALL, produces all 7 nodes
     * - Filtered by HUMAN, produces A, B, C, F (hits D and stops following the tree, and G is BUSINESS)
     * - Filtered by BUSINESS, produces A, F, G (Despite D in the tree, since B is a HUMAN, all subsequent nodes are filtered)
     *
     * @param type
     * @return The list of Nodes that pass the filter.
     */
    public List<String> getFilteredComponents(Type type) {
        return root == null ? Collections.emptyList() : getFilteredComponents(root, type, new LinkedList<>());
    }

    private List<String> getFilteredComponents(Node node, Type type, List<String> acc) {
        if (isAllowed(node, type)) {
            acc.add(node.getName());
            List<Node> children = node.getChildren();
            if (children != null) {
                children.forEach(child -> getFilteredComponents(child, type, acc));
            }
        }
        return acc;
    }

    private boolean isAllowed(Node node, Type filter) {
        return filter == Type.ALL || node.getType() == Type.ALL || filter == node.getType();
    }
}
