package andrey019.repository;


import andrey019.model.dao.UserConfirmation;
import org.springframework.transaction.annotation.Transactional;

public interface UserConfirmationRepository extends BaseRepository<UserConfirmation, Long> {

    UserConfirmation findByCode(String code);

    UserConfirmation findByEmail(String email);

    @Transactional
    long deleteByDateLessThan(long date);
}
