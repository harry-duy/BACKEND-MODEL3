package model;

import java.util.Date;

public class RentalRoom {
    private int id;
    private String roomCode;
    private String tenantName;
    private String phoneNumber;
    private Date startDate;
    private PaymentType paymentType;
    private String note;

    // Constructors, getters and setters
    public RentalRoom() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }
    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}