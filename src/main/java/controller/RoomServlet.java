package controller;

import dao.PaymentTypeDAO;
import dao.RentalRoomDAO;
import dao.PaymentTypeDAOImpl;
import dao.RentalRoomDAOImpl;
import model.RentalRoom;
import model.PaymentType;



import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "RoomServlet", urlPatterns = {"/"})
public class RoomServlet extends HttpServlet {
    private RentalRoomDAO roomDAO;
    private PaymentTypeDAO paymentTypeDAO;

    @Override
    public void init() {
        roomDAO = new RentalRoomDAOImpl();
        paymentTypeDAO = new PaymentTypeDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        List<RentalRoom> rooms;

        if ("search".equals(action)) {
            String keyword = request.getParameter("keyword");
            rooms = roomDAO.searchRooms(keyword);
        } else {
            rooms = roomDAO.getAllRooms();
        }

        List<PaymentType> paymentTypes = paymentTypeDAO.getAllPaymentTypes();
        request.setAttribute("rooms", rooms);
        request.setAttribute("paymentTypes", paymentTypes);
        request.getRequestDispatcher("WEB-INF/view/room-list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // Create new room
            RentalRoom room = new RentalRoom();
            room.setTenantName(request.getParameter("tenantName"));
            room.setPhoneNumber(request.getParameter("phoneNumber"));

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                room.setStartDate(sdf.parse(request.getParameter("startDate")));
            } catch (Exception e) {
                e.printStackTrace();
            }

            PaymentType pt = new PaymentType();
            pt.setId(Integer.parseInt(request.getParameter("paymentType")));
            room.setPaymentType(pt);

            room.setNote(request.getParameter("note"));

            roomDAO.addRoom(room);
        } else if ("delete".equals(action)) {
            // Delete rooms
            String[] selectedRooms = request.getParameterValues("selectedRooms");
            if (selectedRooms != null && selectedRooms.length > 0) {
                roomDAO.deleteRooms(Arrays.asList(selectedRooms));
            }
        }

        response.sendRedirect("rooms");
    }
}