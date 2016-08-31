package andrey019.service;


import andrey019.model.dao.User;
import andrey019.service.dao.UserService;
import andrey019.service.maintenance.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userSocialDetails")
public class SocialUserDetailsService implements org.springframework.social.security.SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        User user = userService.getByEmail(s);
        if (user == null) {
            logService.signIn("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        logService.signIn(user.toString());
        return new SocialUser(user.getEmail(), user.getPassword(),
                user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}
