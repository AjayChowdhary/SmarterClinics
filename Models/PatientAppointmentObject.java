package com.development.smarterclinics.Models;

public class PatientAppointmentObject {

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getBookedSlot() {
        return BookedSlot;
    }

    public void setBookedSlot(String bookedSlot) {
        BookedSlot = bookedSlot;
    }

    public String getBookedDate() {
        return BookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.BookedDate = bookedDate;
    }

    private String DoctorName;
    private String BookedSlot;
    private String BookedDate;

    public PatientAppointmentObject(String DoctorName, String BookedSlot, String phone) {
        this.DoctorName = DoctorName;
        this.BookedSlot = BookedSlot;
        this.BookedDate = phone;
    }


}