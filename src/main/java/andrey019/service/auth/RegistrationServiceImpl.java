package andrey019.service.auth;


import andrey019.dao.RegistrationDao;
import andrey019.dao.TodoListDao;
import andrey019.dao.UserDao;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
import andrey019.model.dao.UserConfirmation;
import andrey019.service.MailService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private final static String MAIL_SUBJECT = "WunderWaffel registration confirmation";
    private final static String MAIL_TEXT_0 = "<html><body>You were trying to register an account on WunderWaffel, " +
            "to confirm please click on the link below...<br>" +
            "<a href=\"http://wunderwaffel-andrey019.rhcloud.com/auth/confirm?code=";
    private final static String MAIL_TEXT_1 = "\">Click here to confirm registration</a><br><br>" +
            "If you don't know what's happening, just ignore this message.</body></html>";

    private final static String EMAIL_INCORRECT = "Email is incorrect!";
    private final static String EMAIL_IN_USE = "Email is already in use!";
    private final static String OK = "ok";
    private final static String ERROR = "Registration error!";
    private final static String FIRST_LIST = "my first todo list";

    @Autowired
    private RegistrationDao registrationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private EmailValidator emailValidator;


    @Override
    public String preRegistrationCheck(String email) {
        if (!isEmailCorrect(email)) {
            return EMAIL_INCORRECT;
        }
        if (isEmailWaiting(email) || isEmailUsed(email)) {
            return EMAIL_IN_USE;
        }
        return null;
    }

    @Override
    public String registration(String email, String password, String fName, String lName) {
        String check = preRegistrationCheck(email);
        if (check != null) {
            return check;
        }
        if ( fName.isEmpty() || lName.isEmpty() || (password.length() < 6) || (password.length() > 20) ) {
            return ERROR;
        }
        UserConfirmation userConfirmation = new UserConfirmation();
        userConfirmation.setEmail(email);
        userConfirmation.setfName(fName);
        userConfirmation.setlName(lName);
        userConfirmation.setPassword(passwordEncoder.encode(password));
        userConfirmation.setCode(passwordEncoder.encode(email + System.currentTimeMillis()));
        userConfirmation.setDate(System.currentTimeMillis());
        if (registrationDao.save(userConfirmation)) {
            mailService.sendMail(email, MAIL_SUBJECT, getMailText(userConfirmation.getCode()));
            return OK;
        }
        return ERROR;
    }

    @Override
    public boolean confirmRegistration(String code) {
        UserConfirmation userConfirmation = registrationDao.getByCode(code);
        if (userConfirmation == null) {
            return false;
        }
        User user = new User();
        user.setUserFromConfirmation(userConfirmation);
        if (!userDao.save(user)) {
            return false;
        }
        user = userDao.getByEmailWitnListsAndSharedLists(user.getEmail());
        if (user == null) {
            return false;
        }
        TodoList todoList = new TodoList();
        todoList.setName(FIRST_LIST);
        user.addTodoList(todoList);
        user.addSharedTodoList(todoList);
        if (userDao.save(user)) {
            registrationDao.delete(userConfirmation);
            return true;
        }
        return false;
    }

    private boolean isEmailCorrect(String email) {
        return emailValidator.isValid(email);
    }

    private boolean isEmailWaiting(String email) {
        if (registrationDao.getByEmail(email) == null) {
            return false;
        }
        return true;
    }

    private boolean isEmailUsed(String email) {
        if (userDao.getByEmail(email) == null) {
            return false;
        }
        return true;
    }

    private String getMailText(String code) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MAIL_TEXT_0);
        stringBuilder.append(code);
        stringBuilder.append(MAIL_TEXT_1);
        return stringBuilder.toString();
    }
}
