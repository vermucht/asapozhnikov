package ru.job4j.crud;

import org.junit.Test;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserStoreCollectionTest {

    /**
     * Test Singleton and getInstance()
     */
    @Test
    public void whenGetInstanceThenTheOnlyObjectInstance() {
        UserStoreCollection store1 = UserStoreCollection.getInstance();
        UserStoreCollection store2 = UserStoreCollection.getInstance();
        UserStoreCollection store3 = UserStoreCollection.getInstance();
        assertThat(store1 == store2, is(true));
        assertThat(store1 == store3, is(true));
    }

    /**
     * Test add(), findById(), findAll()
     */
    @Test
    public void whenAddUserThenHeIsInStoreAndCanFindHimById() {
        UserStoreCollection store = UserStoreCollection.getInstance();
        store.clear();
        User added = new User("nameOne", "loginOne", "email@one.com", 123);
        int id = store.add(added);
        assertThat(store.findById(id), is(added));
        assertThat(store.findAll()[0], is(added));
    }

    /**
     * Test update()
     */
    @Test
    public void whenUpdateUserWithTheSameIdThenFieldsChange() {
        UserStoreCollection store = UserStoreCollection.getInstance();
        store.clear();
        User add = new User("old_name", "old_login", "old_email", 123);
        int id = store.add(add);
        User upd = new User(id, "new_name", "new_login", "new_email", 456);
        assertThat(store.update(upd), is(true));
        User result = store.findById(id);
        User expected = new User(id, upd.getName(), upd.getLogin(), upd.getEmail(), add.getCreated());
        assertThat(result, is(expected));
    }

    @Test
    public void whenUpdateUserWithWrongIdThenUpdateFalseAndUserNotChanging() {
        UserStoreCollection store = UserStoreCollection.getInstance();
        store.clear();
        User add = new User("old_name", "old_login", "old_email", 123);
        int id = store.add(add);
        int badId = id + 2134;
        User update = new User(badId, "new_name", "new_login", "new_email", 456);
        assertThat(store.update(update), is(false));
        User result = store.findById(id);
        User expected = new User(id, add.getName(), add.getLogin(), add.getEmail(), add.getCreated());
        assertThat(result, is(expected));
    }

    /**
     * Test delete()
     */
    @Test
    public void whenDeleteUserThenHeIsReturnedAndNotFoundInStore() {
        UserStoreCollection store = UserStoreCollection.getInstance();
        store.clear();
        User add = new User("name", "login", "email", 123);
        int id = store.add(add);
        User deleted = store.delete(id);
        assertThat(deleted, is(add));
        assertThat(store.findById(id), nullValue());
        assertThat(store.findAll(), is(new User[0]));
    }

    @Test
    public void whenDeleteUserWithWrongIdThenFalseAndUserStays() {
        UserStoreCollection store = UserStoreCollection.getInstance();
        store.clear();
        User add = new User("name", "login", "email", 123);
        int id = store.add(add);
        int badId = id + 123;
        User deleted = store.delete(badId);
        assertThat(deleted, nullValue());
        assertThat(store.findById(id), is(add));
        assertThat(store.findAll(), is(new User[]{add}));
    }

    /**
     * Test findById()
     */
    @Test
    public void whenAddedUsersCanFindThemById() {
        UserStoreCollection store = UserStoreCollection.getInstance();
        store.clear();
        User one = new User("name_1", "login_1", "email_1", 123);
        User two = new User("name_2", "login_2", "email_2", 456);
        User three = new User("name_3", "login_3", "email_3", 789);
        int idOne = store.add(one);
        int idTwo = store.add(two);
        int idThree = store.add(three);
        assertThat(store.findById(idTwo), is(two));
        assertThat(store.findById(idThree), is(three));
        assertThat(store.findById(idOne), is(one));
    }

    /**
     * Test findAll()
     */
    @Test
    public void whenAddedUsersThenFindAllReturnsThemAll() {
        UserStoreCollection store = UserStoreCollection.getInstance();
        store.clear();
        User one = new User("name_1", "login_1", "email_1", 123);
        User two = new User("name_2", "login_2", "email_2", 456);
        User three = new User("name_3", "login_3", "email_3", 789);
        store.add(one);
        store.add(two);
        store.add(three);
        User[] result = store.findAll();
        User[] expected = {two, one, three};    // order shouldn't matter in assert
        assertThat(result, arrayContainingInAnyOrder(expected));
    }
}