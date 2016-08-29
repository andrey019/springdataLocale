package andrey019.service;


import andrey019.model.UserDetails;
import andrey019.model.dao.User;
import andrey019.service.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

@Service("userSocialDetails")
public class SocialUserDetailsService implements org.springframework.social.security.SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        User user = userService.getByEmail(s);
        System.out.println("User : " + user);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return UserDetails.getBuilder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getfName())
                .lastName(user.getlName())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .build();
    }
}
