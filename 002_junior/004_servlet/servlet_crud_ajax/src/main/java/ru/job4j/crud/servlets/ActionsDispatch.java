package ru.job4j.crud.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.crud.logic.Validator;
import ru.job4j.crud.model.Credentials;
import ru.job4j.crud.model.Info;
import ru.job4j.crud.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static ru.job4j.crud.Constants.*;
import static ru.job4j.crud.model.Credentials.Role.ADMIN;
import static ru.job4j.crud.servlets.ActionsDispatch.Action.*;

/**
 * Class dispatching and performing all operations in logic layer.
 * <p>
 * Irs functions return String result which is to be included into
 * html page going to user.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ActionsDispatch {

    /**
     * Logger.
     */
    private static final Logger LOG = LogManager.getLogger(ActionsDispatch.class);
    /**
     * Map of possible actions.
     */
    private final Map<Action, BiFunction<HttpServletRequest, HttpServletResponse, Boolean>> dispatch = new HashMap<>();
    /**
     * Logic layer class validating and adding/updating/deleting users.
     */
    private final Validator<User> logic;

    /**
     * Constructs new instance.
     *
     * @param logic Logic layer object to perform operations on.
     */
    public ActionsDispatch(Validator<User> logic) {
        this.logic = logic;
    }

    /**
     * Tries to find handler for given action.
     * <p>
     * If no matching for the given action found, the "unknown" action handler if called.
     *
     * @param action Given action.
     * @param resp   Object that contains the response the servlet sends to the client.
     * @return String with action result.
     */
    public Boolean handle(final Action action, final HttpServletRequest req, final HttpServletResponse resp) {
        return this.dispatch.getOrDefault(action, this.toUnknown()).apply(req, resp);
    }

    /**
     * Initiates dispatch and returns its initiated object.
     *
     * @return Initiated dispatch object.
     */
    public ActionsDispatch initialize() {
        this.load(CREATE, this.toCreate());
        this.load(UPDATE, this.toUpdate());
        this.load(DELETE, this.toDelete());
        return this;
    }

    /**
     * Loads given action to the "action-handler" map.
     *
     * @param action  Given action.
     * @param handler Action handler.
     */
    private void load(Action action, BiFunction<HttpServletRequest, HttpServletResponse, Boolean> handler) {
        this.dispatch.put(action, handler);
    }

    /**
     * Returns handler for the "create" action.
     *
     * @return Handler for the "create" action.
     */
    private BiFunction<HttpServletRequest, HttpServletResponse, Boolean> toCreate() {
        return (req, resp) -> {
            User add = this.formUser(req);
            int id = logic.add(add);
            return id != -1;
        };
    }

    /**
     * Returns handler for the "update" action.
     *
     * @return Handler for the "update" action.
     */
    private BiFunction<HttpServletRequest, HttpServletResponse, Boolean> toUpdate() {
        return (req, resp) -> {
            boolean result = false;
            int loggedId = this.getInt(PARAM_LOGGED_USER_ID.v(), req);
            int id = this.getInt(PARAM_USER_ID.v(), req);
            if (loggedId != -1 && id != -1) {
                User logged = this.logic.findById(loggedId);
                User old = this.logic.findById(id);
                User upd = this.formUser(req);
                result = isRoleUpdatePossible(logged, old, upd) && this.logic.update(id, upd);
            }
            return result;
        };
    }

    private boolean isRoleUpdatePossible(User loggedUser, User oldUser, User updateUser) {
        Credentials.Role logged = loggedUser.getCredentials().getRole();
        Credentials.Role old = oldUser.getCredentials().getRole();
        Credentials.Role update = updateUser.getCredentials().getRole();
        return logged == ADMIN || old == update;
    }

    /**
     * Returns handler for the "delete" action.
     *
     * @return Handler for the "delete" action.
     */
    private BiFunction<HttpServletRequest, HttpServletResponse, Boolean> toDelete() {
        return (req, resp) -> {
            boolean result = false;
            int id = this.getInt(PARAM_USER_ID.v(), req);
            if (id != -1) {
                result = logic.delete(id) != null;
            }
            return result;
        };
    }

    /**
     * Returns handler for the "unknown" action. "Unknown" action is called if
     * no matching handler was found for the given action in the "handle" method.
     *
     * @return Handler for the "unknown" action.
     */
    private BiFunction<HttpServletRequest, HttpServletResponse, Boolean> toUnknown() {
        return (req, resp) -> {
            LOG.error("Unknown action type. Did nothing.");
            return false;
        };
    }

    /**
     * Forms User object from the request.
     *
     * @param req Object that contains the request the client has made of the servlet.
     * @return User object formed from the request.
     */
    private User formUser(HttpServletRequest req) {
        return new User(
                new Credentials(
                        req.getParameter(PARAM_USER_LOGIN.v()),
                        req.getParameter(PARAM_USER_PASSWORD.v()),
                        Credentials.Role.valueOf(req.getParameter(PARAM_USER_ROLE.v()))
                ),
                new Info(
                        req.getParameter(PARAM_USER_NAME.v()),
                        req.getParameter(PARAM_USER_EMAIL.v()),
                        req.getParameter(PARAM_USER_COUNTRY.v()),
                        req.getParameter(PARAM_USER_CITY.v())
                )
        );
    }

    /**
     * Tries to get id from the request and returns if got it.
     *
     * @param req Object that contains the request the client has made of the servlet.
     * @return Id parsed from the request or <tt>-1</tt> if couldn't.
     */
    private int getInt(String paramName, HttpServletRequest req) {
        int result = -1;
        String idString = req.getParameter(paramName);
        if (idString != null) {
            try {
                result = Integer.valueOf(idString);
            } catch (Exception e) {
                LOG.error(String.format("%s: %s", e.getClass().getName(), e.getMessage()));
            }
        }
        return result;
    }

    /**
     * Enum with possible actions.
     *
     * @author Aleksei Sapozhnikov (vermucht@gmail.com)
     * @version $Id$
     * @since 0.1
     */
    public enum Action {
        CREATE,
        UPDATE,
        DELETE
    }
}
