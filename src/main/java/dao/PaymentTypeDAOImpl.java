package dao;

import model.PaymentType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentTypeDAOImpl implements PaymentTypeDAO {
    @Override
    public List<PaymentType> getAllPaymentTypes() {
        List<PaymentType> paymentTypes = new ArrayList<>();
        String sql = "SELECT * FROM payment_type";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PaymentType pt = new PaymentType();
                pt.setId(rs.getInt("id"));
                pt.setName(rs.getString("name"));
                paymentTypes.add(pt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentTypes;
    }
}