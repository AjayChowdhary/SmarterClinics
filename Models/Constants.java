package com.development.smarterclinics.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {

    public static final String baseURL="****************************";      // change it to baseUrlProduction url w9 final builed is done
    public static final String EmrbaseURL="****************************";  // change it to EmrbaseUrlProduction for final build
    public static final String baseURLProduction = "****************************";
    public static final String baseURLTesting = "****************************";
    public static final String EmrbaseURLTesting = "****************************";
    public static final String EmrbaseURLProduction = "****************************";


    public static final int CONNECTION_TIMEOUT = 10000;
    public static int idNumber;
    public static String username;
    public static int docnamenumber;

    public static String current_patient_id;
    public static int length;
    public static String TokenDB;
    public static String DoctorSelectedId;
    public static String first_name;
    public static String DoctorSelected;
    private static String ClinicName;


    //used in SearchPatientCancelAppointment Class
    public static String CancelAppPatientIDSearchPatient;
    public static String CancelAppPatientNameSearchPatient;
    public static Long CancelAppPatientMobileSearchPatient;

    public static String [] D_id= new String[length];
    public static String [] D_specialisation = new String[length];
    public static String [] D_Name=new String[length];

    public static String DoctorNameList4array;
    public static String DoctorIDList4array;
    public static String DoctorSlotList4array;

    public static int DateSelect;
    public static int dateToday;
    public static int SlotDateSelected;


    public static List<String> listID=new ArrayList<>();
    public static List<String> listOfDoctorsRegisteredWithClinic=new ArrayList<>();
    public static List<Integer> SlotLength=new ArrayList<>();
    public static List<String> PatientIDofSelectedDocs=new ArrayList<String>(); public static String[] patientsIDCurrentday;
    public static List<String> SlotIDCurrentAppoint=new ArrayList<String>();    public static String[] slotIDCurrentday;
    public static List<String> LnameCurrentDayPatients=new ArrayList<String>(); public static String[] LnameCurrentday;
    public static List<String> FnameCurrentDayPatients=new ArrayList<String>(); public static String[] FnameCurrentday;
    public static List<Long> MobileCurrentDayPatients=new ArrayList<Long>();    public static String[] MovileCurrentDay;
    public static List<String> SlotStatusofPatientforCheckin=new ArrayList<String>();
    public static List<String> EmailCurrentDayPatients=new ArrayList<>();
    public static List<String> GenderCurrentDayPatients=new ArrayList<>();
    public static List<Integer> AgeCurrentDayPatients=new ArrayList<>();


    public static String getTokenDB() {
        return TokenDB;
    }
    public static void setTokenDB(String tokenDB) {
        TokenDB = tokenDB;
    }

    public static Integer[] slotOfDocs;
    public static String [] nameArrayDoc;
    public static String [] idArray;
    public static String [] Availability_day;
    public static Integer [] Availability_Time;
    public static int slotLength; //Used in SetSchedule.java
    public static int dateSelectedSetSchceduleDate; //Useed in ScheduleDate;

    public static int getDateSelect() {
        return DateSelect;
    }

    public static void setDateSelect(int dateSelect) {
        DateSelect = dateSelect;
    }


    public static String getDoctorSelectedId() {
        return DoctorSelectedId;
    }

    public static void setDoctorSelectedId(String doctorSelectedId) {
        DoctorSelectedId = doctorSelectedId;
    }


    public static String getFirst_name() {
        return first_name;
    }

    public static void setFirst_name(String first_name) {
        Constants.first_name = first_name;
    }

    public static String getLast_name() {
        return last_name;
    }

    public static void setLast_name(String last_name) {
        Constants.last_name = last_name;
    }

    public static String last_name;

    public static long getMobile_number() {
        return mobile_number;
    }

    public static void setMobile_number(long mobile_number) {
        Constants.mobile_number = mobile_number;
    }

    public static long mobile_number;

    public static String getSlotSelected() {
        return SlotSelected;
    }

    public static void setSlotSelected(String slotSelected) {
        SlotSelected = slotSelected;
    }

    public static String SlotSelected;

    public static String[] getTimeSlot() {
        return TimeSlot;
    }

    public static void setTimeSlot(String[] timeSlot) {
        TimeSlot = timeSlot;
    }

    public static String[] TimeSlot;

    public static HashMap<String,String> map = new HashMap<String, String>();

    public static HashMap<String, String> getMap() {
        return map;
    }
    public static void setValue(HashMap<String, String> map)
    {
        Constants.map = map;
    }
    public static int getLength() {
        return length;
    }

    public static void setLength(int length) {
        Constants.length = length;
    }

    public static void setDoctorID(String DoctorID)
    {
        Constants.D_id[idNumber]=DoctorID;
        idNumber++;
    }

    public static  String getDoctorID(int i)
    {
        return D_id[i];
    }

    public static void setDoctorName(String f_name,String l_name)
    {
        Constants.D_Name[docnamenumber]=f_name+" "+l_name;
        docnamenumber++;
    }

    public static  String getDoctorName(int i)
    {
        return D_Name[i];
    }

    public static String getD_specialisation( int i ) {
        return D_specialisation[i];
    }

    public static String[] getNameArrayDoc() {
        return nameArrayDoc;
    }

    public static void setNameArrayDoc(String[] nameArrayDoc) {
        Constants.nameArrayDoc = nameArrayDoc;
    }

    public static String[] getIdArray() {
        return idArray;
    }

    public static void setIdArray(String[] idArray) {
        Constants.idArray = idArray;
    }

    public static String[] getAvailability_day() {
        return Availability_day;
    }

    public static void setAvailability_day(String[] availability_day) {
        Availability_day = availability_day;
    }

    public static Integer[] getAvailability_Time() {
        return Availability_Time;
    }

    public static void setAvailability_Time(Integer[] availability_Time) {
        Availability_Time = availability_Time;
    }

    public static String getDoctorSelected() {
        return DoctorSelected;
    }

    public static void setDoctorSelected(String doctorSelected) {
        DoctorSelected = doctorSelected;
    }

    public static void setD_specialisation(String d_specialisation) {
        D_specialisation[idNumber] = d_specialisation;

        idNumber++;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Constants.username = username;
    }

    public static String getCurrent_patient_id() {
        return current_patient_id;
    }

    public static void setCurrent_patient_id(String current_patient_id) {
        Constants.current_patient_id = current_patient_id;
    }


    public static int getSlotLength() {
        //Used in SetSchedule.java
        return slotLength;
    }

    public static void setSlotLength(int slotLength) {
        //Used in SetSchedule.java
        Constants.slotLength = slotLength;
    }

    public static int getSlotDateSelected() {
        return SlotDateSelected;
    }

    public static void setSlotDateSelected(int slotDateSelected) {
        SlotDateSelected = slotDateSelected;
    }

    public static int getDateToday() {
        return dateToday;
    }

    public static void setDateToday(int dateToday) {
        Constants.dateToday = dateToday;
    }

    public static int getDateSelectedSetSchceduleDate() {
        return dateSelectedSetSchceduleDate;
    }

    public static void setDateSelectedSetSchceduleDate(int dateSelectedSetSchceduleDate) {
        Constants.dateSelectedSetSchceduleDate = dateSelectedSetSchceduleDate;
    }

    public static String getCancelAppPatientIDSearchPatient() {
        return CancelAppPatientIDSearchPatient;
    }

    public static void setCancelAppPatientIDSearchPatient(String cancelAppPatientIDSearchPatient) {
        CancelAppPatientIDSearchPatient = cancelAppPatientIDSearchPatient;
    }

    public static String getCancelAppPatientNameSearchPatient() {
        return CancelAppPatientNameSearchPatient;
    }

    public static void setCancelAppPatientNameSearchPatient(String cancelAppPatientNameSearchPatient) {
        CancelAppPatientNameSearchPatient = cancelAppPatientNameSearchPatient;
    }

    public static Long getCancelAppPatientMobileSearchPatient() {
        return CancelAppPatientMobileSearchPatient;
    }

    public static void setCancelAppPatientMobileSearchPatient(Long cancelAppPatientMobileSearchPatient) {
        CancelAppPatientMobileSearchPatient = cancelAppPatientMobileSearchPatient;
    }

    public static String getClinicName() {
        return ClinicName;
    }

    public static void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }
}
