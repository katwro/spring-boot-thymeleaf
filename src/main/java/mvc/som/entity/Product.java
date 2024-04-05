package mvc.som.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import mvc.som.constant.AppConstants;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int id;

    @NotEmpty(message = AppConstants.REQUIRED_FIELD)
    @Column(name = "Name")
    private String name;

    @NotEmpty(message = AppConstants.REQUIRED_FIELD)
    @Column(name = "ProductIndex")
    private String productIndex;

    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    @Column(name = "Price")
    private double price;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})

    private List<OrderDetail> orderDetails;

}

