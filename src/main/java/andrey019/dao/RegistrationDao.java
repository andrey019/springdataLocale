package andrey019.dao;

import andrey019.model.dao.UserConfirmation;


public interface RegistrationDao {

    boolean save(UserConfirmation userConfirmation);
    UserConfirmation getByEmail(String email);
    UserConfirmation getByCode(String code);
    boolean deleteByDateOlderThen(long date);
    boolean delete(UserConfirmation userConfirmation);
}
