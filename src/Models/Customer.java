package Models;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String cus_id;
    private String name;
    private String phone;
    private String email;
    private int discount;
    private int deleted;
    private String membershipType;
    private int billId;
    private Double billSubTotal;
    private Date billDate;
    private String billStatus;
    private double totalSpending;

    
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
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

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
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
}
