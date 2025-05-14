package controller.user;

import model.User;
import service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserController", urlPatterns = "/users")
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // Kiểm tra quyền admin
        if (currentUser == null || currentUser.getRoleId() != 1) {
            response.sendRedirect("login");
            return;
        }

        // Xử lý tìm kiếm
        String searchQuery = request.getParameter("search");
        List<User> users;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            users = userService.findByName(searchQuery.trim());
            request.setAttribute("searchQuery", searchQuery);
        } else {
            users = UserService.getAllUsersWithStatus(); // Lấy tất cả người dùng bao gồm cả đã ẩn
        }

        request.setAttribute("users", users);

        // Forward đến JSP
        request.getRequestDispatcher("WEB-INF/view/user/list-users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String redirectUrl = "users";

        try {
            // Lấy ID từ form
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                redirectUrl += "?error=Missing user ID";
                response.sendRedirect(redirectUrl);
                return;
            }

            int userId = Integer.parseInt(idParam);
            User targetUser = userService.findById(userId);

            if (targetUser == null) {
                redirectUrl += "?error=User not found";
                response.sendRedirect(redirectUrl);
                return;
            }

            // Debug log - see what action is being received
            System.out.println("Action received: " + action + " for user ID: " + userId);

            switch (action) {
                case "delete":
                    userService.remove(userId);
                    redirectUrl += "?success=User hidden successfully";
                    break;

                case "restore":
                    // Call the restore method and make sure it actually updates the status
                    userService.restore(userId);
                    redirectUrl += "?success=User restored successfully";
                    break;

                case "edit":
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String roleIdParam = request.getParameter("role_id");

                    if (username == null || email == null || roleIdParam == null ||
                            username.isEmpty() || email.isEmpty() || roleIdParam.isEmpty()) {
                        redirectUrl += "?error=Missing required fields";
                        break;
                    }

                    int roleId = Integer.parseInt(roleIdParam);
                    User updatedUser = new User(userId, username, targetUser.getPassword(), email, roleId, targetUser.getStatus());
                    userService.update(userId, updatedUser);
                    redirectUrl += "?success=User updated successfully";
                    break;

                default:
                    redirectUrl += "?error=Invalid action: " + action;
            }
        } catch (NumberFormatException e) {
            redirectUrl += "?error=Invalid ID format";
        } catch (Exception e) {
            e.printStackTrace();
            redirectUrl += "?error=" + e.getMessage();
        }

        // Redirect với message thích hợp
        response.sendRedirect(redirectUrl);
    }
}