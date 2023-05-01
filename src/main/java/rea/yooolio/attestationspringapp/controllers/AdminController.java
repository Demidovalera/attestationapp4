package rea.yooolio.attestationspringapp.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rea.yooolio.attestationspringapp.services.OrderService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final OrderService orderService;

    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders")
    public String orders(@RequestParam(value = "search", required = false) Integer search, @NotNull Model model) {
        model.addAttribute("orders", orderService.findAll(search));

        return "admin/orders";
    }

    @GetMapping(value = "/orders/{id}")
    public String viewOrder(@PathVariable(value = "id") int id, @NotNull Model model) {
        model.addAttribute("order", orderService.findById(id));

        return "admin/order";
    }

    @PatchMapping(value = "/orders/{id}")
    public String editStatus(@PathVariable(value = "id") int id, @ModelAttribute(value = "status") String status) {
        orderService.changeStatus(id, status);

        return "redirect:/admin/orders";
    }
}
