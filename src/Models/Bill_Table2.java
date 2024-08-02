
package Models;

import java.util.Date;

public class Bill_Table2 {
    private int billId;
    private String proId;
    private String proName;
    private int cateId;
    private int quantity;
    private double proPrice;
    private Date archiveDate;
    private String status;
    private String proImage;
    private Double billTotal;
    private Double billTax;
    private Double billService;
    private Double billDiscount;
    private Double billSubTotal;
    
    private String cus_id;
    private String cus_name;

    public Bill_Table2() {
    }

    public Bill_Table2(String proId, String proName, int cateId, int quantity, double proPrice, String status, String proImage, Double billTotal, Double billTax, Double billService, Double billDiscount, Double billSubTotal, String cus_id, String cus_name) {
        this.proId = proId;
        this.proName = proName;
        this.cateId = cateId;
        this.quantity = quantity;
        this.proPrice = proPrice;
        this.status = status;
        this.proImage = proImage;
        this.billTotal = billTotal;
        this.billTax = billTax;
        this.billService = billService;
        this.billDiscount = billDiscount;
        this.billSubTotal = billSubTotal;
        this.cus_id = cus_id;
        this.cus_name = cus_name;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    @Override
    public String toString() {
        return "Bill_Table2{" + "billId=" + billId + ", proId=" + proId + ", proName=" + proName + ", cateId=" + cateId + ", quantity=" + quantity + ", proPrice=" + proPrice + ", status=" + status + ", proImage=" + proImage + ", billTotal=" + billTotal + ", billTax=" + billTax + ", billService=" + billService + ", billDiscount=" + billDiscount + ", billSubTotal=" + billSubTotal + ", cus_id=" + cus_id + ", cus_name=" + cus_name + '}';
    }

 

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Double getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(Double billDiscount) {
        this.billDiscount = billDiscount;
    }

    public Double getBillSubTotal() {
        return billSubTotal;
    }

    public void setBillSubTotal(Double billSubTotal) {
        this.billSubTotal = billSubTotal;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }
    
}
