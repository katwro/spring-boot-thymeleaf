package mvc.som.controller;

import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import mvc.som.entity.Product;
import mvc.som.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private OrderService orderService;

    public ProductController(OrderService orderService) {
        this.orderService = orderService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String showProductList(Model model) {
        List<Product> products = orderService.findAllProducts();
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
            redirectAttributes.addFlashAttribute("error", "The product cannot be deleted. Check if it has already been assigned to the order.");
        }
        return "redirect:/product/list";
    }

}
