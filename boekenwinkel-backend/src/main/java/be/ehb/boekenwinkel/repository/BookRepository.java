
package be.ehb.boekenwinkel.repository;

import be.ehb.boekenwinkel.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
