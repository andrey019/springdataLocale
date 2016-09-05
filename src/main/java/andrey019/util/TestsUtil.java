package andrey019.util;


import andrey019.LiqPay.LiqPay;
import andrey019.LiqPay.LiqPayUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;

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

        HashMap params = new HashMap();
        params.put("action", "pay");
        params.put("amount", "1");
        params.put("currency", "UAH");
        params.put("description", "description text");
        params.put("order_id", "order_id_6");
        params.put("language", "en");
        params.put("server_url", "https://social-andrey019.rhcloud.com/payment/liqpay");
        LiqPay liqpay = new LiqPay(LIQPAY_PUBLIC_KEY, LIQPAY_PRIVATE_KEY);
        liqpay.setCnbSandbox(true);
        System.out.println(liqpay.cnb_form(params));


//        String data = "data = eyJhY3Rpb24iOiJwYXkiLCJwYXltZW50X2lkIjoyMzg0NTE0ODAsInN0YXR1cyI6InNhbmRib" +
//                "3giLCJ2ZXJzaW9uIjozLCJ0eXBlIjoiYnV5IiwicGF5dHlwZSI6ImNhcmQiLCJwdWJsaWNfa2V5Ijoia" +
//                "TMxOTQyMjgwNzczIiwiYWNxX2lkIjo0MTQ5NjMsIm9yZGVyX2lkIjoib3JkZXJfaWRfMiIsImxpcXBhe" +
//                "V9vcmRlcl9pZCI6IkNRQ0tXTFFPMTQ3MzA4NTUxMzUxNDkxNCIsImRlc2NyaXB0aW9uIjoiZGVzY3Jpc" +
//                "HRpb24gdGV4dCIsInNlbmRlcl9jYXJkX21hc2syIjoiNTE2OTMwKjExIiwic2VuZGVyX2NhcmRfYmFua" +
//                "yI6IlBVQkxJQyBKT0lOVC1TVE9DSyBDT01QQU5ZIFwiVUsiLCJzZW5kZXJfY2FyZF90eXBlIjoibWMiL" +
//                "CJzZW5kZXJfY2FyZF9jb3VudHJ5Ijo4MDQsImlwIjoiNDYuMjAwLjEzNC4xMTIiLCJhbW91bnQiOjEuM" +
//                "CwiY3VycmVuY3kiOiJVQUgiLCJzZW5kZXJfY29tbWlzc2lvbiI6MC4wLCJyZWNlaXZlcl9jb21taXNza" +
//                "W9uIjowLjAzLCJhZ2VudF9jb21taXNzaW9uIjowLjAsImFtb3VudF9kZWJpdCI6MS4wLCJhbW91bnRfY" +
//                "3JlZGl0IjoxLjAsImNvbW1pc3Npb25fZGViaXQiOjAuMCwiY29tbWlzc2lvbl9jcmVkaXQiOjAuMDMsI" +
//                "mN1cnJlbmN5X2RlYml0IjoiVUFIIiwiY3VycmVuY3lfY3JlZGl0IjoiVUFIIiwic2VuZGVyX2JvbnVzI" +
//                "jowLjAsImFtb3VudF9ib251cyI6MC4wLCJtcGlfZWNpIjoiNyIsImlzXzNkcyI6ZmFsc2UsImNyZWF0Z" +
//                "V9kYXRlIjoxNDczMDg1NTEzNTY0LCJlbmRfZGF0ZSI6MTQ3MzA4NTUxMzU2NCwidHJhbnNhY3Rpb25fa" +
//                "WQiOjIzODQ1MTQ4MH0=";
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
