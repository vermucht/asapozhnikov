package ru.job4j.crud.servlets;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.crud.logic.DatabaseValidator;
import ru.job4j.crud.logic.Validator;
import ru.job4j.crud.model.Credentials;
import ru.job4j.crud.model.Info;
import ru.job4j.crud.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static ru.job4j.crud.Constants.*;
import static ru.job4j.crud.model.Credentials.Role.ADMIN;

public class CreateUserServletTest {
    /**
     * Context path for tests.
     */
    private static final String CONTEXT = "context";
    /**
     * Mocks.
     */
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    /**
     * Users to use.
     */
    private final User userRoleAdmin = new User(new Credentials("aLogin", "aPassword", ADMIN), new Info("aName", "aEmail@mail.com", "aCountry", "aCity"));
    private final User userRoleUser = new User(new Credentials("uLogin", "uPassword", ADMIN), new Info("uName", "uEmail@mail.com", "uCountry", "uCity"));
    private final User userEmailWrongFormat = new User(new Credentials("login", "password", ADMIN), new Info("name", "email", "country", "city"));
    /**
     * Servlet to test.
     */
    private CreateUserServlet servlet = new CreateUserServlet();
    /**
     * Validator servlet is working with.
     */
    private Validator<User> validator = DatabaseValidator.getInstance();

    @Before
    public void clearStorageAndSetCommonMocks() {
        this.validator.clear();
        when(this.request.getContextPath()).thenReturn(CONTEXT);
        when(this.request.getSession()).thenReturn(this.httpSession);
        when(this.request.getSession()).thenReturn(this.httpSession);
        when(this.request.getRequestDispatcher(anyString())).thenReturn(this.requestDispatcher);
    }

    private void setMockForUserFields(User user) {
        when(this.request.getParameter(PARAM_USER_LOGIN.v())).thenReturn(user.getCredentials().getLogin());
        when(this.request.getParameter(PARAM_USER_PASSWORD.v())).thenReturn(user.getCredentials().getPassword());
        when(this.request.getParameter(PARAM_USER_ROLE.v())).thenReturn(user.getCredentials().getRole().toString());
        when(this.request.getParameter(PARAM_USER_NAME.v())).thenReturn(user.getInfo().getName());
        when(this.request.getParameter(PARAM_USER_EMAIL.v())).thenReturn(user.getInfo().getEmail());
        when(this.request.getParameter(PARAM_USER_COUNTRY.v())).thenReturn(user.getInfo().getCountry());
        when(this.request.getParameter(PARAM_USER_CITY.v())).thenReturn(user.getInfo().getCity());
    }

    /**
     * Test doPost()
     */
    @Test
    public void whenCreateUserWithValidFieldsThenUserInStorage() throws IOException, ServletException {
        this.setMockForUserFields(this.userRoleAdmin);
        this.servlet.doPost(this.request, this.response);
        User result = this.validator.findByCredentials(
                this.userRoleAdmin.getCredentials().getLogin(), this.userRoleAdmin.getCredentials().getPassword()
        );
        assertThat(result, is(this.userRoleAdmin));
        verify(this.response).sendRedirect(CONTEXT);
    }

    @Test
    public void whenCreateUserWithWrongFieldsThenUserNotInStorage() throws IOException, ServletException {
        this.setMockForUserFields(this.userEmailWrongFormat);
        this.servlet.doPost(this.request, this.response);
        verify(this.request).setAttribute(eq(PARAM_ERROR.v()), anyString());
        verify(this.requestDispatcher).forward(this.request, this.response);
    }

    /**
     * Test doGet()
     */
    @Test
    public void whenGetMethodThenRedirectToCreatePage() throws IOException, ServletException {
        this.servlet.doGet(this.request, this.response);
        verify(this.request).getRequestDispatcher(JSP_VIEWS_DIR.v().concat(JSP_CREATE_USER.v()));

    }
}