package ru.job4j.test;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test for StringContains class.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 12.01.2018
 */
public class StringContainsTest {

    /**
     * Test contains method.
     */
    @Test
    public void whenNotContainsSubStringThenFalse() {
        StringContains cont = new StringContains();
        String origin = "Привет";
        String sub = "ива";
        boolean result = cont.contains(origin, sub);
        boolean expected = false;
        assertThat(result, is(expected));
    }

    @Test
    public void whenContainsSubStringInCenterThenTrue() {
        StringContains cont = new StringContains();
        String origin = "Прививеет";
        String sub = "иве";
        boolean result = cont.contains(origin, sub);
        boolean expected = true;
        assertThat(result, is(expected));

    }

    @Test
    public void whenContainsSubStringInTheEndThenTrue() {
        StringContains cont = new StringContains();
        String origin = "Привет";
        String sub = "ет";
        boolean result = cont.contains(origin, sub);
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenContainsSubStringInTheBeginningThenTrue() {
        StringContains cont = new StringContains();
        String origin = "Привет";
        String sub = "Пр";
        boolean result = cont.contains(origin, sub);
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenSubStringIsEqualToOriginalThenTrue() {
        StringContains cont = new StringContains();
        String origin = "Привет";
        String sub = "Привет";
        boolean result = cont.contains(origin, sub);
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenSubStringLongerThenOriginalThenFalse() {
        StringContains cont = new StringContains();
        String origin = "Привет";
        String sub = "Приветкагдила";
        boolean result = cont.contains(origin, sub);
        boolean expected = false;
        assertThat(result, is(expected));
    }

}
