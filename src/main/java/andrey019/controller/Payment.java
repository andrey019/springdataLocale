package andrey019.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class Payment {

    @RequestMapping(value = "/liqpay", method = RequestMethod.POST)
    public void liqpay(@RequestParam("data") String data, @RequestParam("signature") String signature,
                       @RequestParam("status") String status, @RequestParam("order_id") String orderId,
                       @RequestParam("acq_id") long acqId) {
        System.out.println("data = " + data);
        System.out.println("signature = " + signature);
        System.out.println(status);
        System.out.println(orderId);
        System.out.println(acqId);
        System.out.println();
    }
}
