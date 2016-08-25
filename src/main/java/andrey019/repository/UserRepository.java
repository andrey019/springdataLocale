package andrey019.repository;


import andrey019.model.dao.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByEmail(String email);

    List<User> findBySharedTodoLists_Id(long todoListId);
}
