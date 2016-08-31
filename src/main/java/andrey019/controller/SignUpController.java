package andrey019.controller;


import andrey019.service.auth.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignUpController {

    @Autowired
    private ProviderSignInUtils signInUtils;

    @Autowired
    private RegistrationService registrationService;

    private final static String RESPONSE_ERROR = "error";


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(WebRequest webRequest, HttpServletRequest request) {
        System.out.println("signup");

        Connection<?> connection = signInUtils.getConnectionFromSession(webRequest);
        UserProfile userProfile = connection.fetchUserProfile();
        System.out.println(userProfile);
        System.out.println(connection.getKey().getProviderId());
        System.out.println(userProfile.getEmail() + " / " + userProfile.getUsername() + " / "
                + userProfile.getId() + " / " + userProfile.getFirstName() + " / " + userProfile.getName());

        if (userProfile.getEmail() == null) {
            request.setAttribute("socialSignUp", RESPONSE_ERROR);
            return "main_page";
        }

        registrationService.socialRegistration(connection.getKey().getProviderId(), userProfile.getEmail(),
                userProfile.getFirstName(), userProfile.getLastName());

        signInUtils.doPostSignUp(userProfile.getEmail(), webRequest);

        return "redirect:/auth/" + connection.getKey().getProviderId();
    }
}
