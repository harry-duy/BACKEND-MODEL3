package repository.order;

import model.Order;
import model.OrderDetail;
import repository.DBRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

//    public static List<OrderDetail> findAllOrderDetails() {
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        try {
//            Statement statement = DBRepository.getConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM order_details");
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                int orderId = resultSet.getInt("order_id");
//                int bookId = resultSet.getInt("book_id");
//                int quantity = resultSet.getInt("quantity");
//                String noteOrder = resultSet.getString("note_order");
//                String paymentMethod = resultSet.getString("payment_method");
//
//                orderDetails.add(new OrderDetail(id, orderId, bookId, quantity, noteOrder, paymentMethod));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Lỗi khi lấy danh sách chi tiết đơn hàng", e);
//        }
//        return orderDetails;
//    }
}
