package controller.login;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns ="/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    // Xử lý yêu cầu GET: Hiển thị trang đăng nhập
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/loginPage.jsp").forward(request, response);
    }

    // Xử lý yêu cầu POST: Kiểm tra thông tin đăng nhập
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.authenticate(username, password); // Gọi hàm xác thực từ UserService
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Kiểm tra quyền hạn (admin hoặc user)
            if (user.getRoleId() == 1) {
                response.sendRedirect("WEB-INF/view/list-users.jsp"); // Điều hướng đến trang quản trị
            } else {
                response.sendRedirect("WEB-INF/view/user-dashboard.jsp"); // Điều hướng đến trang người dùng
            }
        } else {
            // Nếu đăng nhập thất bại, quay lại trang đăng nhập với thông báo lỗi
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu sai!");
            request.getRequestDispatcher("WEB-INF/view/loginPage.jsp").forward(request, response);
        }
    }
}
