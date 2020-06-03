package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

public class Employees {

public int employeeId;
public String employeeFirstName;
public String employeeLastName;
@DateTimeFormat(pattern = "yyyy-MM-dd")
public String employeeBirthday;
public String employeeEmail;
public String employeeJob;

    public Employees(int employeeId, String employeeFirstName, String employeeLastName, String employeeBirthday, String employeeEmail, String employeeJob) {
        this.employeeId = employeeId;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeBirthday = employeeBirthday;
        this.employeeEmail = employeeEmail;
        this.employeeJob = employeeJob;
    }

    public Employees() {

    }


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeBirthday() {
        return employeeBirthday;
    }

    public void setEmployeeBirthday(String employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeJob() {
        return employeeJob;
    }

    public void setEmployeeJob(String employeeJob) {
        this.employeeJob = employeeJob;
    }


    @Override
    public String toString() {
        return "Employees{" +
                "employeeId=" + employeeId +
                ", employeeFirstName='" + employeeFirstName + '\'' +
                ", employeeLastName='" + employeeLastName + '\'' +
                ", employeeBirthday='" + employeeBirthday + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeJob='" + employeeJob + '\'' +
                '}';
    }
}
