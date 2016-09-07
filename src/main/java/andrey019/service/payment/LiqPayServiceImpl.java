package andrey019.service.payment;


import andrey019.LiqPay.LiqPayApi;
import andrey019.model.dao.Donation;
import andrey019.model.dao.DonationWait;
import andrey019.model.dao.User;
import andrey019.repository.DonationRepository;
import andrey019.repository.DonationWaitRepository;
import andrey019.repository.UserRepository;
import andrey019.service.maintenance.LogService;
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
        if (amount < 5) {
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
            return "validity check fail";
        }
        JsonNode jsonNode = getJson(data);
        if (jsonNode == null) {
            return "json parse error";
        }
        if (donationRepository.findByOrderId(jsonNode.get("order_id").textValue()) != null) {
            return "already processed: order_id = " + jsonNode.get("order_id").textValue();
        }
        DonationWait donationWait = donationWaitRepository.findByOrderId(jsonNode.get("order_id").textValue());
        if (donationWait == null) {
            return "no initial request: order_id = " + jsonNode.get("order_id").textValue();
        }
        User user = userRepository.findByEmail(donationWait.getUserEmail());
        if (user == null) {
            return "user not found: " + donationWait.getUserEmail() +
                    ", order_id = " + jsonNode.get("order_id").textValue();
        }
        Donation donation = getDonation(jsonNode, user);
        if (donation == null) {
            return "donation parse error: order_id = " + jsonNode.get("order_id").textValue();
        }
        if (donation.getAmount() != donationWait.getAmount()) {
            return "donation amount not equals: initial = " + donationWait.getAmount() +
                    ", final = " + donation.getAmount() + ", " + donationWait.getUserEmail() +
                    ", order_id = " + jsonNode.get("order_id").textValue();
        }
        user.addDonation(donation);
        if (userRepository.save(user) == null) {
            return "donation save error: " + donationWait.getUserEmail() +
                    ", order_id = " + jsonNode.get("order_id").textValue();
        }
        donationWaitRepository.delete(donationWait.getId());
        return "success: " + user.getEmail() + ", order_id = " + donation.getOrderId() +
                ", amount = " + donation.getAmount() + " " + donation.getCurrency();
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

    private Donation getDonation(JsonNode jsonNode, User user) {
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
            donation.setIp(jsonNode.get("ip").textValue());
            donation.setAmount(jsonNode.get("amount").doubleValue());
            donation.setCurrency(jsonNode.get("currency").textValue());
            donation.setSenderCommission(jsonNode.get("sender_commission").doubleValue());
            donation.setReceiverCommission(jsonNode.get("receiver_commission").doubleValue());
            donation.setAgentCommission(jsonNode.get("agent_commission").doubleValue());
            donation.setAmountDebit(jsonNode.get("amount_debit").doubleValue());
            donation.setAmountCredit(jsonNode.get("amount_credit").doubleValue());
            donation.setCommissionDebit(jsonNode.get("commission_debit").doubleValue());
            donation.setCommissionCredit(jsonNode.get("commission_credit").doubleValue());
            donation.setCurrencyDebit(jsonNode.get("currency_debit").textValue());
            donation.setCurrencyCredit(jsonNode.get("currency_credit").textValue());
            donation.setCreateDate(jsonNode.get("create_date").longValue());
            donation.setEndDate(jsonNode.get("end_date").longValue());
            donation.setTransactionId(jsonNode.get("transaction_id").longValue());
            return donation;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
