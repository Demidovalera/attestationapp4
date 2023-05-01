package rea.yooolio.attestationspringapp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rea.yooolio.attestationspringapp.dto.dtoo.ProductDTOO;
import rea.yooolio.attestationspringapp.entities.CartEntity;
import rea.yooolio.attestationspringapp.entities.ProductEntity;
import rea.yooolio.attestationspringapp.entities.UserEntity;
import rea.yooolio.attestationspringapp.repositories.CartRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public void save(int userId, int productId) {
        UserEntity userEntity = userService.findById(userId).orElse(null);
        ProductEntity productEntity = productService.findByIdOpt(productId).orElse(null);

        cartRepository.save(new CartEntity(userEntity, productEntity));
    }

    public List<ProductEntity> findAllByUserId(int userId) {
        UserEntity userEntity = new UserEntity(userId);
        return cartRepository.findAllByUserEntity(userEntity).stream().map(CartEntity::getProductEntity).toList();
    }

    public List<ProductDTOO> findAllDTOOSByUserId(int userId) {
        List<ProductDTOO> productDTOOS = new ArrayList<>();
        UserEntity userEntity = new UserEntity(userId);

        for (CartEntity cartEntity : cartRepository.findAllByUserEntity(userEntity)) {
            productDTOOS.add(CartEntity.convertToProductDTOO(cartEntity));
        }

        return productDTOOS;
    }

    public Optional<CartEntity> findByUserIdAndProductId(int userId, int productId) {
        UserEntity userEntity = userService.findById(userId).orElse(null);
        ProductEntity productEntity = productService.findByIdOpt(productId).orElse(null);

        return cartRepository.findByUserEntityAndProductEntity(userEntity, productEntity);
    }

    @Transactional
    public void delete(int userId, int productId) {
        findByUserIdAndProductId(userId, productId).ifPresent(cartRepository::delete);
    }

    @Transactional
    public void clean(UserEntity userEntity) {
        cartRepository.deleteAllByUserEntity(userEntity);
    }

    @Transactional
    public void cleanByUserId(int userId) {
        Optional<UserEntity> userEntity = userService.findById(userId);

        userEntity.ifPresent(this::clean);
    }
}
