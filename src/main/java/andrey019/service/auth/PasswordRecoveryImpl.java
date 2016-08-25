package andrey019.service.auth;


import andrey019.model.dao.User;
import andrey019.repository.UserRepository;
import andrey019.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("passwordRecovery")
public class PasswordRecoveryImpl implements PasswordRecovery {

    private final static String MAIL_SUBJECT = "WunderWaffel password recovery";
    private final static String MAIL_TEXT_0 = "<html><body>You were trying to recover your password for " +
            "WunderWaffel.<br><br>New password: <b>";
    private final static String MAIL_TEXT_1 = "</b><br><br>Please, change it as soon as possible. " +
            "You can do it in your profile menu.";
    private final static String NOT_VALID = "Email is not valid!";
    private final static String NOT_FOUND = "Email is not found!";
    private final static String ERROR = "Internal error!";
    private final static String OK = "ok";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String generateNewPassword(String email) {
        if (!isEmailValid(email)) {
            return NOT_VALID;
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return NOT_FOUND;
        }
        String password = RandomStringUtils.random(10, true, true);
        user.setPassword(passwordEncoder.encode(password));
        if (userRepository.save(user) != null) {
            mailService.sendMail(email, MAIL_SUBJECT, getMailText(password));
            return OK;
        }
        return ERROR;
    }

    private boolean isEmailValid(String email) {
        return emailValidator.isValid(email);
    }

    private String getMailText(String password) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MAIL_TEXT_0);
        stringBuilder.append(password);
        stringBuilder.append(MAIL_TEXT_1);
        return stringBuilder.toString();
    }
}
