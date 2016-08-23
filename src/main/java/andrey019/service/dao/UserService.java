package andrey019.service.dao;

import andrey019.model.dao.User;

public interface UserService {

    User getByEmail(String email);
}