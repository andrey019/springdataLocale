package andrey019.controller;

import andrey019.model.json.JsonEmail;
import andrey019.model.json.JsonRegistration;
import andrey019.service.auth.PasswordRecovery;
import andrey019.service.maintenance.LogService;
import andrey019.service.auth.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth/")
public class AuthController {

    private final static String RESPONSE_OK = "ok";
    private final static String RESPONSE_ERROR = "error";
    private final static String TEXT_UTF8 = "text/plain;charset=UTF-8";

    @Autowired
    private LogService logService;

    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PasswordRecovery passwordRecovery;


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        logService.accessToPage("logout");
        return "redirect:/";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getUserEmail());
        logService.accessToPage("access_denied");
        return "access_denied";
    }

    @RequestMapping(value = "/emailCheck", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String emailCheck(@RequestBody JsonEmail jsonEmail) {
        logService.ajaxJson("emailCheck " + jsonEmail.getEmail());
        return registrationService.preRegistrationCheck(jsonEmail.getEmail());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String registration(@RequestBody JsonRegistration jsonRegistration) {
        logService.ajaxJson("registration " + jsonRegistration.getEmail());
        return registrationService.registration(jsonRegistration.getEmail(), jsonRegistration.getPassword(),
                jsonRegistration.getfName(), jsonRegistration.getlName());
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmation(@RequestParam("code") String code, HttpServletRequest request) {
        if (code == null) {
            logService.accessToPage("confirm, code == null");
            request.setAttribute("confirm", RESPONSE_ERROR);
            return "main_page";
        }
        if (registrationService.confirmRegistration(code)) {
            logService.accessToPage("confirm, ok");
            request.setAttribute("confirm", RESPONSE_OK);
            return "main_page";
        } else {
            logService.accessToPage("confirm, error");
            request.setAttribute("confirm", RESPONSE_ERROR);
            return "main_page";
        }
    }

    @RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String passwordRecovery(@RequestBody JsonEmail jsonEmail) {
        logService.ajaxJson("passwordRecovery " + jsonEmail.getEmail());
        return passwordRecovery.generateNewPassword(jsonEmail.getEmail());
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