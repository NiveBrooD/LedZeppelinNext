package com.javarush.lesson12.view;

import com.javarush.lesson12.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/secretPage/loginform.msp")
public class TemplateViewAkaJsp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        Object oUser = session.getAttribute("user");
        User user = oUser == null
                ? new User("","")
                : (User) oUser;
        String htmlPage = getHtmlPage();
        String completeHtml = htmlPage.formatted(user.getLogin(), user.getPassword());
        out.println(completeHtml);
    }

    private static String getHtmlPage() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Title</title>
                </head>
                <body>
                <form method='post' action='/'>
                    <input type="text" value="%s" name="login">
                    <input type="password" value="%s" name="password">
                    <input type="submit" name="send">
                </form>
                </body>
                </html>
                """;
    }
}
