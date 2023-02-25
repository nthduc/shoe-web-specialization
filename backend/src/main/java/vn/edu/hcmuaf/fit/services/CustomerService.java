package vn.edu.hcmuaf.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.models.Customer;
import vn.edu.hcmuaf.fit.repositories.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findById(id));
        return customerOptional.orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void saveCustomer(Customer customer) {
        customerRepository.saveAndFlush(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteAllByIdInBatch(Collections.singleton(id));
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
