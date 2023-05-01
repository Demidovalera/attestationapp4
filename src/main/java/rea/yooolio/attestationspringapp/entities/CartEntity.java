package rea.yooolio.attestationspringapp.entities;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import rea.yooolio.attestationspringapp.dto.dtoo.ProductDTOO;

@Entity
@Table(name = "cart")
public class CartEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public CartEntity() {

    }

    public CartEntity(UserEntity userEntity, ProductEntity productEntity) {
        this.userEntity = userEntity;
        this.productEntity = productEntity;
    }

    @Contract("_ -> new")
    public static @NotNull ProductDTOO convertToProductDTOO(@NotNull CartEntity cartEntity) {
        return ProductEntity.convertToProductDTOO(cartEntity.getProductEntity());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}
