package com.example.demo.models;

public class Accessories {
    public int cykelholder;
    public  int sengelinned;
    public  int barnesaede;
    public  int picnicbord;
    public Integer rentalId;


    public Accessories(int cykelholder, int sengelinned, int barnesaede, int picnicbord, Integer rentalId) {
        this.cykelholder = cykelholder;
        this.sengelinned = sengelinned;
        this.barnesaede = barnesaede;
        this.picnicbord = picnicbord;
        this.rentalId = rentalId;
    }


    public int getCykelholder() {
        return cykelholder;
    }

    public void setCykelholder(int cykelholder) {
        this.cykelholder = cykelholder;
    }

    public int getSengelinned() {
        return sengelinned;
    }

    public void setSengelinned(int sengelinned) {
        this.sengelinned = sengelinned;
    }

    public int getBarnesaede() {
        return barnesaede;
    }

    public void setBarnesaede(int barnesaede) {
        this.barnesaede = barnesaede;
    }

    public int getPicnicbord() {
        return picnicbord;
    }

    public void setPicnicbord(int picnicbord) {
        this.picnicbord = picnicbord;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }
}