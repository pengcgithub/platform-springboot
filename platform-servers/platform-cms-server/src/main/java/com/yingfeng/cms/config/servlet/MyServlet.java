package com.yingfeng.cms.config.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet <br/>
 *
 * @author pengc
 * @since 2017/8/7
 * @see com.yingfeng.cms.config.servlet
 */
@WebServlet(name = "myServlet", urlPatterns = "/example")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(">>>>>>>>>>doGet<<<<<<<<<<<");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(">>>>>>>>>>doPost<<<<<<<<<<<");
        resp.getWriter().write("hello world");
    }

}
