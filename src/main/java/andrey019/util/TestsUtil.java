package andrey019.util;


import andrey019.LiqPay.LiqPay;
import andrey019.LiqPay.LiqPayUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;


public class TestsUtil {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    private final static long TIMEZONE_CORRECTION_MILLISECONDS = 7 * 60 * 60 * 1000;

    private final static String LIQPAY_PUBLIC_KEY = "i31942280773";
    private final static String LIQPAY_PRIVATE_KEY = "2a1NcYfUoz09cuUQPRZikmq5LAQgk7JdA5PDDeNw";

    static {
//        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("Etc/GMT+2"));
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMsdfT+3"));
    }

    public static void main(String[] args) {

        EmailValidator emailValidator = EmailValidator.getInstance();
        System.out.println(emailValidator.isValid("sdfs@asdf"));
        System.out.println(emailValidator.isValid("sdfs@asdf.com"));
        System.out.println(emailValidator.isValid("sdfsasdf.com"));
        System.out.println(emailValidator.isValid("sdfs@as.ua"));

        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.toString());

        System.out.println(RandomStringUtils.random(10, true, true));

        System.out.println(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(System.currentTimeMillis()));

//        Long time = new Long(1477741569385);
        long timeFacebook = Long.decode("1477828913657");
        long timeGoogle = Long.decode("1472655161548");
        System.out.println("facebook = " + (timeFacebook - System.currentTimeMillis()));
        System.out.println("google = " + (timeGoogle - System.currentTimeMillis()));

        System.out.println(DATE_FORMAT.format(new Date()));

//        HashMap params = new HashMap();
//        params.put("action", "pay");
//        params.put("amount", "1");
//        params.put("currency", "UAH");
//        params.put("description", "description text");
//        params.put("order_id", "order_id_7");
//        params.put("language", "en");
//        params.put("server_url", "https://social-andrey019.rhcloud.com/payment/liqpay");
        LiqPay liqpay = new LiqPay(LIQPAY_PUBLIC_KEY, LIQPAY_PRIVATE_KEY);
//        liqpay.setCnbSandbox(true);
//        System.out.println(liqpay.cnb_form(params));


        String data = "eyJhY3Rpb24iOiJwYXkiLCJwYXltZW50X2lkIjoyMzg2MzA4MzAsInN0YXR1cyI6InNhbmRib" +
                "3giLCJ2ZXJzaW9uIjozLCJ0eXBlIjoiYnV5IiwicGF5dHlwZSI6ImNhcmQiLCJwdWJsaWNfa2V5Ijoia" +
                "TMxOTQyMjgwNzczIiwiYWNxX2lkIjo0MTQ5NjMsIm9yZGVyX2lkIjoib3JkZXJfaWRfNyIsImxpcXBhe" +
                "V9vcmRlcl9pZCI6IlNISVJBMTdXMTQ3MzEwMjc5NDAyMjczMCIsImRlc2NyaXB0aW9uIjoiZGVzY3Jpc" +
                "HRpb24gdGV4dCIsInNlbmRlcl9jYXJkX21hc2syIjoiNTE2OTMwKjExIiwic2VuZGVyX2NhcmRfYmFua" +
                "yI6IlBVQkxJQyBKT0lOVC1TVE9DSyBDT01QQU5ZIFwiVUsiLCJzZW5kZXJfY2FyZF90eXBlIjoibWMiL" +
                "CJzZW5kZXJfY2FyZF9jb3VudHJ5Ijo4MDQsImlwIjoiMTA5Ljg2LjU3LjIwIiwiYW1vdW50IjoxLjAsI" +
                "mN1cnJlbmN5IjoiVUFIIiwic2VuZGVyX2NvbW1pc3Npb24iOjAuMCwicmVjZWl2ZXJfY29tbWlzc2lvb" +
                "iI6MC4wMywiYWdlbnRfY29tbWlzc2lvbiI6MC4wLCJhbW91bnRfZGViaXQiOjEuMCwiYW1vdW50X2NyZ" +
                "WRpdCI6MS4wLCJjb21taXNzaW9uX2RlYml0IjowLjAsImNvbW1pc3Npb25fY3JlZGl0IjowLjAzLCJjd" +
                "XJyZW5jeV9kZWJpdCI6IlVBSCIsImN1cnJlbmN5X2NyZWRpdCI6IlVBSCIsInNlbmRlcl9ib251cyI6M" +
                "C4wLCJhbW91bnRfYm9udXMiOjAuMCwibXBpX2VjaSI6IjciLCJpc18zZHMiOmZhbHNlLCJjcmVhdGVfZ" +
                "GF0ZSI6MTQ3MzEwMjc5NDA2OCwiZW5kX2RhdGUiOjE0NzMxMDI3OTQwNjgsInRyYW5zYWN0aW9uX2lkI" +
                "joyMzg2MzA4MzB9";
        String signature = "WlAKg0wvY2JGRlTPnJccNNfzI3w=";


        System.out.println(new String(LiqPayUtil.base64_decode(data)));


        System.out.println(liqpay.checkValidity(data, signature));

        ObjectMapper objectMapper = new ObjectMapper();

        //JsonFactory factory = new JsonFactory();
        try {
            JsonNode jsonNode = objectMapper.readTree(LiqPayUtil.base64_decode(data));
            System.out.println(jsonNode.get("status").textValue());
            //JsonParser jsonParser = factory.createParser(LiqPayUtil.base64_decode(data));
            //System.out.println(jsonParser.getValueAsString("status"));
            //System.out.println(jsonParser.getValue("status"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String signature = "1p9KrdIxZ1bsL3IrSk8InTm3Uoo=";
//        LiqPay liqPay = new LiqPay(LIQPAY_PUBLIC_KEY, LIQPAY_PRIVATE_KEY);
//        String sign = liqPay.str_to_sign(
//                LIQPAY_PRIVATE_KEY +
//                        signature +
//                        LIQPAY_PRIVATE_KEY
//        );
//        System.out.println(sign);
//
//        System.out.println(LiqPayUtil.base64_decode(data));

    }
}
