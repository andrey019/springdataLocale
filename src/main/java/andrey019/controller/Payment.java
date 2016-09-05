package andrey019.controller;


import andrey019.LiqPay.LiqPay;
import andrey019.LiqPay.LiqPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class Payment {

    @Autowired
    private LiqPay liqPay;

    @RequestMapping(value = "/liqpay", method = RequestMethod.POST)
    public void liqpay(@RequestParam("data") String data, @RequestParam("signature") String signature) {
        System.out.println("data = " + data);
        System.out.println("signature = " + signature);
        System.out.println(new String(LiqPayUtil.base64_decode(data)));
        System.out.println(liqPay.checkValidity(data, signature));



        System.out.println();
    }
}
