package com.example.demo.repositories;

import com.example.demo.models.Accessories;
import com.example.demo.models.RentalPayment;
import com.example.demo.models.Rentals;
import com.example.demo.util.DatabaseConnectionManager;
import com.example.demo.util.dateConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class PaymentRepository {
    com.example.demo.util.dateConverter dateConverter = new dateConverter();
    private Connection conn;

    public PaymentRepository() {
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }


    public RentalPayment read(int rentalId) {
        RentalPayment rentalPaymentToReturn = new RentalPayment();
        try {
            PreparedStatement getSingleRentalPayment = conn.prepareStatement("SELECT * FROM rentalpayment WHERE rentalId =?");
            getSingleRentalPayment.setInt(1, rentalId);
            ResultSet rs = getSingleRentalPayment.executeQuery();
            while (rs.next()) {
                rentalPaymentToReturn = new RentalPayment();
                rentalPaymentToReturn.setRentalId(rs.getInt(1));
                rentalPaymentToReturn.setPricePerDay(rs.getInt(2));
                rentalPaymentToReturn.setDropoffPrice(rs.getInt(3));
                rentalPaymentToReturn.setAccessoriesPrice(rs.getInt(4));
                rentalPaymentToReturn.setCancellationPrice(rs.getInt(5));
                rentalPaymentToReturn.setFuelPrice(rs.getInt(6));
                rentalPaymentToReturn.setKilometersDrivenPrice(rs.getInt(7));
                rentalPaymentToReturn.setTotalPrice(rs.getInt(8));

            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return rentalPaymentToReturn;
    }

    public void calculateKilometerprice(RentalPayment rentalPayment) throws SQLException {
        RentalPayment paymentToReturn = new RentalPayment();
        int kilometer = rentalPayment.getKilometersDriven();
        try {
            PreparedStatement getTotalDays = conn.prepareStatement("SELECT totalDays FROM rentals WHERE rentalId=?");
            getTotalDays.setInt(1, rentalPayment.getRentalId());
            ResultSet rs = getTotalDays.executeQuery();
            while (rs.next()) {
                paymentToReturn = new RentalPayment();
                paymentToReturn.setTotalDays(rs.getInt(1));
            }

            int days = paymentToReturn.getTotalDays();
            int kilometers = kilometer;

            int dropoffprice = days * kilometer;

            int ekstrakilometer = (kilometers / days - 400) * days;
            double convertedToDKK = ekstrakilometer * 7.46;
            int gennemsnit = kilometers/days;
//vi siger der er kørt 4200 kilometer på 10 dage. gennemsnitteligt altså 420 per dag.

            if (gennemsnit > 400) {
                PreparedStatement addKilometerPrice = conn.prepareStatement("UPDATE rentalpayment" + " SET kilometersDrivenPrice=? WHERE rentalId=?");
                addKilometerPrice.setDouble(1, convertedToDKK);
                addKilometerPrice.setInt(2, rentalPayment.getRentalId());
                addKilometerPrice.executeUpdate();
            }
            updateTotalprice(rentalPayment.getRentalId());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropoffPrice(RentalPayment rental) {
        //medarbejderen skriver et kilometer sats og vi ganger med 70 cent pr kilometer.
        try {
            PreparedStatement dropoffPrice = conn.prepareStatement("UPDATE rentalpayment" + " SET dropoffPrice=? WHERE rentalId=?");
            dropoffPrice.setDouble(1, rental.getKilometersDriven() * 5.22); //converted til kroner
            dropoffPrice.setInt(2, rental.getRentalId());
            dropoffPrice.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTotalprice(rental.getRentalId());

    }

    public void fuelprice(RentalPayment rental, int rentalId) {
        System.out.println(rentalId);
        try {
            if (rental.getKilometersDriven() < 50) {
                PreparedStatement addfuelprice = conn.prepareStatement("UPDATE rentalpayment" + " SET fuelPrice = 522 WHERE rentalId=?");
                addfuelprice.setInt(1, rentalId);
                addfuelprice.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTotalprice(rentalId);


    }

    public void cancellationprice(Rentals rentals, int rentalId) throws SQLException {
        Rentals totalPriceToReturn = new Rentals();
        Date datePickupDate = rentals.getPickupDate(); //laver vores pickupdate til nyt objekt
        LocalDate pickupdate = dateConverter.dateConverter(datePickupDate); //konvertere vores pickupDate til Localdate datatype
        LocalDate currentDate = LocalDate.now(); //laver et nyt objekt der tager tiden når programmet kører
        int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currentDate, pickupdate); //ser hvor mange dage der er imellem nu og pickup date

        PreparedStatement getTotalPrice = conn.prepareStatement("SELECT totalPrice FROM rentalpayment WHERE rentalId=?"); //totalPrice skal nok rettes til priceperday * totaldays.
        getTotalPrice.setInt(1, rentalId);
        ResultSet rs = getTotalPrice.executeQuery();
        while (rs.next()) {
            totalPriceToReturn = new Rentals();
            totalPriceToReturn.setTotalPrice(rs.getInt(1));
        }

        try {
            if (noOfDaysBetween > 49) {
                double cancellationtotalprice = totalPriceToReturn.getTotalPrice() * 0.2;
                PreparedStatement minCancellation = conn.prepareStatement("UPDATE rentalpayment" + " SET cancellationPrice =?, totalPrice=? WHERE rentalId=?");
                minCancellation.setDouble(1, cancellationtotalprice);
                minCancellation.setDouble(2, cancellationtotalprice);
                minCancellation.setInt(3, rentalId);
                minCancellation.executeUpdate();

            } else if (noOfDaysBetween <= 49 && noOfDaysBetween >= 15) {
                double cancellationtotalprice = totalPriceToReturn.getTotalPrice() * 0.50;

                PreparedStatement mediumCancellation = conn.prepareStatement("UPDATE rentalpayment" + " SET cancellationPrice =?, totalPrice=? WHERE rentalId=?");
                mediumCancellation.setDouble(1, cancellationtotalprice);
                mediumCancellation.setDouble(2, cancellationtotalprice);
                mediumCancellation.setInt(3, rentalId);
                mediumCancellation.executeUpdate();
            } else if (noOfDaysBetween < 15 && noOfDaysBetween >= 1) {
                double cancellationtotalprice = totalPriceToReturn.getTotalPrice() * 0.80;
                PreparedStatement highCancellation = conn.prepareStatement("UPDATE rentalpayment" + " SET cancellationPrice =?, totalPrice=? WHERE rentalId=?");
                highCancellation.setDouble(1, cancellationtotalprice);
                highCancellation.setDouble(2, cancellationtotalprice);
                highCancellation.setInt(3, rentalId);
                highCancellation.executeUpdate();
            } else {
                PreparedStatement maxCancellation = conn.prepareStatement("UPDATE rentalpayment" + " SET cancellationPrice =?, totalPrice=? WHERE rentalId=?");
                double cancellationtotalprice = totalPriceToReturn.getTotalPrice() * 0.95;
                maxCancellation.setDouble(1, cancellationtotalprice);
                maxCancellation.setDouble(2, cancellationtotalprice);
                maxCancellation.setInt(3, rentalId);
                maxCancellation.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void updateTotalprice(int rentalId) {
        RentalPayment rentalpayment = new RentalPayment();
        try {
            PreparedStatement updateTotalPrice = conn.prepareStatement("SELECT dropoffPrice, accessoriesPrice, cancellationPrice, fuelPrice, kilometersDrivenPrice, totalPrice, totalDays, pricePerDay FROM rentalpayment INNER JOIN rentals ON rentalpayment.rentalId = rentals.rentalId WHERE rentalpayment.rentalId=?");
            updateTotalPrice.setInt(1, rentalId);
            ResultSet pricesrs = updateTotalPrice.executeQuery();
            while (pricesrs.next()) {
                rentalpayment = new RentalPayment();
                rentalpayment.setDropoffPrice(pricesrs.getInt(1));
                rentalpayment.setAccessoriesPrice(pricesrs.getInt(2));
                rentalpayment.setCancellationPrice(pricesrs.getInt(3));
                rentalpayment.setFuelPrice(pricesrs.getInt(4));
                rentalpayment.setKilometersDrivenPrice(pricesrs.getInt(5));
                rentalpayment.setTotalPrice(pricesrs.getInt(6));
                rentalpayment.setTotalDays(pricesrs.getInt(7));
                rentalpayment.setPricePerDay(pricesrs.getInt(8));

            }
            int totalpricebase = rentalpayment.getTotalDays() * rentalpayment.getPricePerDay();
            PreparedStatement totalPriceUpdate = conn.prepareStatement("UPDATE rentalpayment" + " SET totalPrice=? WHERE rentalId=?");
            totalPriceUpdate.setDouble(1,  rentalpayment.getDropoffPrice() + rentalpayment.getAccessoriesPrice() + rentalpayment.getCancellationPrice() + rentalpayment.getFuelPrice() + rentalpayment.getKilometersDrivenPrice() + totalpricebase);
            totalPriceUpdate.setInt(2, rentalId);
            totalPriceUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RentalPayment selectTotalPrice(int rentalId){
        RentalPayment rentalPaymentToReturn = new RentalPayment();
        try{
            PreparedStatement selectTotalPrice = conn.prepareStatement("SELECT totalPrice FROM rentalpayment WHERE rentalId=?");
            selectTotalPrice.setInt(1, rentalId);
            ResultSet rs = selectTotalPrice.executeQuery();
            while(rs.next()){
                rentalPaymentToReturn = new RentalPayment();
                rentalPaymentToReturn.setTotalPrice(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalPaymentToReturn;
    }

    public void Accessoriescalculated(Accessories accessories)  {
        RentalPayment paymentToReturn = new RentalPayment();

        //Accessories accessoriesToReturn = new Accessories();
    try {
        int cykelholder = accessories.getCykelholder();
        int sengelinned = accessories.getSengelinned();
        int barnesaede = accessories.getBarnesaede();
        int picnicbord = accessories.getPicnicbord();
        int accessoriesprice = (cykelholder * 400) + (sengelinned * 200) + (barnesaede * 200) + (picnicbord * 300);


        PreparedStatement addAccessprices = conn.prepareStatement("UPDATE rentalpayment" + " SET accessoriesPrice=? WHERE rentalId=?");
        addAccessprices.setInt(1, accessoriesprice);
        addAccessprices.setInt(2, accessories.getRentalId());
        addAccessprices.executeUpdate();

        updateTotalprice(accessories.getRentalId());


    } catch (SQLException e) {
        e.printStackTrace();

    }
    }

    public boolean updateRentalPayment(RentalPayment rentalPayment) {
        try {
            PreparedStatement updateMotorhome = conn.prepareStatement("UPDATE rentalPayment" + "  SET pricePerDay=?, dropoffPrice=?, accessoriesPrice=?," +
                    " cancellationPrice=?, fuelPrice=?, kilometersDrivenPrice=?, totalPrice=?  WHERE rentalId=?");

            updateMotorhome.setInt(1, rentalPayment.getPricePerDay());
            updateMotorhome.setInt(2, rentalPayment.getDropoffPrice());
            updateMotorhome.setInt(3, rentalPayment.getAccessoriesPrice());
            updateMotorhome.setInt(4, rentalPayment.getCancellationPrice());
            updateMotorhome.setInt(5, rentalPayment.getFuelPrice());
            updateMotorhome.setInt(6, rentalPayment.getKilometersDrivenPrice());
            updateMotorhome.setInt(7, rentalPayment.getTotalPrice());
            updateMotorhome.setInt(8, rentalPayment.getRentalId());
            updateMotorhome.executeUpdate();

            updateTotalprice(rentalPayment.getRentalId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteRentalAndPayment(int rentalId){
        try{
            PreparedStatement deleteRental = conn.prepareStatement("DELETE FROM rentals WHERE rentalId=?");
            deleteRental.setInt(1, rentalId);
            deleteRental.executeUpdate();

            PreparedStatement deletePayment = conn.prepareStatement("DELETE FROM rentalPayment WHERE rentalId=?");
            deletePayment.setInt(1, rentalId);
            deletePayment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}