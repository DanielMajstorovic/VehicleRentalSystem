package org.unibl.etf.ip.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import org.unibl.etf.ip.dao.*;
import org.unibl.etf.ip.model.*;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException,IOException{

        User user=(User)req.getSession().getAttribute("client");
        try{
            Client client=new ClientDao().findById(user.getId());
            List<Rental> finished=new RentalDao().findFinishedByClient(user.getId());

            req.setAttribute("clientInfo",client);
            req.setAttribute("rentals",finished);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp")
               .forward(req,resp);

        }catch(Exception ex){throw new ServletException(ex);}
    }

    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException,IOException{

        User user=(User)req.getSession().getAttribute("client");
        String action=req.getParameter("action");

        try{
            if("changePwd".equals(action)){
                String curr=req.getParameter("current");
                String next=req.getParameter("next");
                boolean ok=new UserDao().changePassword(user.getId(),curr,next);
                req.setAttribute("pwdMsg", ok?"Password changed.":"Wrong current password!");
            }
            else if("delete".equals(action)){
                new UserDao().deactivate(user.getId());
                req.getSession().invalidate();
                resp.sendRedirect("login");
                return;
            }
        }catch(Exception ex){ throw new ServletException(ex); }

        doGet(req,resp); 
    }
}
