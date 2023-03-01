package vn.edu.hcmuaf.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.edu.hcmuaf.fit.exception.InvalidCredentialsException;
import vn.edu.hcmuaf.fit.exception.ResourceNotFoundException;
import vn.edu.hcmuaf.fit.models.Customer;
import vn.edu.hcmuaf.fit.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
/* sử dụng Optional để bọc kết quả trả về từ phương thức , đồng thời tránh được tình trạng null pointer exception nếu kết quả trả về là null.*/
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lưu thông tin một khách hàng mới (thêm khách hàng), mã hóa mật khẩu và lưu vào CSDL
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

    // kiểm tra Email đã tồn tại trong CSDL hay chưa
    /*
    khởi tạo một đối tượng Optional bao bọc một đối tượng Customer,
    trả về bởi phương thức findByEmail trong interface CustomerRepository.
    Phương thức findByEmail được sử dụng để truy vấn một khách hàng dựa trên địa chỉ email.
    Nếu khách hàng tồn tại, phương thức sẽ trả về đối tượng Customer, ngược lại sẽ trả về null.
    */
    public boolean isExistEmail(String email) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByEmail(email));
        return customer.isPresent();
    }

    /*
    Đây là method để xác thực đăng nhập của khách hàng.
    Phương thức này nhận vào hai tham số email và password của khách hàng.
    Sau đó, phương thức sẽ tìm kiếm khách hàng với email được cung cấp trong cơ sở dữ liệu.
    Nếu khách hàng không tồn tại, phương thức sẽ ném ra một ngoại lệ ResourceNotFoundException.
    Nếu khách hàng tồn tại, phương thức sẽ so sánh mật khẩu được cung cấp với mật khẩu của khách hàng.
    Nếu mật khẩu không khớp, phương thức sẽ ném ra một ngoại lệ InvalidCredentialsException.
    Nếu mật khẩu khớp, phương thức sẽ trả về khách hàng được xác thực.
    */
    public Customer login(String email, String password) throws InvalidCredentialsException {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByEmail(email));
        if (customer.isPresent()) {
            String hashedPassword = customer.get().getPassword();
            if (BCrypt.checkpw(password, hashedPassword)) {
                return customer.get();
            } else {
                throw new InvalidCredentialsException("Invalid email or password");
            }
        } else {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

}

