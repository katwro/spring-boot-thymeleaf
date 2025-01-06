package mvc.som.service;

import mvc.som.entity.Customer;
import mvc.som.entity.OrderDetail;
import mvc.som.entity.OrderHeader;
import mvc.som.entity.Product;

import java.util.List;

public interface OrderService {

    List<Customer> findAllCustomers();

    List<Customer> searchCustomer(String searchTerm);

    List<OrderHeader> findAllOrders();

    List<OrderDetail> findAllOrderDetails();

    List<Product> findAllProducts();

    List<Product> searchProduct(String searchTerm);

    Customer findCustomerById(int id);

    OrderHeader findOrderById(int id);

    Product findProductById(int id);

    void saveCustomer(Customer customer);

    void saveOrder(OrderHeader order);

    void saveProduct(Product product);

    void deleteCustomerById(int id);

    void deleteOrderById(int id);

    void deleteProductById(int id);

}
