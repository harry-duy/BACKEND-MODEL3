package dao;

import model.RentalRoom;
import model.PaymentType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalRoomDAOImpl implements RentalRoomDAO {
    @Override
    public List<RentalRoom> getAllRooms() {
        List<RentalRoom> rooms = new ArrayList<>();
        String sql = "SELECT r.*, p.name as payment_name FROM rental_room r JOIN payment_type p ON r.payment_type_id = p.id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RentalRoom room = new RentalRoom();
                room.setId(rs.getInt("id"));
                room.setRoomCode(rs.getString("room_code"));
                room.setTenantName(rs.getString("tenant_name"));
                room.setPhoneNumber(rs.getString("phone_number"));
                room.setStartDate(rs.getDate("start_date"));

                PaymentType pt = new PaymentType();
                pt.setId(rs.getInt("payment_type_id"));
                pt.setName(rs.getString("payment_name"));
                room.setPaymentType(pt);

                room.setNote(rs.getString("note"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public List<RentalRoom> searchRooms(String keyword) {
        List<RentalRoom> rooms = new ArrayList<>();
        String sql = "SELECT r.*, p.name as payment_name FROM rental_room r JOIN payment_type p ON r.payment_type_id = p.id " +
                "WHERE r.room_code LIKE ? OR r.tenant_name LIKE ? OR r.phone_number LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RentalRoom room = new RentalRoom();
                    room.setId(rs.getInt("id"));
                    room.setRoomCode(rs.getString("room_code"));
                    room.setTenantName(rs.getString("tenant_name"));
                    room.setPhoneNumber(rs.getString("phone_number"));
                    room.setStartDate(rs.getDate("start_date"));

                    PaymentType pt = new PaymentType();
                    pt.setId(rs.getInt("payment_type_id"));
                    pt.setName(rs.getString("payment_name"));
                    room.setPaymentType(pt);

                    room.setNote(rs.getString("note"));
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public boolean addRoom(RentalRoom room) {
        String sql = "INSERT INTO rental_room (tenant_name, phone_number, start_date, payment_type_id, note) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, room.getTenantName());
            stmt.setString(2, room.getPhoneNumber());
            stmt.setDate(3, new java.sql.Date(room.getStartDate().getTime()));
            stmt.setInt(4, room.getPaymentType().getId());
            stmt.setString(5, room.getNote());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRooms(List<String> roomCodes) {
        if (roomCodes == null || roomCodes.isEmpty()) return false;

        String sql = "DELETE FROM rental_room WHERE room_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            boolean success = true;

            for (String roomCode : roomCodes) {
                stmt.setString(1, roomCode);
                if (stmt.executeUpdate() == 0) {
                    success = false;
                    break;
                }
            }

            if (success) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}