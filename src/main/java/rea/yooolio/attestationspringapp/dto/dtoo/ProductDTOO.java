package rea.yooolio.attestationspringapp.dto.dtoo;

import java.math.BigDecimal;

public class ProductDTOO {
    private int id;
    private String fileName;
    private String title;
    private BigDecimal price;

    public ProductDTOO() {

    }

    public ProductDTOO(int id, String fileName, String title, BigDecimal price) {
        this.id = id;
        this.fileName = fileName;
        this.title = title;
        this.price = price;
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
