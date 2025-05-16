package service;

import model.RentalRoom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalRoomService {

    // Dummy storage, replace with DB calls
    private static final List<RentalRoom> rentalRooms = new ArrayList<>();

    public List<RentalRoom> getAllRentalRooms() {
        return new ArrayList<>(rentalRooms);
    }

    public List<RentalRoom> searchRentalRooms(String searchTerm) {
        List<RentalRoom> result = new ArrayList<>();
        for (RentalRoom room : rentalRooms) {
            if (room.getTenantName() != null && room.getTenantName().toLowerCase().contains(searchTerm.toLowerCase())) {
                result.add(room);
            }
        }
        return result;
    }

    public RentalRoom getRentalRoomByRoomCode(String roomCode) {
        for (RentalRoom room : rentalRooms) {
            if (room.getRoomCode() != null && room.getRoomCode().equals(roomCode)) {
                return room;
            }
        }
        return null;
    }

    public boolean addRentalRoom(RentalRoom rentalRoom) {
        // In real app, insert into DB
        // For now, just add to list
        return rentalRooms.add(rentalRoom);
    }

    public boolean deleteRentalRoom(String roomCode) {
        RentalRoom room = getRentalRoomByRoomCode(roomCode);
        if (room != null) {
            return rentalRooms.remove(room);
        }
        return false;
    }

    public boolean deleteMultipleRentalRooms(String[] roomCodes) {
        boolean allDeleted = true;
        for (String code : roomCodes) {
            boolean deleted = deleteRentalRoom(code);
            if (!deleted) allDeleted = false;
        }
        return allDeleted;
    }

    // Validation methods

    public boolean validateTenantName(String tenantName) {
        if (tenantName == null) return false;
        if (tenantName.length() < 5 || tenantName.length() > 50) return false;
        return tenantName.matches("[a-zA-Z\\s]+"); // only letters and spaces
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        return phoneNumber.matches("\\d{10}"); // exactly 10 digits
    }

    public boolean validateStartDate(Date startDate) {
        if (startDate == null) return false;
        Date today = new Date();
        // Remove time portion if needed here for strict day comparison
        return !startDate.before(today);
    }

    public boolean validateNote(String note) {
        if (note == null) return true;  // note is optional
        return note.length() <= 200;
    }
}
