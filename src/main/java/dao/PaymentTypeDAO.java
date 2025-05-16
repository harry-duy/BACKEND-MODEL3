package dao;
import model.PaymentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentTypeDAO {

    public List<PaymentType> getAllPaymentTypes() {
        List<PaymentType> paymentTypes = new ArrayList<>();
        String sql = "SELECT * FROM payment_type";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PaymentType paymentType = new PaymentType();
                paymentType.setId(rs.getInt("id"));
                paymentType.setName(rs.getString("name"));
                paymentTypes.add(paymentType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentTypes;
    }

    public PaymentType getPaymentTypeById(int id) {
        String sql = "SELECT * FROM payment_type WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PaymentType paymentType = new PaymentType();
                    paymentType.setId(rs.getInt("id"));
                    paymentType.setName(rs.getString("name"));
                    return paymentType;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}