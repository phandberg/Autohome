package com.example.demo.repositories;

import com.example.demo.models.Customers;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private Connection conn;

    public CustomerRepository(){this.conn = DatabaseConnectionManager.getDatabaseConnection();}

    public void createCustomer(Customers customer) {
        try {
            PreparedStatement createCustomer = conn.prepareStatement(
                    "INSERT INTO customers(customerId, customerFirstName, customerLastName, " +
                            "customerBirthday, customerEmail, customerDriversLicense)" +
                            "VALUES(?,?,?,?,?,?)");
            createCustomer.setInt(1, customer.getCustomerId());
            createCustomer.setString(2, customer.getCustomerFirstName());
            createCustomer.setString(3, customer.getCustomerLastName());
            createCustomer.setString(4, customer.getCustomerBirthday());
            createCustomer.setString(5, customer.getCustomerEmail());
            createCustomer.setInt(6, customer.getCustomerDriversLicense());
            createCustomer.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Customers> customersList(){
        List<Customers> allCustomers =new ArrayList<>();
        try {
            PreparedStatement readCustomers = conn.prepareStatement("SELECT * FROM customers");
            ResultSet rs = readCustomers.executeQuery();
            while(rs.next()){
                Customers tempCustomers = new Customers();
                tempCustomers.setCustomerId(rs.getInt(1));
                tempCustomers.setCustomerFirstName(rs.getString(2));
                tempCustomers.setCustomerLastName(rs.getString(3));
                tempCustomers.setCustomerBirthday(rs.getString(4));
                tempCustomers.setCustomerEmail(rs.getString(5));
                tempCustomers.setCustomerDriversLicense(rs.getInt(6));
                allCustomers.add(tempCustomers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public void deleteCustomer(int customerId){
        try{
            PreparedStatement deleteCustomer = conn.prepareStatement("DELETE FROM customers WHERE customerId=?");
            deleteCustomer.setInt(1, customerId);
            deleteCustomer.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customers customer) {
        System.out.println(customer.getCustomerFirstName());
        try {
            PreparedStatement updateCustomer = conn.prepareStatement("UPDATE customers " +"SET  customerFirstName=?, customerLastName=?, customerBirthday=?, customerEmail=?, customerDriversLicense=? WHERE customerId=?");
            updateCustomer.setString(1,customer.getCustomerFirstName());
            updateCustomer.setString(2,customer.getCustomerLastName());
            updateCustomer.setString(3,customer.getCustomerBirthday());
            updateCustomer.setString(4,customer.getCustomerEmail());
            updateCustomer.setInt(5,customer.getCustomerDriversLicense());
            updateCustomer.setInt(6, customer.getCustomerId());
            updateCustomer.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customers read(int customerId) {
        Customers customerToReturn = new Customers();
        try {
            PreparedStatement getSingleCustomer = conn.prepareStatement("SELECT * FROM customers WHERE customerId=?");
            getSingleCustomer.setInt(1, customerId );
            ResultSet rs = getSingleCustomer.executeQuery();
            while (rs.next()) {
                customerToReturn = new Customers();
                customerToReturn.setCustomerId(rs.getInt(1));
                customerToReturn.setCustomerFirstName(rs.getString(2));
                customerToReturn.setCustomerLastName(rs.getString(3));
                customerToReturn.setCustomerBirthday(rs.getString(4));
                customerToReturn.setCustomerEmail(rs.getString(5));
                customerToReturn.setCustomerDriversLicense(rs.getInt(6));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return customerToReturn;
    }

}