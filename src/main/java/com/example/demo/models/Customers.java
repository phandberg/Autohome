package com.example.demo.models;

public class Customers {

    public int customerId;
    public String customerFirstName;
    public String customerLastName;
    public String customerBirthday;
    public String customerEmail;
    public int customerDriversLicense;


    public Customers(int customerId, String customerFirstName, String customerLastName, String customerBirthday, String customerEmail, int customerDriversLicense) {
        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerBirthday = customerBirthday;
        this.customerEmail = customerEmail;
        this.customerDriversLicense = customerDriversLicense;
    }

    public Customers() {

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerBirthday() {
        return customerBirthday;
    }

    public void setCustomerBirthday(String customerBirthday) {
        this.customerBirthday = customerBirthday;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getCustomerDriversLicense() {
        return customerDriversLicense;
    }

    public void setCustomerDriversLicense(int customerDriversLicense) {
        this.customerDriversLicense = customerDriversLicense;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId=" + customerId +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", customerBirthday='" + customerBirthday + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerDriversLicense=" + customerDriversLicense +
                '}';
    }
}
