package andrey019.service.payment;


public interface LiqPayService {

    String generateDonation(String userEmail, double amount);

    String donationConfirm(String data, String signature);
}
