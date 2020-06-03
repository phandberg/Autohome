package com.example.demo.repositories;

import com.example.demo.models.Employees;
import com.example.demo.models.Motorhomes;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

private Connection conn;

public EmployeeRepository(){this.conn = DatabaseConnectionManager.getDatabaseConnection();}

public Boolean createEmployee(Employees employee) {
    try {
        PreparedStatement createEmployee = conn.prepareStatement("INSERT INTO employees(employeeFirstName, employeeLastName, employeeBirthday, employeeEmail, employeeJob)" + "VALUES(?,?,?,?,?)");
        createEmployee.setString(1, employee.getEmployeeFirstName());
        createEmployee.setString(2, employee.getEmployeeLastName());
        createEmployee.setString(3, employee.getEmployeeBirthday());
        createEmployee.setString(4, employee.getEmployeeEmail());
        createEmployee.setString(5, employee.getEmployeeJob());
        createEmployee.executeUpdate();


    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;


}

    public List<Employees> listEmployees(){
    List<Employees> allEmployees =new ArrayList<>();
    try{
    PreparedStatement reademployees = conn.prepareStatement("SELECT * FROM employees");
        ResultSet rs = reademployees.executeQuery();
        while(rs.next()){
        Employees tempemployees = new Employees();
        tempemployees.setEmployeeId(rs.getInt(1));
        tempemployees.setEmployeeFirstName(rs.getString(2));
        tempemployees.setEmployeeLastName(rs.getString(3));
        tempemployees.setEmployeeBirthday(rs.getString(4));
        tempemployees.setEmployeeEmail(rs.getString(5));
        tempemployees.setEmployeeJob(rs.getString(6));
        allEmployees.add(tempemployees);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return allEmployees;

    }

    public boolean delete(int employeeId){
        try{
            PreparedStatement deleteEmployee = conn.prepareStatement("DELETE FROM employees WHERE employeeId=?");
            deleteEmployee.setInt(1, employeeId);
            deleteEmployee.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    public Employees read(int employeeId) {
        Employees employeeToReturn = new Employees();
        try {
            PreparedStatement getSingleEmployee = conn.prepareStatement("SELECT * FROM employees WHERE employeeId=?");
            getSingleEmployee.setInt(1, employeeId );
            ResultSet rs = getSingleEmployee.executeQuery();
            while (rs.next()) {
                employeeToReturn = new Employees();
                employeeToReturn.setEmployeeId(rs.getInt(1));
                employeeToReturn.setEmployeeFirstName(rs.getString(2));
                employeeToReturn.setEmployeeLastName(rs.getString(3));
                employeeToReturn.setEmployeeBirthday(rs.getString(4));
                employeeToReturn.setEmployeeEmail(rs.getString(5));
                employeeToReturn.setEmployeeJob(rs.getString(6));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return employeeToReturn;
    }

    public boolean update(Employees employee){
        try{PreparedStatement updateEmployees = conn.prepareStatement("UPDATE employees" + " SET employeeFirstName=?, employeeLastName=?, employeeBirthday=?, employeeEmail=?, employeeJob=? WHERE employeeId=?");
            updateEmployees.setString(1, employee.getEmployeeFirstName());
            updateEmployees.setString(2, employee.getEmployeeLastName());
            updateEmployees.setString(3, employee.getEmployeeBirthday());
            updateEmployees.setString(4, employee.getEmployeeEmail());
            updateEmployees.setString(5, employee.getEmployeeJob());
            updateEmployees.setInt(6, employee.getEmployeeId());

            updateEmployees.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }



}
