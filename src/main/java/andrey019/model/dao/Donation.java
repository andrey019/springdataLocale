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
    private String ip;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String currency;

    @Column(name = "sender_commission", nullable = false)
    private double senderCommission;

    @Column(name = "receiver_commission", nullable = false)
    private double receiverCommission;

    @Column(name = "agent_commission", nullable = false)
    private double agentCommission;

    @Column(name = "amount_debit", nullable = false)
    private double amountDebit;

    @Column(name = "amount_credit", nullable = false)
    private double amountCredit;

    @Column(name = "commission_debit", nullable = false)
    private double commissionDebit;

    @Column(name = "commission_credit", nullable = false)
    private double commissionCredit;

    @Column(name = "currency_debit", nullable = false)
    private String currencyDebit;

    @Column(name = "currency_credit", nullable = false)
    private String currencyCredit;

    @Column(name = "date", nullable = false)
    private long date;

    @Column(name = "authcode_debit", nullable = false)
    private String authCodeDebit;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private long transactionId;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public double getSenderCommission() {
        return senderCommission;
    }

    public void setSenderCommission(double senderCommission) {
        this.senderCommission = senderCommission;
    }

    public double getReceiverCommission() {
        return receiverCommission;
    }

    public void setReceiverCommission(double receiverCommission) {
        this.receiverCommission = receiverCommission;
    }

    public double getAgentCommission() {
        return agentCommission;
    }

    public void setAgentCommission(double agentCommission) {
        this.agentCommission = agentCommission;
    }

    public double getAmountDebit() {
        return amountDebit;
    }

    public void setAmountDebit(double amountDebit) {
        this.amountDebit = amountDebit;
    }

    public double getAmountCredit() {
        return amountCredit;
    }

    public void setAmountCredit(double amountCredit) {
        this.amountCredit = amountCredit;
    }

    public double getCommissionDebit() {
        return commissionDebit;
    }

    public void setCommissionDebit(double commissionDebit) {
        this.commissionDebit = commissionDebit;
    }

    public double getCommissionCredit() {
        return commissionCredit;
    }

    public void setCommissionCredit(double commissionCredit) {
        this.commissionCredit = commissionCredit;
    }

    public String getCurrencyDebit() {
        return currencyDebit;
    }

    public void setCurrencyDebit(String currencyDebit) {
        this.currencyDebit = currencyDebit;
    }

    public String getCurrencyCredit() {
        return currencyCredit;
    }

    public void setCurrencyCredit(String currencyCredit) {
        this.currencyCredit = currencyCredit;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAuthCodeDebit() {
        return authCodeDebit;
    }

    public void setAuthCodeDebit(String authCodeDebit) {
        this.authCodeDebit = authCodeDebit;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
