package net.seabears.challenge.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class TypedTreeTest {
    private static final String EOL = System.lineSeparator();

    @Test
    public void testDisplay() {
        TypedTree tree = new TypedTree();
        String expected = "# Parent -> Child ChildType" + EOL +
                "A->B ALL" + EOL +
                "A->C ALL" + EOL +
                "A->D HUMAN" + EOL +
                "B->B1 HUMAN" + EOL +
                "B1->B11 ALL" + EOL +
                "B11->B111 BUSINESS" + EOL +
                "C->C1 BUSINESS" + EOL +
                "C1->C11 BUSINESS" + EOL +
                "C1->C12 BUSINESS" + EOL +
                "C12->C121 ALL" + EOL +
                "C12->C122 HUMAN" + EOL +
                "D->D1 HUMAN" + EOL +
                "D1->D11 HUMAN" + EOL +
                "D1->D12 BUSINESS" + EOL +
                "D12->D121 ALL";
        assertEquals(expected, tree.displayTree());
    }

    @Test
    public void testFilterAny() {
        TypedTree tree = new TypedTree();
        assertEquals(Arrays.asList("A", "B", "B1", "B11", "B111", "C", "C1", "C11", "C12", "C121", "C122", "D", "D1", "D11", "D12", "D121"), tree.getFilteredComponents(Type.ALL));
    }

    @Test
    public void testFilterCompany() {
        TypedTree tree = new TypedTree();
        assertEquals(Arrays.asList("A", "B", "C", "C1", "C11", "C12", "C121"), tree.getFilteredComponents(Type.BUSINESS));
    }

    @Test
    public void testFilterPerson() {
        TypedTree tree = new TypedTree();
        assertEquals(Arrays.asList("A", "B", "B1", "B11", "C", "D", "D1", "D11"), tree.getFilteredComponents(Type.HUMAN));
    }

    @Test
    public void testDisplayEmpty() {
        TypedTree tree = new TypedTree("tree-empty.csv");
        assertEquals("# Empty", tree.displayTree());
    }

    @Test
    public void testFilterEmpty() {
        TypedTree tree = new TypedTree("tree-empty.csv");
        assertEquals(Collections.emptyList(), tree.getFilteredComponents(Type.ALL));
    }

    @Test(expected = IllegalStateException.class)
    public void testMultipleRoots() {
        new TypedTree("tree-multiple-roots.csv");
    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicateChildren() {
        new TypedTree("tree-duplicate-children.csv");
    }
}
