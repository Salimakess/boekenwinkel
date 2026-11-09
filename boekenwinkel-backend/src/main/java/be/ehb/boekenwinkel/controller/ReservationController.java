
package be.ehb.boekenwinkel.controller;

import be.ehb.boekenwinkel.model.Book;
import be.ehb.boekenwinkel.model.User;
import be.ehb.boekenwinkel.model.Reservation;
import be.ehb.boekenwinkel.model.Purchase;

import be.ehb.boekenwinkel.repository.BookRepository;
import be.ehb.boekenwinkel.repository.UserRepository;
import be.ehb.boekenwinkel.repository.ReservationRepository;
import be.ehb.boekenwinkel.repository.PurchaseRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PurchaseRepository purchaseRepository;

    public ReservationController(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            PurchaseRepository purchaseRepository) {

        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.purchaseRepository = purchaseRepository;
    }

    // 1. RESERVER UN LIVRE
    @PostMapping
    public ResponseEntity<?> reserveBook(
            @RequestBody Map<String, String> request) {

        String isbn = request.get("isbn");
        String userIdText = request.get("userId");

        if (isbn == null || userIdText == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Vul userId en isbn in."));
        }

        Long userId;

        try {
            userId = Long.parseLong(userIdText);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Ongeldige userId."));
        }

        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(isbn).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Gebruiker niet gevonden."));
        }

        if (book == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Boek niet gevonden."));
        }

        if (book.getStock() <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Boek niet op voorraad."));
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus("GERESERVEERD");

        Reservation saved = reservationRepository.save(reservation);

        return ResponseEntity.ok(Map.of(
                "message", "Reservatie geslaagd!",
                "reservationId", saved.getId(),
                "bookTitle", book.getTitle(),
                "status", saved.getStatus()
        ));
    }

    // 2. CONFIRMER LE RETRAIT ET LE PAIEMENT
    @Transactional
    @PostMapping("/{id}/confirm")
    public ResponseEntity<?> confirmReservation(
            @PathVariable Long id) {

        Reservation reservation =
                reservationRepository.findById(id).orElse(null);

        if (reservation == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Reservatie niet gevonden."));
        }

        if (!"GERESERVEERD".equals(reservation.getStatus())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Reservatie is al verwerkt."));
        }

        Book book = reservation.getBook();

        if (book.getStock() <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Boek niet op voorraad."));
        }

        // Diminuer le stock
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        // Enregistrer l'achat
        Purchase purchase = new Purchase();
        purchase.setUser(reservation.getUser());
        purchase.setBook(book);
        purchase.setPurchaseDate(LocalDateTime.now());

        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Confirmer la réservation
        reservation.setStatus("BEVESTIGD");
        reservationRepository.save(reservation);

        return ResponseEntity.ok(Map.of(
                "message", "Betaling en afhaling bevestigd!",
                "reservationId", reservation.getId(),
                "purchaseId", savedPurchase.getId(),
                "bookTitle", book.getTitle(),
                "remainingStock", book.getStock(),
                "status", reservation.getStatus()
        ));
    }
}
