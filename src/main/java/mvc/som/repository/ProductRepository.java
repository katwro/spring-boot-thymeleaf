package mvc.som.repository;

import mvc.som.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByOrderByName();

    List<Product> findByNameContainingIgnoreCaseOrProductIndexContainingIgnoreCase(
            String searchTerm1, String searchTerm2);
}
