
package be.ehb.boekenwinkel.repository;

import be.ehb.boekenwinkel.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
