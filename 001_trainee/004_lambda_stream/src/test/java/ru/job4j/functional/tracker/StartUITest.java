package ru.job4j.functional.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for StartUI class.
 */

@SuppressWarnings("Duplicates")
public class StartUITest {

    /**
     * Stores "standard" console output.
     */
    private PrintStream stdout = System.out;

    /**
     * Output for testing.
     */
    private ByteArrayOutputStream out = new ByteArrayOutputStream();


    /**
     * Change "standard" output to testing output.
     */
    @Before
    public void changeOutput() {
        System.setOut(new PrintStream(this.out));
    }

    /**
     * Return output back to "standard".
     */
    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }


    /**
     * Test add.
     */
    @Test
    public void whenUserAddItemThenTrackerHasItemWithThatName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "name1", "description1", "6"});
        new StartUI(input, tracker).init();
        String result = tracker.findAll().get(0).getDescription();
        String expected = "description1";
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserAddItemThenTrackerHasItemWithThatDescription() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "name1", "description1", "6"});
        new StartUI(input, tracker).init();
        String result = tracker.findAll().get(0).getName();
        String expected = "name1";
        assertThat(result, is(expected));
    }

    /**
     * Test update.
     */
    @Test
    public void whenUserUpdateItemThenTrackerHasUpdatedName() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name1", "desc1", System.currentTimeMillis()));
        Input input = new StubInput(new String[]{"2", item.getId(), "new name", "new desc", "y", "6"});
        new StartUI(input, tracker).init();
        String result = tracker.findById(item.getId()).getName();
        String expected = "new name";
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserUpdateItemThenTrackerHasUpdatedDescription() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name1", "desc1", System.currentTimeMillis()));
        Input input = new StubInput(new String[]{"2", item.getId(), "new name", "new desc", "y", "6"});
        new StartUI(input, tracker).init();
        String result = tracker.findById(item.getId()).getDescription();
        String expected = "new desc";
        assertThat(result, is(expected));
    }

    /**
     * Test delete.
     */
    @Test
    public void whenUserDeleteLastItemThenTrackerIsEmpty() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name1", "desc1", System.currentTimeMillis()));
        Input input = new StubInput(new String[]{"3", item.getId(), "y", "6"});
        new StartUI(input, tracker).init();
        List<Item> result = tracker.findAll();
        List<Item> expected = new ArrayList<>();
        assertThat(result, is(expected));
    }

    @Test(expected = NoSuchIdException.class)
    public void whenUserDeleteItemThenFindByIdThisItemNoSuchIdException() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("name1", "desc1", System.currentTimeMillis()));
        Item itemDel = tracker.add(new Item("name2", "desc3", System.currentTimeMillis()));
        Input input = new StubInput(new String[]{"3", itemDel.getId(), "y", "6"});
        new StartUI(input, tracker).init();
        tracker.findById(itemDel.getId());
    }

    /**
     * Test show all.
     */
    @Test
    public void whenUserShowAllThenListOfItems() {
        //add items
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("name1", "desc1", 0L));
        item1.setId("111");
        Item item2 = tracker.add(new Item("name2", "desc2", 0L));
        item2.setId("222");
        //run program
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String result = new String(this.out.toByteArray());
        String expected = new StringJoiner(System.lineSeparator())
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("")
                .add("------------ Show all items contained ------------")
                .add("")
                .add("=== Item id : 111")
                .add("name : name1")
                .add("description : desc1")
                .add("")
                .add("=== Item id : 222")
                .add("name : name2")
                .add("description : desc2")
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("=== Exit program.")
                .add("")
                .toString();
        assertThat(result, is(expected));
    }

    /**
     * Test show by id.
     */
    @Test
    public void whenUserShowItemByIdThenShowItem() {
        //add items
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("name1", "desc1", 0L));
        item1.setId("111");
        Item item2 = tracker.add(new Item("name2", "desc2", 0L));
        item2.setId("222");
        //run program
        Input input = new StubInput(new String[]{"4", "111", "6"});
        new StartUI(input, tracker).init();
        String result = new String(this.out.toByteArray());
        String expected = new StringJoiner(System.lineSeparator())
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("")
                .add("------------ Find item by id ------------")
                .add("=== Item information : ")
                .add("id : 111")
                .add("name : name1")
                .add("description : desc1")
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("=== Exit program.")
                .add("")
                .toString();
        assertThat(result, is(expected));
    }

    /**
     * Test show by id.
     */
    @Test
    public void whenUserShowItemByNameThenShowItemsWithThatName() {
        //add items
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("name1", "desc1", 0L));
        item1.setId("111");
        Item item2 = tracker.add(new Item("name2", "desc2", 0L));
        item2.setId("222");
        Item item3 = tracker.add(new Item("name1", "desc3", 0L));
        item3.setId("333");
        //run program
        Input input = new StubInput(new String[]{"5", "name1", "6"});
        new StartUI(input, tracker).init();
        String result = new String(this.out.toByteArray());
        String expected = new StringJoiner(System.lineSeparator())
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("")
                .add("------------ Find items with given name ------------")
                .add("")
                .add("== Item id : 111")
                .add("name : name1")
                .add("description : desc1")
                .add("")
                .add("== Item id : 333")
                .add("name : name1")
                .add("description : desc3")
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("=== Exit program.")
                .add("")
                .toString();
        assertThat(result, is(expected));
    }

    /**
     * Test with List<String> consumer.
     */
    @Test
    public void whenConsumerListOfStringsThenShowByIdResultRight() {
        List<String> result = new ArrayList<>();
        Consumer<String> consumer = result::add;
        //add items
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("name1", "desc1", 0L));
        item1.setId("111");
        Item item2 = tracker.add(new Item("name2", "desc2", 0L));
        item2.setId("222");
        //run program and get result
        Input input = new StubInput(new String[]{"4", "111", "6"});
        new StartUI(input, tracker, consumer).init();
        // create expected
        List<String> expected = new ArrayList<>();
        expected.add(new StringJoiner(System.lineSeparator())
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("")
                .toString()
        );
        expected.add(new StringJoiner(System.lineSeparator())
                .add("")
                .add("------------ Find item by id ------------")
                .add("=== Item information : ")
                .add("id : 111")
                .add("name : name1")
                .add("description : desc1")
                .add("")
                .toString()
        );
        expected.add(new StringJoiner(System.lineSeparator())
                .add("")
                .add("============ Action menu ============")
                .add("0 : Add new Item")
                .add("1 : Show all items")
                .add("2 : Edit item")
                .add("3 : Delete item")
                .add("4 : Find item by Id")
                .add("5 : Find items by name")
                .add("6 : Exit Program")
                .add("")
                .toString()
        );
        expected.add(new StringJoiner(System.lineSeparator())
                .add("=== Exit program.")
                .add("")
                .toString()
        );
        assertThat(result, is(expected));
    }
}