package vn.edu.hcmuaf.fit.services;

import vn.edu.hcmuaf.fit.exception.ResourceNotFoundException;
import vn.edu.hcmuaf.fit.models.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id) throws ResourceNotFoundException;
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customerDetails) throws ResourceNotFoundException;
    void deleteCustomer(Long id) throws ResourceNotFoundException;
}
