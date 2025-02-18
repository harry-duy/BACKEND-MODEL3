package controller.user;

import model.User;
import service.impl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        // Lưu user với roleId mặc định là 2 (người dùng bình thường)
        User newUser = new User(0, username, password, email, 2);
        userService.registerUser(newUser);

        // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
        resp.sendRedirect("WEB-INF/view/loginPage.jsp");
    }
}
