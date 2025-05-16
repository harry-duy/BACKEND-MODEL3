package dao;

import model.RentalRoom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalRoomDAO {

    public List<RentalRoom> getAllRentalRooms() {
        List<RentalRoom> rentalRooms = new ArrayList<>();
        String sql = "SELECT r.*, p.name as payment_type_name FROM rental_room r " +
                "JOIN payment_type p ON r.payment_type_id = p.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RentalRoom rentalRoom = createRentalRoomFromResultSet(rs);
                rentalRooms.add(rentalRoom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentalRooms;
    }

    public List<RentalRoom> searchRentalRooms(String searchTerm) {
        List<RentalRoom> rentalRooms = new ArrayList<>();
        String sql = "SELECT r.*, p.name as payment_type_name FROM rental_room r " +
                "JOIN payment_type p ON r.payment_type_id = p.id " +
                "WHERE r.room_code LIKE ? OR r.tenant_name LIKE ? OR r.phone_number LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RentalRoom rentalRoom = createRentalRoomFromResultSet(rs);
                    rentalRooms.add(rentalRoom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentalRooms;
    }

    public boolean addRentalRoom(RentalRoom room) {
        String sql = "INSERT INTO rental_room (tenant_name, phone_number, start_date, payment_type_id, note) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, room.getTenantName());
            stmt.setString(2, room.getPhoneNumber());
            stmt.setDate(3, new java.sql.Date(room.getStartDate().getTime()));
            stmt.setInt(4, room.getPaymentTypeId());
            stmt.setString(5, room.getNote());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRentalRoom(String roomCode) {
        String sql = "DELETE FROM rental_room WHERE room_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roomCode);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMultipleRentalRooms(String[] roomCodes) {
        if (roomCodes == null || roomCodes.length == 0) {
            return false;
        }

        StringBuilder sql = new StringBuilder("DELETE FROM rental_room WHERE room_code IN (");
        for (int i = 0; i < roomCodes.length; i++) {
            sql.append("?");
            if (i < roomCodes.length - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < roomCodes.length; i++) {
                stmt.setString(i + 1, roomCodes[i]);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public RentalRoom getRentalRoomByRoomCode(String roomCode) {
        String sql = "SELECT r.*, p.name as payment_type_name FROM rental_room r " +
                "JOIN payment_type p ON r.payment_type_id = p.id " +
                "WHERE r.room_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roomCode);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createRentalRoomFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private RentalRoom createRentalRoomFromResultSet(ResultSet rs) throws SQLException {
        RentalRoom rentalRoom = new RentalRoom();
        rentalRoom.setId(rs.getInt("id"));
        rentalRoom.setRoomCode(rs.getString("room_code"));
        rentalRoom.setTenantName(rs.getString("tenant_name"));
        rentalRoom.setPhoneNumber(rs.getString("phone_number"));
        rentalRoom.setStartDate(rs.getDate("start_date"));
        rentalRoom.setPaymentTypeId(rs.getInt("payment_type_id"));
        rentalRoom.setPaymentTypeName(rs.getString("payment_type_name"));
        rentalRoom.setNote(rs.getString("note"));
        return rentalRoom;
    }
}