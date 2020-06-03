package com.example.demo.models;

public class RentalPayment {

    int rentalId;
    int pricePerDay;
    int dropoffPrice;
    int accessoriesPrice;
    int cancellationPrice;
    int fuelPrice;
    int kilometersDriven;
    int daysDriven;
    int totalPrice;
    int totalDays;
    int kilometersDrivenPrice;

    public RentalPayment(int rentalId, int pricePerDay, int dropoffPrice, int accessoriesPrice, int cancellationPrice, int fuelPrice, int kilometersDriven, int daysDriven, int totalPrice, int totalDays, int kilometersDrivenPrice) {
        this.rentalId = rentalId;
        this.pricePerDay = pricePerDay;
        this.dropoffPrice = dropoffPrice;
        this.accessoriesPrice = accessoriesPrice;
        this.cancellationPrice = cancellationPrice;
        this.fuelPrice = fuelPrice;
        this.kilometersDriven = kilometersDriven;
        this.daysDriven = daysDriven;
        this.totalPrice = totalPrice;
        this.totalDays = totalDays;
        this.kilometersDrivenPrice = kilometersDrivenPrice;
    }

    public RentalPayment() {

    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getDropoffPrice() {
        return dropoffPrice;
    }

    public void setDropoffPrice(int dropoffPrice) {
        this.dropoffPrice = dropoffPrice;
    }

    public int getAccessoriesPrice() {
        return accessoriesPrice;
    }

    public void setAccessoriesPrice(int accessoriesPrice) {
        this.accessoriesPrice = accessoriesPrice;
    }

    public int getCancellationPrice() {
        return cancellationPrice;
    }

    public void setCancellationPrice(int cancellationPrice) {
        this.cancellationPrice = cancellationPrice;
    }

    public int getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(int fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public int getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(int kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public int getDaysDriven() {
        return daysDriven;
    }

    public void setDaysDriven(int daysDriven) {
        this.daysDriven = daysDriven;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getKilometersDrivenPrice() {
        return kilometersDrivenPrice;
    }

    public void setKilometersDrivenPrice(int kilometersDrivenPrice) {
        this.kilometersDrivenPrice = kilometersDrivenPrice;
    }
}
