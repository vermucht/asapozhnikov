package ru.job4j.crud.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.crud.User;
import ru.job4j.crud.logic.Validator;
import ru.job4j.crud.logic.ValidatorDatabase;

import javax.servlet.http.HttpServlet;

/**
 * General User HttpServlet class. Holds methods and fields needed
 * for every servlet to make interaction with user through html.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractServlet extends HttpServlet {
    /**
     * Logger.
     */
    protected static final Logger LOG = LogManager.getLogger(AbstractServlet.class);
    /**
     * Main directory where views are stored.
     */
    protected static final String VIEWS_DIR = "/WEB-INF/views";
    /**
     * Logic layer object - validator.
     */
    protected static final Validator<User> VALIDATOR = ValidatorDatabase.getInstance();
    /**
     * Logic layer object - servlet actions dispatch.
     */
    protected static final DispatchServletActions DISPATCH = new DispatchServletActions(VALIDATOR).init();

    /**
     * Returns views directory path.
     *
     * @return Views directory path.
     */
    protected String getViewsDir() {
        return VIEWS_DIR;
    }

    /**
     * Is called when servlet stops working.
     * Closes connection to database.
     */
    @Override
    public void destroy() {
        try {
            VALIDATOR.close();
        } catch (Exception e) {
            LOG.error(e.getStackTrace());
        }
    }

}
