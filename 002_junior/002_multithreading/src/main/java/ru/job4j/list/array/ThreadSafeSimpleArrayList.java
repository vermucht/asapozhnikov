package ru.job4j.list.array;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.list.SimpleContainer;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Allows to store, add and get elements.
 * Grows if capacity not enough to add element.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 02.03.2018
 */
@SuppressWarnings("Duplicates") // the code is purposefully copied from 002_junior_chapter_001
@ThreadSafe
public class ThreadSafeSimpleArrayList<E> implements SimpleContainer<E> {

    /**
     * Default list capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Array to store values.
     */
    @GuardedBy("this")
    private volatile E[] container;

    /**
     * Position where to put next element into array.
     */
    @GuardedBy("this")
    private volatile int position = 0;

    /**
     * Modifications count (to prevent concurrent modification in iterator).
     */
    @GuardedBy("this")
    private volatile int modCount = 0;

    /**
     * Default constructor.
     */
    ThreadSafeSimpleArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * @param size initial list capacity.
     */
    @SuppressWarnings("unchecked")
    ThreadSafeSimpleArrayList(final int size) {
        this.container = (E[]) new Object[size];
    }

    /**
     * Adds element to list.
     *
     * @param value element to add.
     */
    @Override
    public synchronized void add(final E value) {
        modCount++;
        growIfNeeded();
        this.container[this.position++] = value;
    }

    /**
     * Gets value from the list.
     *
     * @param index index of the element to get.
     * @return elemen in given position or {@code null} if element was not found.
     */
    @Override
    public synchronized E get(final int index) {
        return container[index];
    }


    /**
     * Returns iterator to traverse through the elements of list.
     *
     * @return iterator to traverse through the elements of list.
     */
    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            /**
             * Modification count value at the time when the iterator was created.
             * If collection will change during the work of iterator, there will be {@code }ConcurrentModificationException}.
             */
            private final int expectedModCount = modCount;
            /**
             * Next element to take.
             */
            @GuardedBy("this")
            private volatile int cursor = 0;

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.
             *
             * @return {@code true} if the iteration has more element
             * @throws ConcurrentModificationException if the list was changed during the work of iterator.
             */
            @Override
            public synchronized boolean hasNext() {
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor < position;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             * @throws ConcurrentModificationException if the list was changed during the work of iterator.
             */
            @Override
            public synchronized E next() {
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[cursor++];
            }
        };
    }

    /**
     * Ensures capacity is enough or makes list larger if needed.
     */
    private synchronized void growIfNeeded() {
        if (!this.ensureCapacity()) {
            this.grow();
        }
    }

    /**
     * Ensures capacity of the list is enough to add new element.
     *
     * @return {@code true} if capacity is enough, {@code false} if not
     */
    private synchronized boolean ensureCapacity() {
        return this.position < this.container.length;
    }

    /**
     * Enlarges inner array to allow addition of new element(s).
     */
    private synchronized void grow() {
        int newLength = this.container.length * 3 / 2 + 1;
        this.container = Arrays.copyOf(this.container, newLength);
    }
}
