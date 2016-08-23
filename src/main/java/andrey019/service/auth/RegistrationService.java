package andrey019.service.auth;


public interface RegistrationService {

    String preRegistrationCheck(String email);
    String registration(String email, String password, String fName, String lName);
    boolean confirmRegistration(String code);
}
