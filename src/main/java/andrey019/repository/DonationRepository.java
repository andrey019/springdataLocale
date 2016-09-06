package andrey019.repository;


import andrey019.model.dao.Donation;

public interface DonationRepository extends BaseRepository<Donation, Long> {

    Donation findByOrderId(String orderId);
}
