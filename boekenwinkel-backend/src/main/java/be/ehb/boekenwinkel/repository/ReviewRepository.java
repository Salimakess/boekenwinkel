
package be.ehb.boekenwinkel.repository;

import be.ehb.boekenwinkel.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
