package mvc.som.controller;

import jakarta.validation.Valid;
import mvc.som.entity.Customer;
import mvc.som.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController{

    private OrderService orderService;

    public CustomerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String showCustomerList(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model) {
        List<Customer> customers;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            customers = orderService.searchCustomer(searchTerm);
        } else {
            customers = orderService.findAllCustomers();
        }
        model.addAttribute("customers", customers);
        return "customers/customer-list";
    }

    @GetMapping("/new")
    public String showNewCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customers/customer-form";
    }

    @PostMapping("/save")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customers/customer-form";
        } else {
            redirectAttributes.addFlashAttribute("success", "The customer has been saved.");
            orderService.saveCustomer(customer);
            return "redirect:/customer/list";
        }
    }

    @GetMapping("/update")
    public String showUpdateCustomerForm(@RequestParam("customerId") int id, Model model) {
        Customer customer = orderService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "customers/customer-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("customerId") int id, RedirectAttributes redirectAttributes) {
        orderService.deleteCustomerById(id);
        redirectAttributes.addFlashAttribute("success", "The customer has been deleted.");
        return "redirect:/customer/list";
    }

}
