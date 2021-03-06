package ru.job4j.sort.departments;

import java.util.*;

/**
 * Contains node hierarchy and sorts nodes of the same level in given order.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 22.02.2018
 */
public class Sorter {

    /**
     * Set of head departments.
     */
    private final Set<Node> headNodes;

    /**
     * Comparator for nodes and sub-nodes.
     */
    private final Comparator<Node> comparator;

    /**
     * Constructor with given comparator.
     *
     * @param comparator comparator for nodes and sub-nodes.
     */
    Sorter(Comparator<Node> comparator) {
        this.comparator = comparator;
        this.headNodes = new TreeSet<>(comparator);
    }

    /**
     * Default constructor, use natural order sort for nodes and sub-nodes.
     */
    Sorter() {
        this(Comparator.naturalOrder());
    }

    /**
     * Sort given hierarchy array and return sorted Array.
     *
     * @param arr given array.
     */
    String[] sortArray(String[] arr) {
        this.parseArray(arr);
        return this.toStringArray();
    }

    /**
     * Parse array of strings with node hierarchy.
     *
     * @param arr array of string to parse.
     */
    private void parseArray(String[] arr) {
        for (String str : arr) {
            this.parseString(str);
        }
    }

    /**
     * Parse string with node hierarchy and add elements to set.
     *
     * @param hierarchy given string.
     */
    private void parseString(String hierarchy) {
        String[] str = hierarchy.split("\\\\");
        Node current = addOrGet(new Node(str[0], this.comparator));
        for (int i = 1; i < str.length; i++) {
            current = current.addOrGetSub(new Node(str[i], this.comparator));
        }
    }

    /**
     * Return array of strings with node hierarchy stored now.
     */
    private String[] toStringArray() {
        List<String> buffer = new LinkedList<>();
        for (Node head : this.headNodes) {
            buffer.addAll(Arrays.asList(head.toStringArray()));
        }
        return buffer.toArray(new String[0]);
    }

    /**
     * Add new head node or get one if exists.
     *
     * @param headNode node to add.
     * @return new node if not found equal, or existing node if found.
     */
    private Node addOrGet(Node headNode) {
        return this.headNodes.add(headNode) ? headNode : this.find(headNode);
    }

    /**
     * Find head node in the set of head nodes.
     *
     * @param head head node to find.
     * @return found head node or null if not found.
     */
    private Node find(Node head) {
        Node result = null;
        for (Node temp : this.headNodes) {
            if (head.compareTo(temp) == 0) {
                result = temp;
                break;
            }
        }
        return result;
    }
}
