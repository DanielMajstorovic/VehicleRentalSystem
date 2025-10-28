package org.unibl.etf.ip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.unibl.etf.ip.dao.UserDao;
import org.unibl.etf.ip.model.User;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            User u = new UserDao().findByUsernameAndPassword(username, password);
            if (u == null) {
                req.setAttribute("error", "Wrong username or password!");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }
            req.getSession().setAttribute("client", u);
            resp.sendRedirect("home");
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
