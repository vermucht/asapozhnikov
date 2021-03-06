package ru.job4j.crud.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.crud.model.Credentials;
import ru.job4j.crud.model.Info;
import ru.job4j.crud.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.job4j.crud.Constants.*;
import static ru.job4j.crud.model.Credentials.Role.ADMIN;

/**
 * Presentation layer "login" servlet.
 * Shows form for user to enter his login/password and
 * saves the user into the http session.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LogInServlet extends AbstractServlet {
    /**
     * Logger.
     */
    private static final Logger LOG = LogManager.getLogger(LogInServlet.class);

    /**
     * Root user - to guarantee ability to login.
     */
    private User root = new User(new Credentials("root", "root", ADMIN), new Info("root_name", "root@email.com", "root_country", "root_city"));

    public LogInServlet() {
        VALIDATOR.add(root);
    }

    /**
     * Handles GET requests. Shows form for user to log in.
     *
     * @param req  Object that contains the request the client has made of the servlet.
     * @param resp Object that contains the response the servlet sends to the client
     * @throws ServletException General exception a servlet can throw when it encounters difficulty.
     * @throws IOException      Signals that an I/O exception of some sort has occurred.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP_VIEWS_DIR.v().concat(JSP_LOGIN_PAGE.v())).forward(req, resp);
    }

    /**
     * Handles POST requests. Adds user to the http session if user found in database.
     * Otherwise sends error message to client.
     *
     * @param req  Object that contains the request the client has made of the servlet.
     * @param resp Object that contains the response the servlet sends to the client.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(PARAM_USER_LOGIN.v());
        String password = req.getParameter(PARAM_USER_PASSWORD.v());
        User user = VALIDATOR.findByCredentials(login, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute(PARAM_LOGGED_USER.v(), user);
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute(PARAM_ERROR.v(), "Invalid credentials");
            this.doGet(req, resp);
        }
    }
}
    