package com.rentauto.customers.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Customer aggregate
 */
public interface CustomerRepository {
    /**
     * Save a customer
     * @param customer The customer to save
     */
    void save(Customer customer);
    
    /**
     * Find a customer by ID
     * @param id The customer ID
     * @return The customer if found
     */
    Optional<Customer> findById(CustomerId id);
    
    /**
     * Find a customer by email
     * @param email The customer email
     * @return The customer if found
     */
    Optional<Customer> findByEmail(CustomerEmail email);
    
    /**
     * Find customers by name (partial match)
     * @param name The customer name to search for
     * @return List of matching customers
     */
    List<Customer> findByName(CustomerName name);
    
    /**
     * Find all customers
     * @return List of all customers
     */
    List<Customer> findAll();
}