package vn.edu.hcmuaf.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findById(Long idCustomer) ;
}
