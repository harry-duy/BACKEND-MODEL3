package dao;

import model.RentalRoom;
import java.util.List;

public interface RentalRoomDAO {
    List<RentalRoom> getAllRooms();
    List<RentalRoom> searchRooms(String keyword);
    boolean addRoom(RentalRoom room);
    boolean deleteRooms(List<String> roomCodes);
}