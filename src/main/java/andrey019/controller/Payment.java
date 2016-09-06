package andrey019.controller;


import andrey019.service.maintenance.LogService;
import andrey019.service.payment.LiqPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class Payment {

    private final static String FAIL = "failed";

    @Autowired
    private LiqPayService liqPayService;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/liqpay", method = RequestMethod.POST)
    public void liqpay(@RequestParam("data") String data, @RequestParam("signature") String signature) {
        try {
            if (!liqPayService.donationConfirm(data, signature)) {
                logService.donation(FAIL);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
