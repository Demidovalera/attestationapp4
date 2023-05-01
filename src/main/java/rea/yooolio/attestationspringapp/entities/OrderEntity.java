package rea.yooolio.attestationspringapp.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"order\"")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "orderEntity")
    private List<OrdersProductsEntity> ordersProductsEntities;

    public OrderEntity() {

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrdersProductsEntity> getOrdersProductsEntities() {
        return ordersProductsEntities;
    }

    public void setOrdersProductsEntities(List<OrdersProductsEntity> ordersProductsEntities) {
        this.ordersProductsEntities = ordersProductsEntities;
    }
}
