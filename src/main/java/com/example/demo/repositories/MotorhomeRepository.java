package com.example.demo.repositories;

import com.example.demo.models.Motorhomes;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotorhomeRepository {
    private Connection conn;
    public MotorhomeRepository() {this.conn = DatabaseConnectionManager.getDatabaseConnection();}


    public List<Motorhomes> listMotorhomes(){
        List<Motorhomes> allMotorhomes = new ArrayList<>();
        try{
            PreparedStatement readMotorhomes = conn.prepareStatement("SELECT motorhomeId, pricePerDay, maxSeats, motorhomes.modelName FROM motorhomes INNER JOIN motorhomemodels ON motorhomes.modelName = motorhomemodels.modelName");
            ResultSet rs = readMotorhomes.executeQuery();
            while(rs.next()){
                Motorhomes tempMotorhome = new Motorhomes();
                tempMotorhome.setMotorhomeId(rs.getInt(1));
                tempMotorhome.setPricePerDay(rs.getInt(2));
                tempMotorhome.setMaxSeats(rs.getInt(3));
                tempMotorhome.setModelName(rs.getString(4));
                allMotorhomes.add(tempMotorhome);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMotorhomes;
    }

    public Boolean createMotorhomeModel(Motorhomes motorhome) {
        try {
            //tilføjer ny model til databasen
            PreparedStatement createMotorhome = conn.prepareStatement("INSERT INTO motorhomemodels(pricePerDay, maxSeats, modelName)" + "VALUES(?,?,?)");
            createMotorhome.setInt(1, motorhome.getPricePerDay());
            createMotorhome.setInt(2, motorhome.getMaxSeats());
            createMotorhome.setString(3, motorhome.getModelName());
            createMotorhome.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;


    }

    public void addToFleet(Motorhomes motorhome){
        try{
            PreparedStatement addtofleet = conn.prepareStatement("INSERT INTO motorhomes(modelName)" + " VALUES(?) ");
            addtofleet.setString(1, motorhome.getModelName());
            addtofleet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int motorhomeId){
        try{
            PreparedStatement deleteMotorhome = conn.prepareStatement("DELETE FROM motorhomes WHERE motorhomeId=?");
            deleteMotorhome.setInt(1, motorhomeId);
            deleteMotorhome.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
    public boolean update(Motorhomes motorhome){
        try{PreparedStatement updateMotorhome = conn.prepareStatement("UPDATE motorhomemodels" + "  SET pricePerDay=?, maxSeats=?, modelName=? WHERE modelName=?");
            updateMotorhome.setInt(1, motorhome.getPricePerDay());
            updateMotorhome.setInt(2, motorhome.getMaxSeats());
            updateMotorhome.setString(3, motorhome.getModelName());
            updateMotorhome.setString(4, motorhome.getModelName());
            updateMotorhome.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    public Motorhomes read(int motorhomeId) {
        Motorhomes motorhomeToReturn = new Motorhomes();
        try {
            PreparedStatement getSingleMotorhome = conn.prepareStatement("SELECT * FROM motorhomes INNER JOIN motorhomemodels ON motorhomes.modelName = motorhomemodels.modelName WHERE motorhomeId=?");
            getSingleMotorhome.setInt(1, motorhomeId );
            ResultSet rs = getSingleMotorhome.executeQuery();
            while (rs.next()) {
                motorhomeToReturn = new Motorhomes();
                motorhomeToReturn.setMotorhomeId(rs.getInt(1));
                motorhomeToReturn.setModelName(rs.getString(2));
                motorhomeToReturn.setPricePerDay(rs.getInt(4));
                motorhomeToReturn.setMaxSeats(rs.getInt(5));

            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return motorhomeToReturn;
    }
}
