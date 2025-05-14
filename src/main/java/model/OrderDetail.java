package model;

public class OrderDetail {
    private int id;
    private int orderId;
    private int bookId;
    private int quantity;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String provinceCity;
    private String district;
    private String ward;
    private String street;
    private String noteOrder;
    private double price;
    private String paymentMethod;
    private String status;

    // ✅ Constructor không tham số (bắt buộc nếu sử dụng setters)
    public OrderDetail() {
    }

    // ✅ Constructor cho thao tác tạo đơn hàng (insert)
    public OrderDetail(int bookId, String fullName, String email, String phoneNumber,
                       String provinceCity, String district, String ward, String street,
                       String noteOrder, double price, String paymentMethod) {
        this.bookId = bookId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.provinceCity = provinceCity;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.noteOrder = noteOrder;
        this.price = price;
        this.paymentMethod = paymentMethod;
    }

    // ✅ Constructor cho thao tác cập nhật đơn hàng (update)
    public OrderDetail(int id, String fullName, String email, String phoneNumber,
                       String provinceCity, String district, String ward, String street,
                       String noteOrder, double price, String paymentMethod, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.provinceCity = provinceCity;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.noteOrder = noteOrder;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // ✅ GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNoteOrder() {
        return noteOrder;
    }

    public void setNoteOrder(String noteOrder) {
        this.noteOrder = noteOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ✅ Lấy địa chỉ đầy đủ dưới dạng String
    public String getAddress() {
        return street + ", " + ward + ", " + district + ", " + provinceCity;
    }
}
