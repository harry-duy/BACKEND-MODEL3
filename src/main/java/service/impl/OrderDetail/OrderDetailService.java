package service.impl.OrderDetail;

import dao.OrderDetailDAO;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailService {
    private Connection connection;
    private OrderDetailDAO orderDetailDAO;

    public OrderDetailService(Connection connection) {
        this.connection = connection;
        this.orderDetailDAO = new OrderDetailDAO(connection);
    }

    public List<OrderDetail> getAll() {
        try {
            // Debug message
            System.out.println("Fetching all order details...");

            String sql = "SELECT * FROM Order_Details";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<OrderDetail> orderDetails = new ArrayList<>();

            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(resultSet.getInt("id"));
                orderDetail.setBookId(resultSet.getInt("book_id"));
                orderDetail.setFullName(resultSet.getString("full_name"));
                orderDetail.setEmail(resultSet.getString("email"));
                orderDetail.setPhoneNumber(resultSet.getString("phone_number"));
                orderDetail.setProvinceCity(resultSet.getString("province_city"));
                orderDetail.setDistrict(resultSet.getString("district"));
                orderDetail.setWard(resultSet.getString("ward"));
                orderDetail.setStreet(resultSet.getString("street"));
                orderDetail.setNoteOrder(resultSet.getString("note_order"));
                orderDetail.setPrice(resultSet.getDouble("total_price"));
                orderDetail.setPaymentMethod(resultSet.getString("payment_method"));
                orderDetail.setStatus(resultSet.getString("order_status"));

                orderDetails.add(orderDetail);
            }

            System.out.println("Retrieved " + orderDetails.size() + " order details");
            return orderDetails;

        } catch (SQLException e) {
            System.err.println("Error fetching order details: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void add(OrderDetail orderDetail) {
        orderDetailDAO.insertOrderDetail(orderDetail);
    }

    public void update(OrderDetail orderDetail) {
        orderDetailDAO.updateOrderDetail(orderDetail);
    }

    public void remove(int id) {
        orderDetailDAO.deleteOrderDetail(id);
    }

    public OrderDetail findById(int id) {
        try {
            String sql = "SELECT * FROM Order_Details WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(resultSet.getInt("id"));
                orderDetail.setBookId(resultSet.getInt("book_id"));
                orderDetail.setFullName(resultSet.getString("full_name"));
                orderDetail.setEmail(resultSet.getString("email"));
                orderDetail.setPhoneNumber(resultSet.getString("phone_number"));
                orderDetail.setProvinceCity(resultSet.getString("province_city"));
                orderDetail.setDistrict(resultSet.getString("district"));
                orderDetail.setWard(resultSet.getString("ward"));
                orderDetail.setStreet(resultSet.getString("street"));
                orderDetail.setNoteOrder(resultSet.getString("note_order"));
                orderDetail.setPrice(resultSet.getDouble("total_price"));
                orderDetail.setPaymentMethod(resultSet.getString("payment_method"));
                orderDetail.setStatus(resultSet.getString("order_status"));

                return orderDetail;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error finding order detail by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}