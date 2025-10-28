package org.unibl.etf.ip.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName="AuthFilter", urlPatterns={"/home"})
public class AuthFilter implements Filter {

    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getSession().getAttribute("client") == null) {
            resp.sendRedirect("login");
            return;
        }
        chain.doFilter(request, response);
    }
}
