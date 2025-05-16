package model;

import java.util.Date;

public class RentalRoom {
    private int id;
    private String roomCode;
    private String tenantName;
    private String phoneNumber;
    private Date startDate;
    private int paymentTypeId;
    private String paymentTypeName;
    private String note;

    // Getters and Setters
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

    public int getPaymentTypeId() { return paymentTypeId; }
    public void setPaymentTypeId(int paymentTypeId) { this.paymentTypeId = paymentTypeId; }

    public String getPaymentTypeName() { return paymentTypeName; }
    public void setPaymentTypeName(String paymentTypeName) { this.paymentTypeName = paymentTypeName; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}