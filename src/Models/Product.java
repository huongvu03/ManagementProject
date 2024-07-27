package Models;

import java.util.Date;

public class Product {

    private String proId;
    private String proName;
    private int cateId;
    private int stock;
    private double proPrice;
    private String status;
    private String proImage;
    private Date proDate;

    private Category category;

    public Product() {
        this.category = new Category();
    }


    public Product(String proId, String proName, int cateId, int stock, double proPrice, String status, String proImage, Date proDate) {
        this.proId = proId;
        this.proName = proName;
        this.cateId = cateId;
        this.stock = stock;
        this.proPrice = proPrice;
        this.status = status;
        this.proImage = proImage;
        this.proDate = proDate;
        this.category = new Category(cateId, "Default Name");
    }

    @Override
    public String toString() {
        return "Product{" + "proId=" + proId + ", proName=" + proName + ", cateId=" + cateId + ", stock=" + stock + ", proPrice=" + proPrice + ", status=" + status + ", proImage=" + proImage + ", proDate=" + proDate + ", category=" + category + '}';
    }

      public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getProPrice() {
        return proPrice;
    }

    public void setProPrice(double proPrice) {
        this.proPrice = proPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
