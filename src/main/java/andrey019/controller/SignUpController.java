package andrey019.controller;

import andrey019.model.UserDetails;
import andrey019.model.dao.User;
import andrey019.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignUpController {

    private final ProviderSignInUtils signInUtils;

    @Autowired
    public SignUpController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
        signInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @ResponseBody
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String redirectRequestToRegistrationPage(WebRequest webRequest) {
        System.out.println("signup");

        Connection<?> connection = signInUtils.getConnectionFromSession(webRequest);
        UserProfile userProfile = connection.fetchUserProfile();
        System.out.println(userProfile);
        System.out.println(userProfile.getEmail() + " / " + userProfile.getUsername() + " / " + userProfile.getId() + " / " + userProfile.getFirstName() +
        " / " + userProfile.getName());

        User user = new User();
        user.setEmail(userProfile.getEmail());
        user.setfName(userProfile.getFirstName());
        user.setlName(userProfile.getLastName());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setSignInProvider(connection.getKey().getProviderId().toUpperCase());

        user = userRepository.save(user);

        UserDetails userDetails = UserDetails.getBuilder()
                .id(user.getId())
                .firstName(user.getfName())
                .lastName(user.getlName())
                .username(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

            signInUtils.doPostSignUp(userProfile.getEmail(), webRequest);



        //return "redirect:/";
        return "signup";
    }
}
