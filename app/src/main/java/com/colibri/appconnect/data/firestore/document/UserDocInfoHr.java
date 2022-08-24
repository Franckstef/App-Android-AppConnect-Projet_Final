package com.colibri.appconnect.data.firestore.document;

import com.google.firebase.firestore.PropertyName;

public class UserDocInfoHr {
    private int employeeNumber;
    private String title;
    private String location;

    UserDocInfoHr() {
    }

    public UserDocInfoHr(int employeeNumber, String title, String location) {
        this.employeeNumber = employeeNumber;
        this.title = title;
        this.location = location;
    }

    @PropertyName("employee_number")
    public int getEmployeeNumber() {
        return employeeNumber;
    }

    @PropertyName("employee_number")
    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "InfoHr{" +
                "employeeNumber=" + employeeNumber +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
