package rea.yooolio.attestationspringapp.controllers;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rea.yooolio.attestationspringapp.dto.dtio.ProductDTIO;
import rea.yooolio.attestationspringapp.dto.dtoo.ProductDTOO;
import rea.yooolio.attestationspringapp.security.UserDetails;
import rea.yooolio.attestationspringapp.services.CartService;
import rea.yooolio.attestationspringapp.services.ProductService;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;
    private final CartService cartService;

    public ProductController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping(value = "")
    public String products(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to, @RequestParam(value = "sort", required = false) String sort, @NotNull Model model) {
        model.addAttribute("products", productService.findAll(search, from, to, sort));

        return "products/products";
    }

    @GetMapping(value = "/add")
    public String addProduct(@NotNull Model model) {
        model.addAttribute("product", new ProductDTIO());

        return "products/add";
    }

    @PostMapping(value = "/add")
    public String addProduct(@ModelAttribute(value = "product") @Valid ProductDTIO productDTIO, @NotNull BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "products/add";

        productService.save(productDTIO);

        return "redirect:/products";
    }

    @GetMapping(value = "/{id}")
    public String viewProduct(@PathVariable("id") int productId, @NotNull Model model) {
        ProductDTOO productDTOO = productService.findById(productId);

        if (productDTOO == null)
            return "redirect:/products";

        model.addAttribute("product", productDTOO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            int userId = ((UserDetails) authentication.getPrincipal()).getId();

            if (cartService.findByUserIdAndProductId(userId, productId).isPresent()) {
                model.addAttribute("is_present", true);

                return "products/product";
            }
        }

        return "products/product";
    }

    @GetMapping(value = "/{id}/edit")
    public String editProduct(@PathVariable("id") int id, @NotNull Model model) {
        ProductDTOO productDTOO = productService.findById(id);

        if (productDTOO == null) return "redirect:/products";

        model.addAllAttributes(Map.of("product_dtoo", productDTOO, "product_dtio", new ProductDTIO()));

        return "products/edit";
    }

    @PatchMapping(value = "/{id}/edit")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("product") @Valid ProductDTIO productDTIO, @NotNull BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "products/edit";

        productService.edit(id, productDTIO);

        return "redirect:/products";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteProduct(@PathVariable("id") int id) throws IOException {
        productService.deleteById(id);

        return "redirect:/products";
    }

    @PostMapping(value = "/{id}/add")
    public String addToCart(@PathVariable("id") int productId, Model model) {
        int userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        cartService.save(userId, productId);

        return "redirect:/products";
    }
}
