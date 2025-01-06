package mvc.som.service;

import org.springframework.stereotype.Service;
import mvc.som.entity.Customer;
import mvc.som.entity.OrderDetail;
import mvc.som.entity.OrderHeader;
import mvc.som.entity.Product;
import mvc.som.repository.CustomerRepository;
import mvc.som.repository.OrderDetailRepository;
import mvc.som.repository.OrderHeaderRepository;
import mvc.som.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final CustomerRepository customerRepository;
    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(CustomerRepository customerRepository, OrderHeaderRepository orderHeaderRepository,
                            OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Customer> findAllCustomers() {

        return customerRepository.findAllByOrderByNumber();
    }

    @Override
    public List<Customer> searchCustomer(String searchTerm) {
        return customerRepository.searchCustomers(searchTerm);
    }

    @Override
    public List<OrderHeader> findAllOrders() {
        return orderHeaderRepository.findAllByOrderByNumberDesc();
    }

    @Override
    public List<OrderDetail> findAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAllByOrderByName();
    }

    @Override
    public List<Product> searchProduct(String searchTerm) {
        return productRepository.findByNameContainingIgnoreCaseOrProductIndexContainingIgnoreCase(
                searchTerm,searchTerm);
    }

    @Override
    public Customer findCustomerById(int id) {
        Optional<Customer> result = customerRepository.findById(id);

        Customer customer;

        if (result.isPresent()) {
            customer = result.get();
        } else {
            throw new RuntimeException("No result found for id: " + id);
        }
        return customer;
    }

    @Override
    public OrderHeader findOrderById(int id) {
        Optional<OrderHeader> result = orderHeaderRepository.findById(id);

        OrderHeader order;

        if (result.isPresent()) {
            order = result.get();
        } else {
            throw new RuntimeException("No result found for id: " + id);
        }
        return order;
    }

    @Override
    public Product findProductById(int id) {
        Optional<Product> result = productRepository.findById(id);

        Product product;

        if (result.isPresent()) {
            product = result.get();
        } else {
            throw new RuntimeException("No result found for id: " + id);
        }
        return product;
    }

    @Override
    public void saveCustomer(Customer customer) {
        if (customer.getNumber() == 0) {
            customer.setNumber(customerRepository.generateCustomerNumber());
        }
        customer.setPhone(customer.getPhone().replaceAll("\\D", ""));
        customerRepository.save(customer);
    }

    @Override
    public void saveOrder(OrderHeader order) {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails != null) {
            for (OrderDetail orderDetail : orderDetails) {

                Product product = orderDetail.getProduct();
                if (product != null) {
                    orderDetail.setUnitPrice(product.getPrice());
                }
                orderDetail.setOrderHeader(order);
            }
        }
        order.setNumber(orderHeaderRepository.generateOrderNumber(order.getDate()));

        orderHeaderRepository.save(order);
    }

    @Override
    public void saveProduct(Product product) {
        product.setProductIndex(product.getProductIndex().toUpperCase());
        productRepository.save(product);
    }

    @Override
    public void deleteCustomerById(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void deleteOrderById(int id) {
        orderHeaderRepository.deleteById(id);
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

}
