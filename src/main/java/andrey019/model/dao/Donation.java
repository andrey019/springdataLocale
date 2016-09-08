package andrey019.model.dao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "payment_id", unique = true, nullable = false)
    private long paymentId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String paytype;

    @Column(name = "public_key", nullable = false)
    private String publicKey;

    @Column(name = "acq_id", nullable = false)
    private long acqId;

    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    @Column(name = "liqpay_order_id", unique = true, nullable = false)
    private String liqpayOrderId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String currency;

    @Column(name = "date", nullable = false)
    private long date;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private long transactionId;

    @Lob
    @Column(nullable = false)
    private String data;

    @Column(nullable = false)
    private String signature;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public long getAcqId() {
        return acqId;
    }

    public void setAcqId(long acqId) {
        this.acqId = acqId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLiqpayOrderId() {
        return liqpayOrderId;
    }

    public void setLiqpayOrderId(String liqpayOrderId) {
        this.liqpayOrderId = liqpayOrderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
