package andrey019.controller;


import andrey019.model.json.JsonDonation;
import andrey019.service.maintenance.LogService;
import andrey019.service.payment.LiqPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class Payment {

    @Autowired
    private LiqPayService liqPayService;

    @Autowired
    private LogService logService;


    @RequestMapping(value = "/liqpay", method = RequestMethod.POST)
    public void liqpay(@RequestParam("data") String data, @RequestParam("signature") String signature) {
        logService.donation(liqPayService.donationConfirm(data, signature));
    }

    @RequestMapping(value = "/liqpayRequest", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String liqpayRequest(@RequestBody JsonDonation jsonDonation) {
        logService.donation("request " + getUserEmail() + ", " + jsonDonation.getAmount() + " UAH");
        return liqPayService.generateDonation(getUserEmail(), jsonDonation.getAmount());
    }

    private String getUserEmail(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
