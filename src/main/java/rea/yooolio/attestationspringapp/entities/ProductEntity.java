package rea.yooolio.attestationspringapp.entities;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import rea.yooolio.attestationspringapp.dto.dtoo.ProductDTOO;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private BigDecimal price;
    @OneToMany(mappedBy = "productEntity")
    private List<CartEntity> cartEntities;
    @OneToMany(mappedBy = "productEntity")
    private List<OrdersProductsEntity> ordersProductsEntities;

    public ProductEntity() {
    }

    public ProductEntity(String fileName, String filePath, String fileType, long fileSize, String title, BigDecimal price) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.title = title;
        this.price = price;
    }

    public ProductEntity(int id, String fileName, String filePath, String fileType, long fileSize, String title, BigDecimal price) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.title = title;
        this.price = price;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ProductDTOO convertToProductDTOO(@NotNull ProductEntity productEntity) {
        return new ProductDTOO(productEntity.id, productEntity.fileName, productEntity.title, productEntity.price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
