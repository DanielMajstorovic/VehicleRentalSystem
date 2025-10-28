package org.unibl.etf.ip.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.unibl.etf.ip.dao.*;
import org.unibl.etf.ip.model.*;

@WebServlet("/ebike")
public class BikeRentalServlet extends AbstractRentalServlet {

    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException,IOException {

        try{
            if(redirectIfActive(req,resp)) return;

            List<Vehicle> bikes=new VehicleDao().listAvailableEBikes();
            req.setAttribute("bikes",bikes);
            req.getRequestDispatcher("/WEB-INF/views/bike_rental.jsp").forward(req,resp);

        }catch(Exception ex){ throw new ServletException(ex); }
    }

    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException,IOException {

        User client=(User)req.getSession().getAttribute("client");

        int    vehicleId = Integer.parseInt(req.getParameter("vehicleId"));
        String vehicleUid= req.getParameter("vehicleUid");
        double x         = Double.parseDouble(req.getParameter("startX"));
        double y         = Double.parseDouble(req.getParameter("startY"));
        String dl        = req.getParameter("driversLicense");
        String card      = req.getParameter("paymentCard").replaceAll("\\s+","");

        Rental r=new Rental();
        r.setVehicleId(vehicleId);
        r.setClientId(client.getId());
        r.setStartTime(Timestamp.from(Instant.now()));
        r.setStartX(x); r.setStartY(y);
        r.setDriversLicense(dl);
        r.setPaymentCard(card);

        try{
            new RentalDao().createRental(r);
            new VehicleDao().setPositionAndRented(vehicleUid,x,y);
            resp.sendRedirect("active-rental");
        }catch(Exception ex){ throw new ServletException(ex);}
    }
}
