package rea.yooolio.attestationspringapp.entities;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import rea.yooolio.attestationspringapp.embeddable.OrdersProductsKey;

@Entity
@Table(name = "orders_products")
public class OrdersProductsEntity {
    @EmbeddedId
    private OrdersProductsKey id;
    @ManyToOne
    @MapsId(value = "orderId")
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
    @ManyToOne
    @MapsId(value = "productId")
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public OrdersProductsEntity() {

    }

    public OrdersProductsEntity(@NotNull OrderEntity orderEntity, @NotNull ProductEntity productEntity) {
        id = new OrdersProductsKey(orderEntity.getId(), productEntity.getId());
        this.orderEntity = orderEntity;
        this.productEntity = productEntity;
    }

    public OrdersProductsKey getId() {
        return id;
    }

    public void setId(OrdersProductsKey id) {
        this.id = id;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}
