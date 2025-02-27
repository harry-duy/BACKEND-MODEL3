package controller.order;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import model.Book;

@WebServlet(urlPatterns = "/order")
public class OrderPageController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageURL = request.getParameter("imageURL");
        String title = request.getParameter("title");
        String price = request.getParameter("price");

        Book book = new Book();
        book.setImageURL(imageURL);
        book.setTitle(title);
        book.setPrice(Double.parseDouble(price));

        // Gửi đối tượng Book vào request
        request.setAttribute("book", book);

        // Chuyển hướng đến trang orderpage.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/order/orderpage.jsp");
        dispatcher.forward(request, response);
    }
}

