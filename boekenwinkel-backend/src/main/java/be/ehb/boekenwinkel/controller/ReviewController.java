
package be.ehb.boekenwinkel.controller;

import be.ehb.boekenwinkel.model.Book;
import be.ehb.boekenwinkel.model.Review;
import be.ehb.boekenwinkel.model.User;
import be.ehb.boekenwinkel.repository.BookRepository;
import be.ehb.boekenwinkel.repository.ReviewRepository;
import be.ehb.boekenwinkel.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewController(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            BookRepository bookRepository) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    // AJOUTER UN AVIS
    @PostMapping
    public ResponseEntity<?> addReview(
            @RequestBody Map<String, String> request) {

        String userIdText = request.get("userId");
        String isbn = request.get("isbn");
        String ratingText = request.get("rating");
        String comment = request.get("comment");

        if (userIdText == null || isbn == null ||
                ratingText == null || comment == null ||
                comment.isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Vul alle velden in."));
        }

        Long userId;
        int rating;

        try {
            userId = Long.parseLong(userIdText);
            rating = Integer.parseInt(ratingText);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Ongeldige gegevens."));
        }

        if (rating < 1 || rating > 5) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message", "Score moet tussen 1 en 5 liggen."
                    ));
        }

        if (comment.length() > 2000) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Reactie is te lang."));
        }

        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(isbn).orElse(null);

        if (user == null || book == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message", "Gebruiker of boek niet gevonden."
                    ));
        }

        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDateTime.now());

        Review saved = reviewRepository.save(review);

        return ResponseEntity.ok(Map.of(
                "message", "Review toegevoegd!",
                "reviewId", saved.getId(),
                "bookTitle", book.getTitle(),
                "rating", saved.getRating()
        ));
    }

    // CONSULTER LES AVIS D'UN LIVRE
    @GetMapping("/book/{isbn}")
    public ResponseEntity<?> getBookReviews(
            @PathVariable String isbn) {

        if (!bookRepository.existsById(isbn)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Boek niet gevonden."));
        }

        List<Map<String, Object>> reviews =
                reviewRepository.findAll()
                        .stream()
                        .filter(r -> r.getBook().getIsbn().equals(isbn))
                        .map(r -> Map.<String, Object>of(
                                "reviewId", r.getId(),
                                "userName", r.getUser().getName(),
                                "rating", r.getRating(),
                                "comment", r.getComment(),
                                "reviewDate", r.getReviewDate()
                        ))
                        .toList();

        return ResponseEntity.ok(reviews);
    }
}
