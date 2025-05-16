package service;

import model.PaymentType;
import dao.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentTypeService {
    public List<PaymentType> getAllPaymentTypes() {
        List<PaymentType> list = new ArrayList<>();
        String sql = "SELECT * FROM payment_type";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PaymentType p = new PaymentType();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}