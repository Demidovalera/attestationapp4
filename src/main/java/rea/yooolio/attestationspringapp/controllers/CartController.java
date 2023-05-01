package rea.yooolio.attestationspringapp.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rea.yooolio.attestationspringapp.security.UserDetails;
import rea.yooolio.attestationspringapp.services.CartService;
import rea.yooolio.attestationspringapp.services.OrderService;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    private final CartService cartService;
    private final OrderService orderService;

    public CartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping(value = "")
    public String cart(@NotNull Model model) {
        int userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        model.addAttribute("products", cartService.findAllDTOOSByUserId(userId));

        return "cart/cart";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteFromCart(@PathVariable(value = "id") int productId) {
        int userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        cartService.delete(userId, productId);

        return "redirect:/cart";
    }

    @GetMapping(value = "/order")
    public String order() {
        int userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        orderService.save(userId);

        return "redirect:/orders";
    }

    @GetMapping(value = "/clean")
    public String clean() {
        int userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        cartService.cleanByUserId(userId);

        return "redirect:/products";
    }
}
