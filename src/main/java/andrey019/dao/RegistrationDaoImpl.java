package andrey019.dao;


import andrey019.model.dao.UserConfirmation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("registrationDao")
public class RegistrationDaoImpl implements RegistrationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean save(UserConfirmation userConfirmation) {
        try {
            entityManager.merge(userConfirmation);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public UserConfirmation getByEmail(String email) {
        try {
            @SuppressWarnings("unchecked")
            List<UserConfirmation> resultList = entityManager
                    .createQuery("select confirm from UserConfirmation confirm where confirm.email = :email")
                    .setParameter("email", email).setMaxResults(1).getResultList();
            if (resultList.isEmpty()) {
                return null;
            }
            return resultList.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public UserConfirmation getByCode(String code) {
        try {
            @SuppressWarnings("unchecked")
            List<UserConfirmation> resultList = entityManager
                    .createQuery("select confirm from UserConfirmation confirm where confirm.code = :code")
                    .setParameter("code", code).setMaxResults(1).getResultList();
            if (resultList.isEmpty()) {
                return null;
            }
            return resultList.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteByDateOlderThen(long date) {
        try {
            @SuppressWarnings("unchecked")
            List<UserConfirmation> resultList = entityManager
                    .createQuery("select confirm from UserConfirmation confirm where confirm.date < :date")
                    .setParameter("date", date).getResultList();
            for (UserConfirmation userConfirmation : resultList) {
                entityManager.remove(userConfirmation);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(UserConfirmation userConfirmation) {
        try {
            userConfirmation = entityManager.merge(userConfirmation);
            entityManager.remove(userConfirmation);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
