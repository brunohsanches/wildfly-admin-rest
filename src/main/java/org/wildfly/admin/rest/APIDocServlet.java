package org.wildfly.admin.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class APIDocServlet extends HttpServlet {

    private static final long serialVersionUID = -2988666735117618429L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str1 = req.getContextPath();
        String str2 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + str1 + "/";
        String str3 = str1 + "/api.html";
        String str4 = "swagger.json";
        String str5 = "/url=" + str2 + str4;
        String str6 = str3 + "?" + str5;
        resp.sendRedirect(str6);
    }

}
