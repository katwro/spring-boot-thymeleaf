package mvc.som.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import mvc.som.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAllByOrderByNumber();

    @Query("SELECT COALESCE(MAX(c.number)+1, 1) FROM Customer c")
    Integer generateCustomerNumber();

}
