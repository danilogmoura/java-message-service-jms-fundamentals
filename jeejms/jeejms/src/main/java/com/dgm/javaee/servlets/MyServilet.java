package com.dgm.javaee.servlets;

import com.dgm.javaee.jms.MyMessageProducer;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class MyServilet extends HttpServlet {

    @EJB
    MyMessageProducer producer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String message = "Hello Message from JavaEE Server using JMS!!!";

        producer.sendMessage(message);

        resp.getWriter().write("Published the message: " + message);
    }
}
