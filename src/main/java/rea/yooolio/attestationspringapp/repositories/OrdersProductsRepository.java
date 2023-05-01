package rea.yooolio.attestationspringapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rea.yooolio.attestationspringapp.embeddable.OrdersProductsKey;
import rea.yooolio.attestationspringapp.entities.OrdersProductsEntity;

@Repository
public interface OrdersProductsRepository extends JpaRepository<OrdersProductsEntity, OrdersProductsKey> {

}
