
package be.ehb.boekenwinkel.controller;

import be.ehb.boekenwinkel.model.User;
import be.ehb.boekenwinkel.model.Purchase;
import be.ehb.boekenwinkel.repository.UserRepository;
import be.ehb.boekenwinkel.repository.PurchaseRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserController(
            UserRepository userRepository,
            PurchaseRepository purchaseRepository) {

        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    // INSCRIPTION
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (user.getName() == null ||
                user.getName().isBlank() ||
                user.getEmail() == null ||
                user.getEmail().isBlank() ||
                user.getPassword() == null ||
                user.getPassword().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Vul alle velden in."));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "E-mailadres bestaat al."));
        }

        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Registratie geslaagd!",
                        "userId", savedUser.getId(),
                        "name", savedUser.getName()
                ));
    }

    // CONNEXION
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> gegevens) {

        String email = gegevens.get("email");
        String password = gegevens.get("password");

        if (email == null || email.isBlank() ||
                password == null || password.isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message", "Vul e-mail en wachtwoord in."
                    ));
        }

        Optional<User> gevondenUser =
                userRepository.findByEmail(email);

        if (gevondenUser.isEmpty() ||
                !passwordEncoder.matches(
                        password,
                        gevondenUser.get().getPassword())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message",
                            "Ongeldig e-mailadres of wachtwoord."
                    ));
        }

        User user = gevondenUser.get();

        return ResponseEntity.ok(Map.of(
                "message", "Inloggen geslaagd!",
                "userId", user.getId(),
                "name", user.getName()
        ));
    }

    // HISTORIQUE DES ACHATS
    @GetMapping("/{id}/purchases")
    public ResponseEntity<?> getPurchases(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message", "Gebruiker niet gevonden."
                    ));
        }

        List<Map<String, Object>> purchases =
                purchaseRepository.findAll()
                        .stream()
                        .filter(p -> p.getUser().getId().equals(id))
                        .map(p -> Map.<String, Object>of(
                                "purchaseId", p.getId(),
                                "bookTitle", p.getBook().getTitle(),
                                "isbn", p.getBook().getIsbn(),
                                "price", p.getBook().getPrice(),
                                "purchaseDate", p.getPurchaseDate()
                        ))
                        .toList();

        return ResponseEntity.ok(Map.of(
                "userId", user.getId(),
                "name", user.getName(),
                "purchases", purchases
        ));
    }
}
