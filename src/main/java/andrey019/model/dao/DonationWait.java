package andrey019.model.dao;

import javax.persistence.*;

@Entity
@Table(name = "donation_wait")
public class DonationWait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private long created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
