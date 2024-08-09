package Models;

import java.util.Date;

public class Order {

    private int billId;
    private String proId;
    private String proName;
    private int cateId;
    private int quantity;
    private double proPrice;
    private String note;
    private Date archiveDate;
    private double billSubTotal;

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public double getBillSubTotal() {
        return billSubTotal;
    }

    public void setBillSubTotal(double billSubTotal) {
        this.billSubTotal = billSubTotal;
    }

    @Override
    public String toString() {
        return "Order{" + "proId=" + proId + ", proName=" + proName + ", quantity=" + quantity + ", proPrice=" + proPrice + ", note=" + note + '}';
    }

    public Order() {
    }

    public Order(String proId, String proName, int quantity, int cateId, double proPrice, String note) {
        this.proId = proId;
        this.proName = proName;
        this.quantity = quantity;
        this.cateId = cateId;
        this.proPrice = proPrice;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
