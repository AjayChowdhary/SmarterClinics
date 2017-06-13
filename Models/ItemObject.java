package com.development.smarterclinics.Models;

public class ItemObject {

    private String PatientName;
    private String BookedSlot;
    private long phone;
    private String status;
    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getBookedSlot() {
        return BookedSlot;
    }

    public void setBookedSlot(String bookedSlot) {
        BookedSlot = bookedSlot;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ItemObject(String PatientName, String BookedSlot,String status, long phone) {
        this.PatientName= PatientName;
        this.BookedSlot = BookedSlot;
        this.phone = phone;
        this.status=status;

    }


}