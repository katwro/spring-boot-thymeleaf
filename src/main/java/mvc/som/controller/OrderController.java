package mvc.som.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import mvc.som.entity.Customer;
import mvc.som.entity.OrderDetail;
import mvc.som.entity.OrderHeader;
import mvc.som.entity.Product;
import mvc.som.service.OrderService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute("customers")
    public List<Customer> allCustomers() {
        return orderService.findAllCustomers();
    }

    @ModelAttribute("products")
    public List<Product> allProducts() {
        return orderService.findAllProducts();
    }

    @GetMapping("/list")
    public String showOrderList(Model model) {
        List<OrderHeader> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "orders/order-list";
    }

    @GetMapping(value = "/list", params = {"customerId"})
    public String showCustomerOrderList(@RequestParam("customerId") int id, Model model) {
        Customer customer = orderService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "orders/order-list";
    }

    @GetMapping("/new")
    public String showNewOrderForm(Model model) {
        OrderHeader order = new OrderHeader();
        model.addAttribute("order", order);
        order.setDate(LocalDate.now());
        return "orders/order-form";
    }

    @PostMapping(value = "/new", params = {"save"})
    public String saveOrder(@Valid @ModelAttribute("order") OrderHeader order, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "orders/order-form";
        } else {
            redirectAttributes.addFlashAttribute("success", "The order has been saved.");
            orderService.saveOrder(order);
            return "redirect:/order/list";
        }
    }

    @PostMapping(value = "/new", params = {"addProduct"})
    public String addProduct(@ModelAttribute("order") OrderHeader order) {
        OrderDetail orderDetail = new OrderDetail();
        order.getOrderDetails().add(orderDetail);
        orderDetail.setQuantity(1);
        return "orders/order-form";
    }

    @PostMapping(value = "/new", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute("order") OrderHeader order,
                                @RequestParam("removeProduct") int orderDetailId) {
        order.getOrderDetails().remove(orderDetailId);
        return "orders/order-form";
    }

    @GetMapping("/details")
    public String showOrderDetails(@RequestParam(value = "orderId") int id, Model model) {
        OrderHeader order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("oderDetails", orderService.findAllOrderDetails());
        return "orders/detail-list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("orderId") int id, RedirectAttributes redirectAttributes) {
        orderService.deleteOrderById(id);
        redirectAttributes.addFlashAttribute("success", "The order has been deleted.");
        return "redirect:/order/list";
    }

}
