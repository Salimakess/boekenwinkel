
package be.ehb.boekenwinkel;

import be.ehb.boekenwinkel.model.Book;
import be.ehb.boekenwinkel.model.Review;
import be.ehb.boekenwinkel.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoekenwinkelBackendApplicationTests {

	// TEST 1 : CREATION D'UN LIVRE
	@Test
	void testBookCreation() {
		Book book = new Book();

		book.setIsbn("9782070360024");
		book.setTitle("Le Petit Prince");
		book.setPrice(new BigDecimal("12.50"));
		book.setStock(10);

		assertEquals("Le Petit Prince", book.getTitle());
		assertEquals("9782070360024", book.getIsbn());
		assertEquals(10, book.getStock());
	}

	// TEST 2 : DIMINUTION DU STOCK
	@Test
	void testStockUpdate() {
		Book book = new Book();
		book.setStock(10);

		book.setStock(book.getStock() - 1);

		assertEquals(9, book.getStock());
	}

	// TEST 3 : NOTE ET COMMENTAIRE
	@Test
	void testReviewCreation() {
		Review review = new Review();

		review.setRating(5);
		review.setComment("Een prachtig boek!");

		assertEquals(5, review.getRating());
		assertEquals("Een prachtig boek!", review.getComment());
	}
}
