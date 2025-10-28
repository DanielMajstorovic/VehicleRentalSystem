package org.unibl.etf.ip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.unibl.etf.ip.dao.*;
import org.unibl.etf.ip.model.*;

@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String first   = req.getParameter("firstName");
        String last    = req.getParameter("lastName");
        String usern   = req.getParameter("username");
        String pass    = req.getParameter("password");
        String idNo    = req.getParameter("idNumber");
        String passNo  = req.getParameter("passportNumber");
        String email   = req.getParameter("email");
        String phone   = req.getParameter("phone");
        Part avatar    = req.getPart("avatar");

        try {
            UserDao userDao = new UserDao();
            if (userDao.existsByUsername(usern)) {
                req.setAttribute("error", "Username is already taken.");
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
                return;
            }

            
            User u = new User();
            u.setUsername(usern);
            u.setPassword(pass);
            u.setFirstName(first);
            u.setLastName(last);
            int userId = userDao.insert(u);

            
            Client c = new Client();
            c.setUserId(userId);
            c.setIdNumber(idNo);
            c.setPassportNumber(passNo);
            c.setEmail(email);
            c.setPhone(phone);
            c.setHasAvatarImage(avatar.getSize() > 0);
            new ClientDao().insert(c);


            if (avatar.getSize() > 0) {

                String fileName = avatar.getSubmittedFileName();
                String contentType = avatar.getContentType();

                try (InputStream is = avatar.getInputStream()) {

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI("http://localhost:8080/api/avatars/" + userId))
                            .header("Content-Type", contentType) 
                            .PUT(HttpRequest.BodyPublishers.ofInputStream(() -> {
                                try {
                                    return avatar.getInputStream(); 
                                } catch (IOException e) {
                                    throw new UncheckedIOException(e);
                                }
                            }))
                            .build();

                    HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.discarding());

                } catch (Exception e) {
                    req.setAttribute("error", "Failed to upload picture.");
                    req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
                    return;
                }
            }

          	

            req.getSession().setAttribute("client", u);
            resp.sendRedirect("home");
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }
}
