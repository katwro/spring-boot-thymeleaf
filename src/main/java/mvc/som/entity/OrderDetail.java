package mvc.som.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "OrderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private int id;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "UnitValue")
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private OrderHeader orderHeader;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

}


