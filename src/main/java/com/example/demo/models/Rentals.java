package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Rentals {

    public Integer rentalId;
    public int motorhomeId;
    public String customerFirstName;
    public String customerLastName;
    public int weekNumber;
    public int totalDays;
    public int pricePerDay;
    public int addedPrice;
    public int totalPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // needed for input field on html pages (in order to serve the right format)
    public Date pickupDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // needed for input field on html pages (in order to serve the right format)
    public Date endDate;
    public int phoneNumber;
    public int season;
    public int customerid;


    public Rentals() {

    }

    public Rentals(Integer rentalId, int motorhomeId, String customerFirstName, String customerLastName, int weekNumber, int totalDays, int pricePerDay, int addedPrice, int totalPrice, Date pickupDate, Date endDate, int phoneNumber, int season, int customerid) {
        this.rentalId = rentalId;
        this.motorhomeId = motorhomeId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.weekNumber = weekNumber;
        this.totalDays = totalDays;
        this.pricePerDay = pricePerDay;
        this.addedPrice = addedPrice;
        this.totalPrice = totalPrice;
        this.pickupDate = pickupDate;
        this.endDate = endDate;
        this.phoneNumber = phoneNumber;
        this.season = season;
        this.customerid = customerid;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public int getMotorhomeId() {
        return motorhomeId;
    }

    public void setMotorhomeId(int motorhomeId) {
        this.motorhomeId = motorhomeId;
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

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getAddedPrice() {
        return addedPrice;
    }

    public void setAddedPrice(int addedPrice) {
        this.addedPrice = addedPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    @Override
    public String toString() {
        return "Rentals{" +
                "rentalId=" + rentalId +
                ", motorhomeId=" + motorhomeId +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", weekNumber=" + weekNumber +
                ", totalDays=" + totalDays +
                ", pricePerDay=" + pricePerDay +
                ", addedPrice=" + addedPrice +
                ", totalPrice=" + totalPrice +
                ", pickupDate=" + pickupDate +
                ", endDate=" + endDate +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}

