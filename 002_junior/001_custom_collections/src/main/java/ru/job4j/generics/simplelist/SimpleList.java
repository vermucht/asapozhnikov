package ru.job4j.generics.simplelist;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple array-based list.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 01.03.2018
 */
public class SimpleList<T> implements Iterable<T> {

    /**
     * Stored objects.
     */
    private T[] values;

    /**
     * Position where to add next object.
     */
    private int position = 0;

    /**
     * @param size size of the list.
     */
    @SuppressWarnings("unchecked")
    public SimpleList(int size) {
        this.values = (T[]) new Object[size];
    }

    /**
     * Adds new value to the end of the list.
     *
     * @param value value to add.
     */
    public void add(T value) {
        growIfSizeNotEnough();
        this.values[position++] = value;
    }

    /**
     * Replaces element with a new value.
     *
     * @param index index of the element to replace.
     * @param value new value.
     */
    public void set(int index, T value) {
        this.values[index] = value;
    }

    /**
     * Deletes element.
     *
     * @param index position of the element to delete.
     */
    public void delete(int index) {
        System.arraycopy(this.values, index + 1, this.values, index, --this.position - index);
        this.values[this.position] = null;
    }

    /**
     * Find index of the given element.
     *
     * @param element given element.
     * @return index of the element if found or {@code -1} if not found.
     */
    public int indexOf(T element) {
        int result = -1;
        for (int i = 0; i < this.position; i++) {
            if (element.equals(this.values[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Grow list capacity if it is not enough.
     */
    private void growIfSizeNotEnough() {
        if (!this.ensureCapacity()) {
            this.grow();
        }
    }

    /**
     * Ensure that list has the needed capacity.
     *
     * @return {@code true} or {@code false} if the list has needed capacity or not.
     */
    private boolean ensureCapacity() {
        return this.position < this.values.length;
    }

    /**
     * Grow list to the new size.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        int newCapacity = this.values.length * 3 / 2 + 1;
        T[] newValues = (T[]) new Object[newCapacity];
        System.arraycopy(this.values, 0, newValues, 0, this.values.length);
        this.values = newValues;
    }

    /**
     * Get element in the position.
     *
     * @param index position of the element.
     * @return element in this position.
     */
    T get(int index) {
        return this.values[index];
    }

    /**
     * Returns iterator to traverse through this list.
     *
     * @return iterator to traverse through this list.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            /**
             * Position of the iterator.
             */
            private int cursor = 0;

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return this.cursor < position;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return values[cursor++];
            }
        };
    }


}
