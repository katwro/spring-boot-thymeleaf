package mvc.som.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import mvc.som.constant.AppConstants;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private int id;

    @NotEmpty(message = AppConstants.REQUIRED_FIELD)
    @Column(name = "FirstName")
    private String firstName;

    @NotEmpty(message = AppConstants.REQUIRED_FIELD)
    @Column(name = "LastName")
    private String lastName;

    @NotEmpty(message = AppConstants.REQUIRED_FIELD)
    @Pattern(regexp = AppConstants.EMAIL_REGEXP, message = AppConstants.INVALID_VALUE)
    @Column(name = "Email")
    private String email;

    @NotEmpty(message = AppConstants.REQUIRED_FIELD)
    @Pattern(regexp = AppConstants.PHONE_REGEXP, message = AppConstants.INVALID_VALUE)
    @Column(name = "Phone")
    private String phone;

    @Column(name = "Number")
    private int number;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<OrderHeader> orderHeaders;

    public Customer() {
        this.orderHeaders = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String email, String phone) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

}
