package com.development.smarterclinics.Models;


public class Doc
{
    String DocName;
    String Speciality;


    String Mobile;
    String Gender;

    public void setMobile(String mobile) {Mobile = mobile;}

    public String getMobile(){return Mobile;}

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getGender() {return Gender;}

    public void setGender(String gender) {Gender = gender;}

    public Doc(String DoctorName, String Speciality, String phone, String gender) {
        this.DocName= DoctorName;
        this.Speciality = Speciality;
        this.Mobile = phone;
        this.Gender=gender;
    }

}
