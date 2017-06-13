package com.development.smarterclinics.Models;


public class Patient
{

    String Name;

    String Gender;

    long  Mobile;

    public Patient(String name,long mobile,String gender)
    {
        this.Name=name;
        this.Mobile=mobile;
        this.Gender=gender;
    }


    public String getGender() {
        return Gender;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public long getMobile() {
        return Mobile;
    }

    public void setMobile(long mobile) {
        Mobile = mobile;
    }
}
