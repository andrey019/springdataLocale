package andrey019.LiqPay;


import java.util.Map;

public interface LiqPayApi {
    String API_VERSION = "3";
    String LIQPAY_API_CHECKOUT_URL = "https://www.liqpay.com/api/3/checkout";
    String DEFAULT_LANG = "en";


    String cnb_form(Map<String, String> params);

    boolean checkValidity(String data, String signature);

    String generateDonationForm(String orderId, double amount);

    String base64_decode(String data);
}