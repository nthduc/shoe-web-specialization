package vn.edu.hcmuaf.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.models.Customer;
/*Interface này kế thừa JpaRepository để thực hiện các thao tác CRUD trên đối tượng Customer. */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email); // tìm kiếm và trả về đối tượng Customer theo địa chỉ email của khách hàng.
    Customer findById(Long idCustomer) ; // tìm kiếm và trả về đối tượng Customer theo ID của khách hàng.
}
