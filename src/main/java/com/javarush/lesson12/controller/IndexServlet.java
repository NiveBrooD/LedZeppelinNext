package com.javarush.lesson12.controller;

import com.javarush.lesson12.model.User;
import com.javarush.lesson12.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;


@WebServlet("/")
public class IndexServlet extends HttpServlet {

    private final UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("IndexServlet");
        req.getRequestDispatcher("/secretPage/loginform.msp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = userService.create(login, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        resp.sendRedirect(req.getRequestURI());
    }
}
