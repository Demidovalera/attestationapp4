package rea.yooolio.attestationspringapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rea.yooolio.attestationspringapp.entities.OrderEntity;
import rea.yooolio.attestationspringapp.entities.UserEntity;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByUserEntity(UserEntity userEntity);
}
