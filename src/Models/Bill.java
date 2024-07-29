package Models;

import java.util.Date;

public class Bill {

    private Integer billId;
    private String tableNo;
    private String cus_id;
    private String userName;
    private String proId;
    private String proName;
    private int cateId;
    private int quantity;
    private double proPrice;
    private String status;
    private String proImage;
    private Double billTotal;
    private Double billTax;
    private Double billService;
    private Double billDiscount;
    private Double billSubTotal;
    private Date billDate;
    private String billStatus;
    private int guestNo;
    private Customer customer;
    private Product product;

    public Bill() {
        this.customer = new Customer();
        this.product = new Product();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Bill{" + "billId=" + billId + ", tableNo=" + tableNo + ", cus_id=" + cus_id + ", userName=" + userName + ", proId=" + proId + ", proName=" + proName + ", cateId=" + cateId + ", quantity=" + quantity + ", proPrice=" + proPrice + ", status=" + status + ", proImage=" + proImage + ", billTotal=" + billTotal + ", billTax=" + billTax + ", billService=" + billService + ", billSubTotal=" + billSubTotal + ", billDate=" + billDate + ", billStatus=" + billStatus + '}';
    }

    public Bill(Integer billId, String tableNo, int guestNo, String cus_id, String userName,
            Double billTotal, Double billTax, Double billService, Double billDiscount, Double billSubTotal, Date billDate, String billStatus) {
        this.billId = billId;
        this.tableNo = tableNo;
        this.guestNo = guestNo;
        this.cus_id = cus_id;
        this.userName = userName;
        this.billTotal = billTotal;
        this.billTax = billTax;
        this.billService = billService;
        this.billDiscount = billDiscount;
        this.billSubTotal = billSubTotal;
        this.billDate = billDate;
        this.billStatus = billStatus;
    }

    public int getGuestNo() {
        return guestNo;
    }

    public void setGuestNo(int guestNo) {
        this.guestNo = guestNo;
    }

    public Double getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(Double billDiscount) {
        this.billDiscount = billDiscount;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
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

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

}
