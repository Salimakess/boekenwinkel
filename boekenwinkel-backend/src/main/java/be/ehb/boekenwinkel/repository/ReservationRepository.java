
package be.ehb.boekenwinkel.repository;

import be.ehb.boekenwinkel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
