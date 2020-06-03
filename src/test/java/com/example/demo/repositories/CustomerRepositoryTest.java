package com.example.demo.repositories;

import com.example.demo.models.Customers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    CustomerRepository customerRepository = new CustomerRepository();

    @Test
    void updateCustomer() {
        Customers customer = new Customers();
        customer.setCustomerId(119);
        customer.setCustomerFirstName("Charles");

        customerRepository.updateCustomer(customer);
        assertEquals("Tim", customer.getCustomerFirstName());
    }

    @Test
    void deleteCustomer(){
        customerRepository.deleteCustomer(119);
        assertNull(customerRepository.read(119));
    }
}