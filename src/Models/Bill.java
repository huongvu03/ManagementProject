package Models;

import java.util.Date;

public class Bill {

    private Integer billId;
    private String cus_id;
    private Double billTotal;
    private Double billTax;
    private Double billService;
    private Double billSubTotal;
    private Date billDate;
    private String userName;
    private String proId;
    private String proName;
    private Integer cateId;
    private Integer quantity;
    private Double proPrice;
    private String status;
    private String proImage;
     private String note;

    public Bill(String cus_id, String proId, String proName, Integer quantity, Double proPrice) {
        this.cus_id = cus_id;
        this.proId = proId;
        this.proName = proName;
        this.quantity = quantity;
        this.proPrice = proPrice;
    }

   

    public Bill(Integer billId, String cus_id, Double billTotal, Double billTax, Double billService, Double billSubTotal, Date billDate, String userName, String proId, String proName, Integer cateId, Integer quantity, Double proPrice, String status, String proImage) {
        this.billId = billId;
        this.cus_id = cus_id;
        this.billTotal = billTotal;
        this.billTax = billTax;
        this.billService = billService;
        this.billSubTotal = billSubTotal;
        this.billDate = billDate;
        this.userName = userName;
        this.proId = proId;
        this.proName = proName;
        this.cateId = cateId;
        this.quantity = quantity;
        this.proPrice = proPrice;
        this.status = status;
        this.proImage = proImage;
    }

    @Override
    public String toString() {
        return "Bill{" + "billId=" + billId + ", cus_id=" + cus_id + ", billTotal=" + billTotal + ", billTax=" + billTax + ", billService=" + billService + ", billSubTotal=" + billSubTotal + ", billDate=" + billDate + ", userName=" + userName + ", proId=" + proId + ", proName=" + proName + ", cateId=" + cateId + ", quantity=" + quantity + ", proPrice=" + proPrice + ", status=" + status + ", proImage=" + proImage + ", note=" + note + '}';
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public Bill() {
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCustomerId(String cus_id) {
        this.cus_id = cus_id;
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

    public Double getBillSubTotal() {
        return billSubTotal;
    }

    public void setBillSubTotal(Double billSubTotal) {
        this.billSubTotal = billSubTotal;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


}
