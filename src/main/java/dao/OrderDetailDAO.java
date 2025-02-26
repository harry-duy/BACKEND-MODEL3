package dao;

import model.OrderDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private Connection conn;

    public OrderDetailDAO(Connection conn) {
        this.conn = conn;
    }

    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM Order_Details";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("book_id"),
                        rs.getInt("quantity"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("province_city"),
                        rs.getString("district"),
                        rs.getString("ward"),
                        rs.getString("street"),
                        rs.getString("note_order"),
                        rs.getString("payment_method"),
                        rs.getString("order_status")
                );
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    // Tìm chi tiết đơn hàng theo ID
    public OrderDetail getOrderDetailById(int id) {
        String sql = "SELECT * FROM Order_Details WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OrderDetail(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("book_id"),
                        rs.getInt("quantity"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("province_city"),
                        rs.getString("district"),
                        rs.getString("ward"),
                        rs.getString("street"),
                        rs.getString("note_order"),
                        rs.getString("payment_method"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật chi tiết đơn hàng
    public void updateOrderDetail(int id, OrderDetail orderDetail) {
        String sql = "UPDATE Order_Details SET quantity=?, note_order=?, status=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getQuantity());
            stmt.setString(2, orderDetail.getNoteOrder());
            stmt.setString(3, orderDetail.getStatus());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa chi tiết đơn hàng
    public void deleteOrderDetail(int id) {
        String sql = "DELETE FROM Order_Details WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
