package ru.job4j.tree.simple;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for the SimpTree class.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 10.03.2018
 */
public class SimpTreeTest {

    /**
     * Test add() and findBy()
     */
    @Test
    public void whenAddThenRejectsDuplicateChildrenAndFindsValue() {
        SimpTree<Integer> tree = new SimpTree<>(1);
        assertThat(tree.add(1, 2), is(true));
        assertThat(tree.add(1, 2), is(false)); // duplicate
        assertThat(tree.add(1, 3), is(true));
        assertThat(tree.add(1, 4), is(true));
        assertThat(tree.add(4, 5), is(true));
        assertThat(tree.add(4, 5), is(false)); //duplicate
        assertThat(tree.add(4, 5), is(false)); //duplicate
        assertThat(tree.add(5, 6), is(true));
        assertThat(tree.findBy(6).isPresent(), is(true));
        assertThat(tree.findBy(4).isPresent(), is(true));
        assertThat(tree.findBy(1).isPresent(), is(true));
        assertThat(tree.findBy(7).isPresent(), is(false)); //not existing
        assertThat(tree.findBy(23).isPresent(), is(false));
        assertThat(tree.findBy(5).isPresent(), is(true));
    }

    @Test
    public void whenAddThenRejectsAnyDuplicates() {
        SimpTree<Integer> tree = new SimpTree<>(1);
        assertThat(tree.add(1, 2), is(true));
        assertThat(tree.add(1, 3), is(true));
        assertThat(tree.add(2, 1), is(false));
        assertThat(tree.add(2, 2), is(false));
        assertThat(tree.add(2, 3), is(false));
        assertThat(tree.add(3, 1), is(false));
        assertThat(tree.add(3, 2), is(false));
        assertThat(tree.add(3, 3), is(false));
    }

    /**
     * Test iterator(), next() and hasNext().
     */
    @Test(expected = NoSuchElementException.class)
    public void whenIteratorThenWorksAsExpected() {
        SimpTree<String> tree = new SimpTree<>("1");
        tree.add("1", "2");
        tree.add("1", "3"); // let's check it really doesn't add duplicates
        tree.add("1", "3");
        tree.add("1", "3");
        tree.add("1", "4");
        tree.add("4", "5");
        tree.add("5", "6");
        Iterator<String> iterator = tree.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("1"));       //root "1"
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("2"));       //children of root "1"
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("3"));               // duplicate check
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("4"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("5"));       //child of "4"
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("6"));       //child of "5"
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }

    /**
     * Test isBinary()
     */
    @Test
    public void whenBinaryThenIsBinaryReturnsTrueAndViceVersa() {
        SimpTree<String> tree = new SimpTree<>("1");
        tree.add("1", "1-1");
        tree.add("1", "1-2");
        tree.add("1-1", "1-1-1");
        tree.add("1-2", "1-2-1");
        tree.add("1-2", "1-2-2");
        tree.add("1-1-1", "1-1-1-1");
        tree.add("1-1-1", "1-1-1-2");
        assertThat(tree.isBinary(), is(true));
        tree.add("1-1-1", "1-1-1-3"); // now not binary
        assertThat(tree.isBinary(), is(false));
    }

}