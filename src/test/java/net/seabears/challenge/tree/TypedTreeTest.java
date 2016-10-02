package net.seabears.challenge.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class TypedTreeTest {
    private static final String DEFAULT_FILE = "tree.csv";

    private static final String EOL = System.lineSeparator();

    @Test
    public void testDisplay() {
        TypedTree tree = new TypedTree(DEFAULT_FILE);
        String expected = "# Parent -> Child ChildType" + EOL +
                "A->B ALL" + EOL +
                "A->C BUSINESS" + EOL +
                "B->D ALL" + EOL +
                "B->E ALL" + EOL +
                "B->F HUMAN" + EOL +
                "D->I ALL" + EOL +
                "I->Q HUMAN" + EOL +
                "I->R ALL" + EOL +
                "E->J BUSINESS" + EOL +
                "E->K HUMAN" + EOL +
                "J->S ALL" + EOL +
                "K->T BUSINESS" + EOL +
                "K->U ALL" + EOL +
                "F->L HUMAN" + EOL +
                "L->V HUMAN" + EOL +
                "L->W HUMAN" + EOL +
                "C->G HUMAN" + EOL +
                "C->H BUSINESS" + EOL +
                "G->M HUMAN" + EOL +
                "H->N BUSINESS" + EOL +
                "H->O BUSINESS" + EOL +
                "H->P ALL";
        assertEquals(expected, tree.displayTree());
    }

    @Test
    public void testFilterAny() {
        TypedTree tree = new TypedTree(DEFAULT_FILE);
        assertEquals(Arrays.asList("A", "B", "D", "I", "Q", "R", "E", "J", "S", "K", "T", "U", "F", "L", "V", "W", "C", "G", "M", "H", "N", "O", "P"), tree.getFilteredComponents(Type.ALL));
    }

    @Test
    public void testFilterBusiness() {
        TypedTree tree = new TypedTree(DEFAULT_FILE);
        assertEquals(Arrays.asList("A", "B", "D", "I", "R", "E", "J", "S", "C", "H", "N", "O", "P"), tree.getFilteredComponents(Type.BUSINESS));
    }

    @Test
    public void testFilterHuman() {
        TypedTree tree = new TypedTree(DEFAULT_FILE);
        assertEquals(Arrays.asList("A", "B", "D", "I", "Q", "R", "E", "K", "U", "F", "L", "V", "W"), tree.getFilteredComponents(Type.HUMAN));
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
