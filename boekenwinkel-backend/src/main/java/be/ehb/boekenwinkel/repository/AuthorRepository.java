
package be.ehb.boekenwinkel.repository;

import be.ehb.boekenwinkel.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
