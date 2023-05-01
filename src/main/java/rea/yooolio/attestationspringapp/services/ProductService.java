package rea.yooolio.attestationspringapp.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rea.yooolio.attestationspringapp.dto.dtio.ProductDTIO;
import rea.yooolio.attestationspringapp.dto.dtoo.ProductDTOO;
import rea.yooolio.attestationspringapp.entities.ProductEntity;
import rea.yooolio.attestationspringapp.repositories.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTOO> findAll(String search, String from, String to, String sort) {
        List<ProductDTOO> productDTOOS = new ArrayList<>();

        if (search != null) {
            if (from != null) {
                if (to != null) {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findByTitleContainsWherePriceFromTo(search, from, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findByTitleContainsWherePriceFromTo(search, from, to))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                } else {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findByTitleContainsWherePriceFrom(search, from, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findByTitleContainsWherePriceFrom(search, from))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                }
            } else {
                if (to != null) {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findByTitleContainsWherePriceTo(search, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findByTitleContainsWherePriceTo(search, to))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                } else {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findByTitleContains(search, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findByTitleContains(search))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                }
            }
        } else {
            if (from != null) {
                if (to != null) {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findByPriceBetween(from, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findByPriceBetween(from, to))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                } else {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findByPriceGreaterThan(from, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findByPriceGreaterThan(from))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                }
            } else {
                if (to != null) {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findByPriceLessThan(to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findByPriceLessThan(to))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                } else {
                    if (sort != null) {
                        for (ProductEntity productEntity : productRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    } else {
                        for (ProductEntity productEntity : productRepository.findAll())
                            productDTOOS.add(ProductEntity.convertToProductDTOO(productEntity));
                    }
                }
            }
        }

        return productDTOOS;
    }

    @Transactional
    public void save(@NotNull ProductDTIO productDTIO) throws IOException {
        MultipartFile multipartFile = productDTIO.getMultipartFile();

        multipartFile.transferTo(new File(ProductDTIO.PRODUCTS_IMAGES_PATH + multipartFile.getOriginalFilename()));
        productRepository.save(ProductDTIO.convertToProductEntity(productDTIO));
    }

    public ProductDTOO findById(int id) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);

        return productEntityOptional.map(ProductEntity::convertToProductDTOO).orElse(null);

    }

    public Optional<ProductEntity> findByIdOpt(int id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void edit(int id, @NotNull ProductDTIO productDTIO) throws IOException {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow();
        File oldFile = new File(productEntity.getFilePath());
        MultipartFile multipartFile = productDTIO.getMultipartFile();
        File newFile = new File(ProductDTIO.PRODUCTS_IMAGES_PATH + multipartFile.getOriginalFilename());

        if (!multipartFile.isEmpty()) {
            multipartFile.transferTo(newFile);
            productRepository.save(ProductDTIO.convertToProductEntity(productDTIO, id));

            if (!oldFile.getPath().equals(newFile.getPath()))
                if (!oldFile.delete()) throw new IOException();
        } else {
            productEntity.setTitle(productDTIO.getTitle());
            productEntity.setPrice(productDTIO.getPrice());
            productRepository.save(productEntity);
        }
    }

    @Transactional
    public void deleteById(int id) throws IOException {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if (productEntity != null) {
            File file = new File(productEntity.getFilePath());

            if (!file.delete()) throw new IOException();
        }

        productRepository.deleteById(id);
    }
}
