package dao;

import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAO {
    private Connection connection;

    public OrderDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO Order_Details (book_id, full_name, email, phone_number, province_city, district, " +
                "ward, street, note_order, total_price, payment_method, order_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Đang xử lý')";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderDetail.getBookId());
            statement.setString(2, orderDetail.getFullName());
            statement.setString(3, orderDetail.getEmail());
            statement.setString(4, orderDetail.getPhoneNumber());
            statement.setString(5, orderDetail.getProvinceCity());
            statement.setString(6, orderDetail.getDistrict());
            statement.setString(7, orderDetail.getWard());
            statement.setString(8, orderDetail.getStreet());
            statement.setString(9, orderDetail.getNoteOrder());
            statement.setDouble(10, orderDetail.getPrice());
            statement.setString(11, orderDetail.getPaymentMethod());

            statement.executeUpdate();
            System.out.println("Order detail inserted successfully");
        } catch (SQLException e) {
            System.err.println("Error inserting order detail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        String sql = "UPDATE Order_Details SET full_name = ?, email = ?, phone_number = ?, " +
                "province_city = ?, district = ?, ward = ?, street = ?, note_order = ?, " +
                "total_price = ?, payment_method = ?, order_status = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderDetail.getFullName());
            statement.setString(2, orderDetail.getEmail());
            statement.setString(3, orderDetail.getPhoneNumber());
            statement.setString(4, orderDetail.getProvinceCity());
            statement.setString(5, orderDetail.getDistrict());
            statement.setString(6, orderDetail.getWard());
            statement.setString(7, orderDetail.getStreet());
            statement.setString(8, orderDetail.getNoteOrder());
            statement.setDouble(9, orderDetail.getPrice());
            statement.setString(10, orderDetail.getPaymentMethod());
            statement.setString(11, orderDetail.getStatus());
            statement.setInt(12, orderDetail.getId());

            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated + " order detail(s) updated successfully");
        } catch (SQLException e) {
            System.err.println("Error updating order detail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteOrderDetail(int id) {
        String sql = "DELETE FROM Order_Details WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " order detail(s) deleted successfully");
        } catch (SQLException e) {
            System.err.println("Error deleting order detail: " + e.getMessage());
            e.printStackTrace();
        }
    }
}