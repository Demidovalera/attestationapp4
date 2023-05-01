package rea.yooolio.attestationspringapp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rea.yooolio.attestationspringapp.entities.OrderEntity;
import rea.yooolio.attestationspringapp.entities.OrdersProductsEntity;
import rea.yooolio.attestationspringapp.entities.ProductEntity;
import rea.yooolio.attestationspringapp.entities.UserEntity;
import rea.yooolio.attestationspringapp.repositories.OrderRepository;
import rea.yooolio.attestationspringapp.repositories.OrdersProductsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrdersProductsRepository ordersProductsRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, UserService userService, OrdersProductsRepository ordersProductsRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.ordersProductsRepository = ordersProductsRepository;
        this.cartService = cartService;
    }

    @Transactional
    public void save(int userId) {
        OrderEntity orderEntity = new OrderEntity();
        Optional<UserEntity> userEntity = userService.findById(userId);

        userEntity.ifPresent(orderEntity::setUserEntity);
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setStatus("создан");

        orderRepository.save(orderEntity);

        List<ProductEntity> productEntities = new ArrayList<>(cartService.findAllByUserId(userId));

        for (ProductEntity productEntity : productEntities)
            ordersProductsRepository.save(new OrdersProductsEntity(orderEntity, productEntity));

        userEntity.ifPresent(cartService::clean);
    }

    public List<OrderEntity> findAllByUserId(int userId) {
        Optional<UserEntity> userEntity = userService.findById(userId);

        if (userEntity.isPresent()) return orderRepository.findAllByUserEntity(userEntity.get());

        return Collections.emptyList();
    }

    public List<OrderEntity> findAll(Integer search) {
        if (search != null)
            return orderRepository.findById(search).map(List::of).orElse(Collections.emptyList());

        return orderRepository.findAll();
    }

    public OrderEntity findById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public void changeStatus(int id, String status) {
        orderRepository.findById(id).ifPresent(orderEntity -> {
            orderEntity.setStatus(status);
            orderRepository.save(orderEntity);
        });
    }
}
