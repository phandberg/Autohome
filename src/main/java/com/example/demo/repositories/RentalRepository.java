package com.example.demo.repositories;

import com.example.demo.models.Customers;
import com.example.demo.models.Motorhomes;
import com.example.demo.models.Rentals;
import com.example.demo.util.DatabaseConnectionManager;
import com.example.demo.util.dateConverter;
import com.example.demo.util.sqlDateConverter;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.thymeleaf.preprocessor.PreProcessor;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RentalRepository {
    sqlDateConverter sqlconverter = new sqlDateConverter();
    private Connection conn;
    dateConverter dateconverter = new dateConverter();
    public RentalRepository(){this.conn = DatabaseConnectionManager.getDatabaseConnection();}


    public List<Rentals> listRentals(){
        List<Rentals> allRentals = new ArrayList<>();
        try{
            PreparedStatement readRentals = conn.prepareStatement("SELECT rentals.rentalId, customerFirstName, customerLastName, totalDays, rentals.pickupDate, rentals.endDate, pricePerDay, totalPrice, motorhomeId FROM rentals INNER JOIN rentalpayment ON rentals.rentalId = rentalpayment.rentalId INNER JOIN customers ON rentals.customerId = customers.customerId");
            ResultSet rs = readRentals.executeQuery();
            while(rs.next()){
                Rentals tempRental = new Rentals();
                tempRental.setRentalId(rs.getInt(1));
                tempRental.setCustomerFirstName(rs.getString(2));
                tempRental.setCustomerLastName(rs.getString(3));
                tempRental.setTotalDays(rs.getInt(4));
                tempRental.setPickupDate(rs.getDate(5));
                tempRental.setEndDate(rs.getDate(6));
                tempRental.setPricePerDay(rs.getInt(7));
                tempRental.setTotalPrice(rs.getInt(8));
                tempRental.setMotorhomeId(rs.getInt(9));
                allRentals.add(tempRental);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRentals;
    }




    public List<Motorhomes> searchForRental(int maxSeats, int pricePerDay, Date pickupDate, Date endDate){
        List<Motorhomes> searchedMotorHomes = new ArrayList<>();
        //denne metode tager 4 forskellige parametre, og søger i motorhome tabellen efter passende resultater.
        try{

            //PreparedStatement searchForCar = conn.prepareStatement("select a.motorhomeId, pricePerDay, maxSeats, modelName from motorhomes a left outer join rentals b on a.motorhomeId = b.motorhomeId and b.enddate >= ? and b.pickupdate <= ? where maxSeats =? AND pricePerDay <=? AND b.motorhomeId is null");
            PreparedStatement searchForCar = conn.prepareStatement("select a.motorhomeId, pricePerDay, maxSeats, a.modelName from motorhomes a " +
                    "INNER JOIN motorhomemodels ON a.modelName = motorhomemodels.modelName " +
                    "left outer join rentals b on a.motorhomeId = b.motorhomeId and b.enddate >= ? and b.pickupdate <= ? where maxSeats =? AND pricePerDay <=? AND b.motorhomeId is null");
            // ^denne query er opdateret med de nye tabeller
            //denne SQL query filtrer alle de motorhomes som står i vores rentals tabel der "infringer" de givene datoer, og som har passende seats(senge) og en pris der er lig med eller under kravet.

            searchForCar.setDate(1, pickupDate);
            searchForCar.setDate(2, endDate);
            searchForCar.setInt(3, maxSeats);
            searchForCar.setInt(4, pricePerDay);



            ResultSet rs = searchForCar.executeQuery();
            while(rs.next()){
            Motorhomes tempMotorHome = new Motorhomes();
            tempMotorHome.setMotorhomeId(rs.getInt(1));
            tempMotorHome.setPricePerDay(rs.getInt(2));
            tempMotorHome.setMaxSeats(rs.getInt(3));
            tempMotorHome.setModelName(rs.getString(4));
            searchedMotorHomes.add(tempMotorHome);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchedMotorHomes;


    }

    public Motorhomes readMotorhome(int motorhomeId) {
        Motorhomes motorhomeToReturn = new Motorhomes();
        try {
            PreparedStatement getSingleCourse = conn.prepareStatement("SELECT motorhomeId, pricePerDay, maxSeats, motorhomes.modelName FROM motorhomemodels INNER JOIN motorhomes ON motorhomes.modelName = motorhomemodels.modelName WHERE motorhomeId=?");

            getSingleCourse.setInt(1, motorhomeId );
            ResultSet rs = getSingleCourse.executeQuery();
            while (rs.next()) {
                motorhomeToReturn = new Motorhomes();
                motorhomeToReturn.setMotorhomeId(rs.getInt(1));
                motorhomeToReturn.setPricePerDay(rs.getInt(2));
                motorhomeToReturn.setMaxSeats(rs.getInt(3));
                motorhomeToReturn.setModelName(rs.getString(4));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return motorhomeToReturn;
    }

    public boolean createRental(Rentals rental, Customers customer){
        java.sql.Date pickupDate = new java.sql.Date(rental.getPickupDate().getTime());
        java.sql.Date endDate = new java.sql.Date(rental.getEndDate().getTime());
        Rentals rentaltoreturn = new Rentals();


        try{

            PreparedStatement createRentalCustomer = conn.prepareStatement("INSERT INTO customers(customerFirstName, customerLastName, customerBirthday, customerEmail, customerDriversLicense)"
                    + "VALUES(?,?,?,?,?);");
            createRentalCustomer.setString(1, customer.getCustomerFirstName());
            createRentalCustomer.setString(2, customer.getCustomerLastName());
            createRentalCustomer.setString(3, customer.getCustomerBirthday());
            createRentalCustomer.setString(4, customer.getCustomerEmail());
            createRentalCustomer.setInt(5, customer.getCustomerDriversLicense());
            createRentalCustomer.executeUpdate();

            PreparedStatement getcustomerId = conn.prepareStatement("SELECT max(customerId) FROM customers");
            ResultSet customerid = getcustomerId.executeQuery();
            while(customerid.next()){
                rentaltoreturn = new Rentals();
                rentaltoreturn.setCustomerid(customerid.getInt(1));
            }


            PreparedStatement createRental = conn.prepareStatement("INSERT INTO rentals(motorhomeId, totalDays, pickupDate, endDate, customerId)" + "VALUES(?,?,?,?,?)");
            //createRental.setInt( 1, rental.getRentalId());

            LocalDate pickupdateconverted = dateconverter.dateConverter(rental.getPickupDate());
            LocalDate enddateconverted = dateconverter.dateConverter(rental.getEndDate());
            int noOfDaysBetween = (int) ChronoUnit.DAYS.between(pickupdateconverted, enddateconverted); //ser hvor mange dage der er imellem nu og pickup date
            int noOfDaysBetweenInclusive = noOfDaysBetween + 1; //chronounit tæller ikke enddate med, derfor lægger vi en dag til.
            createRental.setInt( 1, rental.getMotorhomeId());
            createRental.setInt(2, noOfDaysBetweenInclusive);
            createRental.setDate(3, pickupDate);
            createRental.setDate(4, endDate);
            createRental.setInt(5, rentaltoreturn.getCustomerid()); //dette er maxcustomer ID, altså det customerID der lige er blevet genereret i customer querien.
            createRental.executeUpdate();



            if(rental.getSeason() == 1){ //der vælges en season i formen, og prisen ændres afhængigt af hvilken season det er.
                double priceperday = rental.getPricePerDay() * 1.60;
                PreparedStatement createRentalPrices = conn.prepareStatement("INSERT INTO rentalpayment(pricePerDay, totalPrice)" + "VALUES(?,?)");
                // createRentalPrices.setInt(1, rental.getRentalId());
                createRentalPrices.setDouble(1, priceperday);
                createRentalPrices.setDouble(2, priceperday * noOfDaysBetweenInclusive);
                createRentalPrices.executeUpdate();}

            else if(rental.getSeason() == 2){
                double priceperday = rental.getPricePerDay() * 1.30;

                PreparedStatement createRentalPrices = conn.prepareStatement("INSERT INTO rentalpayment(pricePerDay, totalPrice)" + "VALUES(?,?)");
                // createRentalPrices.setInt(1, rental.getRentalId());
                createRentalPrices.setDouble(1, priceperday);
                createRentalPrices.setDouble(2, priceperday * noOfDaysBetweenInclusive  );
                createRentalPrices.executeUpdate();}
            else if(rental.getSeason() == 3){
                double priceperday = rental.getPricePerDay();

                PreparedStatement createRentalPrices = conn.prepareStatement("INSERT INTO rentalpayment(pricePerDay, totalPrice)" + "VALUES(?,?)");
                // createRentalPrices.setInt(1, rental.getRentalId());
                createRentalPrices.setDouble(1, priceperday);
                createRentalPrices.setDouble(2, priceperday * noOfDaysBetweenInclusive);
                createRentalPrices.executeUpdate();}




        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Rentals readRentals (int rentalId) {
        Rentals rentalToReturn = new Rentals();
        try {
            PreparedStatement getSingleRental = conn.prepareStatement("SELECT rentals.rentalId, motorhomeId, totalDays, pickupDate, endDate, totalPrice FROM rentals INNER JOIN rentalpayment ON rentals.rentalId = rentalpayment.rentalId  WHERE rentals.rentalId=?");
            getSingleRental.setInt(1, rentalId );
            ResultSet rs = getSingleRental.executeQuery();
            while (rs.next()) {
                rentalToReturn = new Rentals();
                rentalToReturn.setRentalId(rs.getInt(1));
                rentalToReturn.setMotorhomeId(rs.getInt(2));
                rentalToReturn.setTotalDays(rs.getInt(3));
                rentalToReturn.setPickupDate(rs.getDate(4));
                rentalToReturn.setEndDate(rs.getDate(5));
                rentalToReturn.setTotalPrice(rs.getInt(6));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return rentalToReturn;
    }

    //sæt info i cancelledrentals tabel
    //slet fra rentals table
    public void moverentaltable(int rentalId, int totalPrice) {
        try {
            PreparedStatement moverental = conn.prepareStatement("INSERT INTO cancelledrentals(rentalId, totalPrice)" + " VALUES(?,?)");
            moverental.setInt(1, rentalId);
            moverental.setInt(2, totalPrice);
            moverental.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCancelledRental(int rentalId){
        try{
            PreparedStatement deleteCancelledRental = conn.prepareStatement("DELETE FROM rentals where rentalId=?");
            deleteCancelledRental.setInt(1, rentalId);
            deleteCancelledRental.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
