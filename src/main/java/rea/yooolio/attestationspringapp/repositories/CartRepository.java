package rea.yooolio.attestationspringapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rea.yooolio.attestationspringapp.entities.CartEntity;
import rea.yooolio.attestationspringapp.entities.ProductEntity;
import rea.yooolio.attestationspringapp.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findAllByUserEntity(UserEntity userEntity);

    Optional<CartEntity> findByUserEntityAndProductEntity(UserEntity userEntity, ProductEntity productEntity);

    void deleteAllByUserEntity(UserEntity userEntity);
}
