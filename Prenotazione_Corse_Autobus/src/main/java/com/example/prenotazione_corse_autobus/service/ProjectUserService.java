package com.example.prenotazione_corse_autobus.service;

import com.example.prenotazione_corse_autobus.entity.User;
import com.example.prenotazione_corse_autobus.entity.UserRepository;
import com.example.prenotazione_corse_autobus.security.AuthResponse;
import com.example.prenotazione_corse_autobus.security.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class ProjectUserService {

    private static final Logger log = LoggerFactory.getLogger(ProjectUserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.secret}")
    private String jwtSecret;

    /* ----------------- CREATE / REGISTER ----------------- */
    public User addUser(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
        }

        // normalizza email
        String email = user.getEmail().trim().toLowerCase();
        user.setEmail(email);

        // email unica
        if (repository.findByEmail(email) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        // ruolo di default
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singletonList("USER"));
        }

        // hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // credit opzionale: default 0.00; se presente, >= 0 e scala 2
        if (user.getCredit() == null) {
            user.setCredit(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        } else {
            if (user.getCredit().signum() < 0) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Credit must be >= 0");
            }
            user.setCredit(user.getCredit().setScale(2, RoundingMode.HALF_UP));
        }

        User saved = repository.save(user);
        log.info("Registered user id={} email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    /* ----------------- READ ----------------- */
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email == null ? null : email.trim().toLowerCase()));
    }

    public User getUserById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /* ----------------- AUTH: LOGIN (JSON) ----------------- */
    public AuthResponse login(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            log.warn("Login attempt missing email or password");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
        }

        String email = user.getEmail().trim().toLowerCase();
        log.info("Login attempt for {}", email);

        User u = repository.findByEmail(email);

        if (u == null) {
            log.warn("User not found: {}", email);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        boolean passwordOk = passwordEncoder.matches(user.getPassword(), u.getPassword());
        log.info("Password matches? {}", passwordOk);

        if (!passwordOk) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        AuthResponse authResponse = new AuthResponse(
                jwtUtils.generateJwtToken(u.getEmail()),
                jwtUtils.generateRefreshToken(u.getEmail())
        );
        log.info("Login successful for {}", email);
        return authResponse;
    }

    /* ----------------- AUTH: REFRESH by refreshToken header ----------------- */
    public AuthResponse reAuth(String refreshToken) {
        var claims = jwtUtils.decodeRefreshToken(refreshToken);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }

        String email = claims.getSubject();
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found for refresh");
        }

        return new AuthResponse(
                jwtUtils.generateJwtToken(user.getEmail()),
                jwtUtils.generateRefreshToken(user.getEmail())
        );
    }


    /* ----------------- (facoltativo) AUTH: refresh con email+pwd ----------------- */
    public AuthResponse refresh(User user) {
        String email = user.getEmail() == null ? null : user.getEmail().trim().toLowerCase();
        User u = repository.findByEmail(email);
        if (u != null && passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            return new AuthResponse(
                    jwtUtils.generateJwtToken(u.getEmail()),
                    jwtUtils.generateRefreshToken(u.getEmail())
            );
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }


    /* ----------------- Ricarica credito ----------------- */
    public User topupCredit(String email, BigDecimal amount) {
        User u = repository.findByEmail(email);
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be > 0");
        }

        u.setCredit(u.getCredit().add(amount));
        return repository.save(u);
    }


}
