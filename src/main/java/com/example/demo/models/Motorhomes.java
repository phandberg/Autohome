package com.example.demo.models;

public class Motorhomes {

    public int motorhomeId;
    public int maxSeats;
    public int pricePerDay;
    public String modelName;

    public Motorhomes(int motorhomeId, int maxSeats, int pricePerDay, String modelName) {
        this.motorhomeId = motorhomeId;
        this.maxSeats = maxSeats;
        this.pricePerDay = pricePerDay;
        this.modelName = modelName;
    }

    public Motorhomes() {

    }

    public int getMotorhomeId() {
        return motorhomeId;
    }

    public void setMotorhomeId(int motorhomeId) {
        this.motorhomeId = motorhomeId;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "Motorhomes{" +
                "motorhomeId=" + motorhomeId +
                ", maxSeats=" + maxSeats +
                ", pricePerDay=" + pricePerDay +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
