package andrey019.service.payment;


import andrey019.LiqPay.LiqPayApi;
import andrey019.model.dao.Donation;
import andrey019.model.dao.DonationWait;
import andrey019.model.dao.User;
import andrey019.repository.DonationRepository;
import andrey019.repository.DonationWaitRepository;
import andrey019.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service("liqPayService")
public class LiqPayServiceImpl implements LiqPayService {

    private final static String ERROR = "error";

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonationWaitRepository donationWaitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LiqPayApi liqPayApi;


    @Override
    public String generateDonation(String userEmail, double amount) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            return ERROR;
        }
        amount = ((double) Math.round(amount * 100)) / 100;
        if (amount < 1) {
            return ERROR;
        }
        String orderId = generateOrderId();
        DonationWait donationWait = new DonationWait();
        donationWait.setUserEmail(userEmail);
        donationWait.setAmount(amount);
        donationWait.setOrderId(orderId);
        donationWait.setCreated(System.currentTimeMillis());
        if (donationWaitRepository.save(donationWait) == null) {
            return ERROR;
        }
        return liqPayApi.generateDonationForm(orderId, amount);
    }

    private String generateOrderId() {
        String orderId = null;
        while (orderId == null) {
            orderId = RandomStringUtils.random(10, true, true);
            if ( (donationWaitRepository.findByOrderId(orderId) != null) ||
                    (donationRepository.findByOrderId(orderId) != null) ) {
                orderId = null;
            }
        }
        return orderId;
    }

    @Transactional
    @Override
    public String donationConfirm(String data, String signature) {
        if (!liqPayApi.checkValidity(data, signature)) {
            return "validity check fail\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        JsonNode jsonNode = getJson(data);
        if (jsonNode == null) {
            return "json parse error\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        if (donationRepository.findByOrderId(jsonNode.get("order_id").textValue()) != null) {
            return "already processed\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        DonationWait donationWait = donationWaitRepository.findByOrderId(jsonNode.get("order_id").textValue());
        if (donationWait == null) {
            return "no initial request\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        User user = userRepository.findByEmail(donationWait.getUserEmail());
        if (user == null) {
            return "user not found: " + donationWait.getUserEmail() +
                    "\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        Donation donation = getDonation(jsonNode, user, data, signature);
        if (donation == null) {
            return "donation parse error: " + donationWait.getUserEmail() +
                    "\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        if (!donation.getStatus().equalsIgnoreCase("success") && !donation.getStatus().equalsIgnoreCase("sandbox")) {
            return "donation status is not \"success\": " + donationWait.getUserEmail() +
                    "\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        if (donation.getAmount() != donationWait.getAmount()) {
            return "donation amount not equals: initial = " + donationWait.getAmount() +
                    ", final = " + donation.getAmount() + ", " + donationWait.getUserEmail() +
                    "\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        user.addDonation(donation);
        if (userRepository.save(user) == null) {
            return "donation save error: " + donationWait.getUserEmail() +
                    "\r\ndata = " + liqPayApi.base64_decode(data) +
                    "\r\nsignature = " + liqPayApi.base64_decode(signature);
        }
        donationWaitRepository.delete(donationWait.getId());
        return "success: " + donationWait.getUserEmail() +
                "\r\ndata = " + liqPayApi.base64_decode(data) +
                "\r\nsignature = " + liqPayApi.base64_decode(signature);
    }

    private JsonNode getJson(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(liqPayApi.base64_decode(data));
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Donation getDonation(JsonNode jsonNode, User user, String data, String signature) {
        try {
            Donation donation = new Donation();
            donation.setUser(user);
            donation.setPaymentId(jsonNode.get("payment_id").longValue());
            donation.setStatus(jsonNode.get("status").textValue());
            donation.setType(jsonNode.get("type").textValue());
            donation.setPaytype(jsonNode.get("paytype").textValue());
            donation.setPublicKey(jsonNode.get("public_key").textValue());
            donation.setAcqId(jsonNode.get("acq_id").longValue());
            donation.setOrderId(jsonNode.get("order_id").textValue());
            donation.setLiqpayOrderId(jsonNode.get("liqpay_order_id").textValue());
            donation.setAmount(jsonNode.get("amount").doubleValue());
            donation.setCurrency(jsonNode.get("currency").textValue());
            donation.setDate(System.currentTimeMillis());
            donation.setTransactionId(jsonNode.get("transaction_id").longValue());
            donation.setData(data);
            donation.setSignature(signature);
            return donation;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
