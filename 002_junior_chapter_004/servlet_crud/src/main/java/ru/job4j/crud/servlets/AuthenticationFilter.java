package ru.job4j.crud.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getRequestURI().contains("/login")) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = req.getSession();
            synchronized (session) {
                if (session.getAttribute("login") == null) {
                    HttpServletResponse resp = (HttpServletResponse) response;
                    resp.sendRedirect(String.join("/", req.getContextPath(), "login"));
                } else {
                    chain.doFilter(request, response);
                }
            }
        }
    }


    @Override
    public void destroy() {

    }
}
