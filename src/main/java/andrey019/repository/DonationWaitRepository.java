package andrey019.repository;


import andrey019.model.dao.DonationWait;
import org.springframework.transaction.annotation.Transactional;

public interface DonationWaitRepository extends BaseRepository<DonationWait, Long> {

    DonationWait findByOrderId(String orderId);

    @Transactional
    long deleteByCreatedLessThan(long date);
}
