package vn.edu.hcmuaf.fit.shoebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.edu.hcmuaf.fit.shoebackend.exception.ResourceNotFoundException;
import vn.edu.hcmuaf.fit.shoebackend.models.Customer;
import vn.edu.hcmuaf.fit.shoebackend.repositories.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lưu thông tin một khách hàng mới (thêm khách hàng) vào CSDL
    public Customer saveCustomer(Customer customer) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
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

    // Kiểm tra thông tin đăng nhập của một khách hàng
    public Customer login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean passwordMatches = encoder.matches(password, customer.getPassword());

        if (passwordMatches) {
            return customer;
        } else {
            throw new ResourceNotFoundException("Invalid email or password");
        }
    }



}