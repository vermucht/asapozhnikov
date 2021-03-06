package ru.job4j.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * String constants needed for the application.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public enum Constants {
    /**
     * Attribute: class with constants for frontend with getters.
     */
    PARAM_CONSTANTS_WITH_GETTERS("const"),
    /**
     * Attribute: error message.
     */
    PARAM_ERROR("error"),
    /**
     * URI paths.
     */
    URI_CREATE_USER("/create"),
    URI_UPDATE_USER("/update"),
    URI_DELETE_USER("/delete"),
    URI_LOGIN("/login"),
    URI_LOGOUT("/logout"),
    URI_AJAX("/ajax"),
    /**
     * JSP pages.
     */
    JSP_VIEWS_DIR("/WEB-INF/views"),
    JSP_CREATE_USER("/create.jsp"),
    JSP_UPDATE_USER("/update.jsp"),
    JSP_LIST_USERS("/list.jsp"),
    JSP_LOGIN_PAGE("/login.jsp"),
    /**
     * Attribute: user to uns in the page and user parameters.
     */
    PARAM_USER("user"),
    PARAM_USER_ID("id"),
    PARAM_USER_LOGIN("login"),
    PARAM_USER_PASSWORD("password"),
    PARAM_USER_ROLE("role"),
    PARAM_USER_NAME("name"),
    PARAM_USER_EMAIL("email"),
    PARAM_USER_COUNTRY("country"),
    PARAM_USER_CITY("city"),
    /**
     * Attribute: user logged in the session and his values.
     */
    PARAM_LOGGED_USER("loggedUser"),
    PARAM_LOGGED_USER_ID("loggedUserId"),
    /**
     * Attribute: list of all users stored in the system.
     */
    PARAM_ALL_USERS("users"),
    /**
     * Attribute: list of all roles possible in the system.
     */
    PARAM_ALL_ROLES("roles");

    /**
     * Logger.
     */
    private static final Logger LOG = LogManager.getLogger(Constants.class);

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String v() {
        return this.value;
    }
}
