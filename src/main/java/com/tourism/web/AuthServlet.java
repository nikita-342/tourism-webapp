package com.tourism.web;

import com.tourism.service.AuthService;
import com.tourism.repository.XmlUserRepository;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        String resourceName = getServletConfig().getInitParameter("usersResource");
        if (resourceName == null || resourceName.isBlank()) {
            resourceName = "data/users.xml";
        }

        this.authService = new AuthService(new XmlUserRepository(resourceName));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String action = req.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(req, resp, username, password);
        } else if ("register".equals(action)) {
            handleRegister(req, resp, username, password);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            handleLogout(req, resp);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp,
                             String username, String password) throws IOException, ServletException {
        boolean success = authService.login(username, password);

        if (success) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect("tours");
        } else {
            resp.sendRedirect("login.jsp?error=invalid_credentials");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp,
                                String username, String password) throws IOException {
        boolean success = authService.register(username, password);

        if (success) {
            resp.sendRedirect("login.jsp?message=registration_success");
        } else {
            resp.sendRedirect("register.jsp?error=registration_failed");
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect("login.jsp?message=logout_success");
    }
}