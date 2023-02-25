package vn.edu.hcmuaf.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.exception.ResourceNotFoundException;
import vn.edu.hcmuaf.fit.models.Customer;
import vn.edu.hcmuaf.fit.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // phương thức lấy danh sách tất cả khách hàng
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // phương thức lấy thông tin khách hàng theo id
    @Override
    public Customer getCustomerById(Long id) throws ResourceNotFoundException {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(id));
        return customer.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));
    }

    // phương thức tạo mới khách hàng
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }
    // phương thức cập nhật thông tin khách hàng
    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) throws ResourceNotFoundException {
        Customer customer = getCustomerById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPassword(customerDetails.getPassword());
        customer.setPhone(customerDetails.getPhone());
        customer.setAddress(customerDetails.getAddress());
        return customerRepository.saveAndFlush(customer);
    }

    // phương thức xóa khách hàng
    @Override
    public void deleteCustomer(Long id) throws ResourceNotFoundException {
        Customer customer = getCustomerById(id);
        customerRepository.deleteAllInBatch((Iterable<Customer>) customer);
    }
}

