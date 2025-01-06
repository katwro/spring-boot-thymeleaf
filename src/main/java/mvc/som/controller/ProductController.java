package mvc.som.controller;

import jakarta.validation.Valid;
import mvc.som.entity.Product;
import mvc.som.service.OrderService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    private OrderService orderService;

    public ProductController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String showProductList(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model) {
        List<Product> products;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            products = orderService.searchProduct(searchTerm);
        } else {
            products = orderService.findAllProducts();
        }
        model.addAttribute("products", products);
        return "products/product-list";
    }

    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "products/product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "products/product-form";
        } else {
            redirectAttributes.addFlashAttribute("success", "The product has been saved.");
            orderService.saveProduct(product);
            return "redirect:/product/list";
        }
    }

    @GetMapping("/update")
    public String showUpdateProductForm(@RequestParam("productId") int id, Model model) {
        Product product = orderService.findProductById(id);
        model.addAttribute("product", product);
        return "products/product-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("productId") int id, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteProductById(id);
            redirectAttributes.addFlashAttribute("success", "The product has been deleted.");
        } catch (DataIntegrityViolationException exc) {
            redirectAttributes.addFlashAttribute("error", "Deletion of the product is not allowed. Verify if it has been assigned to any order.");
        }
        return "redirect:/product/list";
    }

}
