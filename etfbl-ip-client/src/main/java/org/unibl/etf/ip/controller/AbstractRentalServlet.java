package org.unibl.etf.ip.controller;

import javax.servlet.http.*;
import org.unibl.etf.ip.dao.RentalDao;
import org.unibl.etf.ip.model.*;

abstract class AbstractRentalServlet extends HttpServlet {
    protected boolean redirectIfActive(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User client = (User) req.getSession().getAttribute("client");
        Rental active = new RentalDao().findActiveByClient(client.getId());
        if (active != null) {
            resp.sendRedirect("active-rental");
            return true;
        }
        return false;
    }
}
