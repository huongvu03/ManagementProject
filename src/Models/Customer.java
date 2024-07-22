package Models;

import java.io.Serializable;
import java.util.Vector;

public class Customer implements Serializable {

    private String cus_id, name, phone, email;
    private int discount, deleted;
    private String proId;
    private String proName;
    private Integer cateId;
    private Integer quantity;
    private Double proPrice;
    private String status;
    private String proImage;
    private Double billTotal;
    private Double billTax;
    private Double billService;
    private Double billSubTotal;
    private String userName;

    public Customer( String cus_id,String proId, String proName, Integer cateId, Integer quantity, Double proPrice, String status, String proImage,Double billTotal,Double billTax,Double billService, Double billSubTotal, String userName) {
        this.cus_id=cus_id;
        this.proId = proId;
        this.proName = proName;
        this.cateId = cateId;
        this.quantity = quantity;
        this.proPrice = proPrice;
        this.status = status;
        this.proImage = proImage;
        this.billTotal=billTotal;
        this.billTax=billTax;
        this.billService=billService;
        this.billSubTotal = billSubTotal;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Customer{" + "cus_id=" + cus_id + ", proId=" + proId + ", proName=" + proName + ", cateId=" + cateId + ", quantity=" + quantity + ", proPrice=" + proPrice + ", status=" + status + ", proImage=" + proImage + ", billTotal=" + billTotal + ", billTax=" + billTax + ", billService=" + billService + ", billSubTotal=" + billSubTotal + ", userName=" + userName + '}';
    }
    

    public Customer() {
    }

    public Customer(String cus_id, String name, String phone, String email, int discount, int deleted) {
        this.cus_id = cus_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.discount = discount;
        this.deleted = deleted;
    }

    public Vector toVector() {
        Vector v = new Vector();
        v.add(this.cus_id);
        v.add(this.name);
        v.add(this.phone);
        v.add(this.email);
        v.add(discount);
        v.add(deleted);

        return v;
    }

    public Double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(Double billTotal) {
        this.billTotal = billTotal;
    }

    public Double getBillTax() {
        return billTax;
    }

    public void setBillTax(Double billTax) {
        this.billTax = billTax;
    }

    public Double getBillService() {
        return billService;
    }

    public void setBillService(Double billService) {
        this.billService = billService;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getProPrice() {
        return proPrice;
    }

    public void setProPrice(Double proPrice) {
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

    public Double getBillSubTotal() {
        return billSubTotal;
    }

    public void setBillSubTotal(Double billSubTotal) {
        this.billSubTotal = billSubTotal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Customer getCusid() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
