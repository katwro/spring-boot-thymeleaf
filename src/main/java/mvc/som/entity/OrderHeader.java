package mvc.som.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "OrderHeader")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private int id;

    @Column(name = "OrderNumber")
    private String number;

    @PastOrPresent(message = "The selected date is in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "OrderDate")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public OrderHeader() {
        this.orderDetails = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "OrderHeader{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date=" + date +
                '}';
    }

}

