package rea.yooolio.attestationspringapp.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rea.yooolio.attestationspringapp.entities.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT p FROM ProductEntity p WHERE p.title LIKE %:search% AND p.price BETWEEN :from AND :to")
    List<ProductEntity> findByTitleContainsWherePriceFromTo(@Param(value = "search") String search, @Param(value = "from") String from, @Param(value = "to") String to, Sort sort);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.title LIKE %:search% AND p.price BETWEEN :from AND :to")
    List<ProductEntity> findByTitleContainsWherePriceFromTo(@Param(value = "search") String search, @Param(value = "from") String from, @Param(value = "to") String to);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.title LIKE %:search% AND p.price >= :from")
    List<ProductEntity> findByTitleContainsWherePriceFrom(@Param(value = "search") String search, @Param(value = "from") String from, Sort sort);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.title LIKE %:search% AND p.price >= :from")
    List<ProductEntity> findByTitleContainsWherePriceFrom(@Param(value = "search") String search, @Param(value = "from") String from);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.title LIKE %:search% AND p.price <= :to")
    List<ProductEntity> findByTitleContainsWherePriceTo(@Param(value = "search") String search, @Param(value = "to") String to, Sort sort);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.title LIKE %:search% AND p.price <= :to")
    List<ProductEntity> findByTitleContainsWherePriceTo(@Param(value = "search") String search, @Param(value = "to") String to);

    List<ProductEntity> findByTitleContains(String search, Sort sort);

    List<ProductEntity> findByTitleContains(String search);

    List<ProductEntity> findByPriceBetween(String from, String to, Sort sort);

    List<ProductEntity> findByPriceBetween(String from, String to);

    List<ProductEntity> findByPriceGreaterThan(String from, Sort sort);

    List<ProductEntity> findByPriceGreaterThan(String from);

    List<ProductEntity> findByPriceLessThan(String from, Sort sort);

    List<ProductEntity> findByPriceLessThan(String from);

    @NotNull List<ProductEntity> findAll(@NotNull Sort sort);
}
