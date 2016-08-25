package andrey019.service.auth;


import andrey019.model.json.JsonProfile;
import andrey019.model.dao.User;
import andrey019.repository.UserRepository;
import andrey019.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    private final static String OK = "ok";
    private final static String NO_CHANGES = "Nothing to update!";
    private final static String ERROR = "Update error!";

    private final static String MAIL_SUBJECT = "WunderWaffel profile update";
    private final static String MAIL_TEXT_0 = "<html><body>You updated your profile info on WunderWaffel, " +
            "here is you new credentials:<br>";
    private final static String MAIL_TEXT_1 = "</body></html>";
    private final static String NEW_LINE = "<br>";
    private final static String FIRST_NAME = "First name: ";
    private final static String LAST_NAME = "Last name: ";
    private final static String PASSWORD = "Password: ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JsonProfile getProfile(String email) {
        JsonProfile jsonProfile = new JsonProfile();
        jsonProfile.setFromUser(userRepository.findByEmail(email));
        return jsonProfile;
    }

    @Override
    public String updateProfile(String email, JsonProfile jsonProfile) {
        if ( (jsonProfile.getfName() == null) && (jsonProfile.getlName() == null) &&
                (jsonProfile.getPassword() == null) ) {
            return NO_CHANGES;
        }
        User user = userRepository.findByEmail(email);
        String encodedPassword = passCheckAndEncoding(jsonProfile.getPassword());
        String mailText = getMailText(jsonProfile);
        if (encodedPassword != null) {
            jsonProfile.setPassword(encodedPassword);
        }
        user.setFromJsonProfile(jsonProfile);
        if (userRepository.save(user) != null) {
            mailService.sendMail(email, MAIL_SUBJECT, mailText);
            return OK;
        }
        return ERROR;
    }

    private String passCheckAndEncoding(String password) {
        if ( (!password.isEmpty()) && ( (password.length() >= 6) && (password.length() <= 20) ) ) {
            password = passwordEncoder.encode(password);
            return password;
        }
        return null;
    }

    private String getMailText(JsonProfile jsonProfile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MAIL_TEXT_0);
        if (!jsonProfile.getfName().isEmpty()) {
            stringBuilder.append(FIRST_NAME);
            stringBuilder.append(jsonProfile.getfName());
            stringBuilder.append(NEW_LINE);
        }
        if (!jsonProfile.getlName().isEmpty()) {
            stringBuilder.append(LAST_NAME);
            stringBuilder.append(jsonProfile.getlName());
            stringBuilder.append(NEW_LINE);
        }
        if (!jsonProfile.getPassword().isEmpty()) {
            stringBuilder.append(PASSWORD);
            stringBuilder.append(jsonProfile.getPassword());
            stringBuilder.append(NEW_LINE);
        }
        stringBuilder.append(MAIL_TEXT_1);
        return stringBuilder.toString();
    }
}
