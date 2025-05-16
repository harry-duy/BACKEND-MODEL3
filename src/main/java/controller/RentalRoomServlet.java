package controller;


import model.PaymentType;
import model.RentalRoom;
import service.PaymentTypeService;
import service.RentalRoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/")
public class RentalRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RentalRoomService rentalRoomService;
    private PaymentTypeService paymentTypeService;

    public void init() {
        rentalRoomService = new RentalRoomService();
        paymentTypeService = new PaymentTypeService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "list" : action;

        switch (action) {
            case "list":
                listRentalRooms(request, response);
                break;
            case "search":
                searchRentalRooms(request, response);
                break;
            case "delete":
                deleteRentalRoom(request, response);
                break;
            case "deleteMultiple":
                deleteMultipleRentalRooms(request, response);
                break;
            default:
                listRentalRooms(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "list" : action;

        switch (action) {
            case "add":
                addRentalRoom(request, response);
                break;
            case "search":
                searchRentalRooms(request, response);
                break;
            case "deleteConfirm":
                deleteConfirm(request, response);
                break;
            default:
                listRentalRooms(request, response);
                break;
        }
    }

    private void listRentalRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RentalRoom> rentalRooms = rentalRoomService.getAllRentalRooms();
        List<PaymentType> paymentTypes = paymentTypeService.getAllPaymentTypes();

        request.setAttribute("rentalRooms", rentalRooms);
        request.setAttribute("paymentTypes", paymentTypes);
        request.getRequestDispatcher("/WEB-INF/view/list.jsp").forward(request, response);
    }

    private void searchRentalRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        List<RentalRoom> rentalRooms;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            rentalRooms = rentalRoomService.searchRentalRooms(searchTerm);
        } else {
            rentalRooms = rentalRoomService.getAllRentalRooms();
        }

        List<PaymentType> paymentTypes = paymentTypeService.getAllPaymentTypes();

        request.setAttribute("rentalRooms", rentalRooms);
        request.setAttribute("paymentTypes", paymentTypes);
        request.setAttribute("searchTerm", searchTerm);
        request.getRequestDispatcher("/WEB-INF/view/list.jsp").forward(request, response);
    }

    private void addRentalRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tenantName = request.getParameter("tenantName");
        String phoneNumber = request.getParameter("phoneNumber");
        String startDateStr = request.getParameter("startDate");
        String paymentTypeIdStr = request.getParameter("paymentTypeId");
        String note = request.getParameter("note");

        // Validation
        boolean hasError = false;

        if (!rentalRoomService.validateTenantName(tenantName)) {
            request.setAttribute("tenantNameError", "Tên người thuê trọ phải từ 5-50 ký tự và không chứa số hoặc ký tự đặc biệt");
            hasError = true;
        }

        if (!rentalRoomService.validatePhoneNumber(phoneNumber)) {
            request.setAttribute("phoneNumberError", "Số điện thoại phải có đúng 10 chữ số");
            hasError = true;
        }

        Date startDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            startDate = sdf.parse(startDateStr);

            if (!rentalRoomService.validateStartDate(startDate)) {
                request.setAttribute("startDateError", "Ngày bắt đầu thuê không được là ngày trong quá khứ");
                hasError = true;
            }
        } catch (ParseException e) {
            request.setAttribute("startDateError", "Ngày phải có định dạng dd-MM-yyyy");
            hasError = true;
        }

        if (paymentTypeIdStr == null || paymentTypeIdStr.trim().isEmpty()) {
            request.setAttribute("paymentTypeError", "Phải chọn hình thức thanh toán");
            hasError = true;
        }

        if (!rentalRoomService.validateNote(note)) {
            request.setAttribute("noteError", "Ghi chú không được quá 200 ký tự");
            hasError = true;
        }

        if (hasError) {
            List<RentalRoom> rentalRooms = rentalRoomService.getAllRentalRooms();
            List<PaymentType> paymentTypes = paymentTypeService.getAllPaymentTypes();

            request.setAttribute("rentalRooms", rentalRooms);
            request.setAttribute("paymentTypes", paymentTypes);
            request.setAttribute("tenantName", tenantName);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("startDate", startDateStr);
            request.setAttribute("paymentTypeId", paymentTypeIdStr);
            request.setAttribute("note", note);
            request.setAttribute("showAddForm", true);

            request.getRequestDispatcher("/WEB-INF/view/list.jsp").forward(request, response);
            return;
        }

        // Add rental room
        RentalRoom rentalRoom = new RentalRoom();
        rentalRoom.setTenantName(tenantName);
        rentalRoom.setPhoneNumber(phoneNumber);
        rentalRoom.setStartDate(startDate);
        rentalRoom.setPaymentTypeId(Integer.parseInt(paymentTypeIdStr));
        rentalRoom.setNote(note);

        if (rentalRoomService.addRentalRoom(rentalRoom)) {
            request.setAttribute("successMessage", "Thêm thông tin thuê trọ thành công");
        } else {
            request.setAttribute("errorMessage", "Lỗi khi thêm thông tin thuê trọ");
        }

        listRentalRooms(request, response);
    }

    private void deleteRentalRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomCode = request.getParameter("roomCode");

        if (roomCode != null && !roomCode.isEmpty()) {
            RentalRoom room = rentalRoomService.getRentalRoomByRoomCode(roomCode);
            if (room != null) {
                request.setAttribute("roomsToDelete", new String[]{roomCode});
                request.setAttribute("confirmMessage", "Bạn có muốn xóa thông tin thuê trọ " + roomCode + " hay không?");
                request.setAttribute("confirmAction", "deleteConfirm");

                listRentalRooms(request, response);
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/");
    }

    private void deleteMultipleRentalRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] roomCodes = request.getParameterValues("selectedRooms");

        if (roomCodes != null && roomCodes.length > 0) {
            StringBuilder confirmMessage = new StringBuilder("Bạn có muốn xóa thông tin thuê trọ ");
            for (int i = 0; i < roomCodes.length; i++) {
                confirmMessage.append(roomCodes[i]);
                if (i < roomCodes.length - 1) {
                    confirmMessage.append(", ");
                }
            }
            confirmMessage.append(" hay không?");

            request.setAttribute("roomsToDelete", roomCodes);
            request.setAttribute("confirmMessage", confirmMessage.toString());
            request.setAttribute("confirmAction", "deleteConfirm");

            listRentalRooms(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    private void deleteConfirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] roomCodes = request.getParameterValues("roomsToDelete");

        if (roomCodes != null && roomCodes.length > 0) {
            boolean result;

            if (roomCodes.length == 1) {
                result = rentalRoomService.deleteRentalRoom(roomCodes[0]);
            } else {
                result = rentalRoomService.deleteMultipleRentalRooms(roomCodes);
            }

            if (result) {
                request.setAttribute("successMessage", "Xóa thông tin thuê trọ thành công");
            } else {
                request.setAttribute("errorMessage", "Lỗi khi xóa thông tin thuê trọ");
            }
        }

        listRentalRooms(request, response);
    }
}