package org.unibl.etf.ip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import org.unibl.etf.ip.dao.RentalDao;
import org.unibl.etf.ip.model.Rental;
import org.unibl.etf.ip.model.User;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User client = (User) req.getSession().getAttribute("client");
        try {
            Rental active = new RentalDao().findActiveByClient(client.getId());
            req.setAttribute("activeRental", active);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}
