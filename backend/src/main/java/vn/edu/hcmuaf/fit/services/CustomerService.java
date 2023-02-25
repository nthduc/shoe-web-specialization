package vn.edu.hcmuaf.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.exception.ResourceNotFoundException;
import vn.edu.hcmuaf.fit.models.Customer;
import vn.edu.hcmuaf.fit.repositories.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lưu thông tin một khách hàng mới (thêm khách hàng) vào CSDL
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Lấy danh sách tất cả các khách hàng từ CSDL
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Lấy thông tin một khách hàng theo ID từ CSDL
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    // Xóa thông tin một khách hàng theo ID khỏi CSDL
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    // Cập nhật thông tin một khách hàng theo ID trong CSDL
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPassword(customer.getPassword());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setAddress(customer.getAddress());

        return customerRepository.save(existingCustomer);
    }

}

