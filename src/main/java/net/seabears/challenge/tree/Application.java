package net.seabears.challenge.tree;

import static java.util.Arrays.stream;

public class Application {
    public static void main(String[] args) {
        final TypedTree tree = new TypedTree(args[1]);
        System.out.println(tree.displayTree());
        stream(Type.values()).forEach(type -> System.out.println(type + ": " + tree.getFilteredComponents(type)));
    }
}
