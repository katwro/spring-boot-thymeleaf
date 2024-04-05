package mvc.som.repository;

import mvc.som.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {

    List<OrderHeader> findAllByOrderByNumberDesc();

    @Query("SELECT " +
            "COALESCE(CONCAT(MAX(FUNCTION('FORMAT', o.date, 'yy')), " +
            "       MAX(FUNCTION('FORMAT', o.date, 'MM')), " +
            "       RIGHT('000000' || CAST((MAX(CAST(RIGHT(o.number, 6) AS INTEGER)) + 1) AS STRING), 6))," +
            " CONCAT(SUBSTRING(cast(:orderDate AS STRING),3,2),SUBSTRING(cast(:orderDate AS STRING),6,2),'000001')) " +
            "FROM OrderHeader o " +
            "WHERE FUNCTION('MONTH', :orderDate) = FUNCTION('MONTH', o.date) " +
            "  AND FUNCTION('YEAR', :orderDate) = FUNCTION('YEAR', o.date)")
    String generateOrderNumber(@Param("orderDate") LocalDate orderDate);

}
