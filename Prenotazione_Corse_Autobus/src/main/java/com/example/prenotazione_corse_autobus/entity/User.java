package com.example.prenotazione_corse_autobus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@Table(name = "users") // evito "user" perché parola sensibile in mysql, meglio un nome esplicito
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Email is required!")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Password is required!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; // salva SOLO l'hash a livello service

    //il credito non può essere negativo, ma è opzionale
    @PositiveOrZero(message = "Credit must be >= 0")
    @Column(nullable = false, precision = 12, scale = 2) //valore del credito max 12 cifre: 10 prima e 2 dopo la virgola
    private BigDecimal credit;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_user_roles_user")
    )
    @Column(name = "role", nullable = false, length = 30)
    private List<String> roles;

    /* -------- lifecycle: default & normalizzazione -------- */
    @PrePersist
    void prePersist() {
        // email normalizzata
        if (email != null) email = email.trim().toLowerCase();

        // credit opzionale in input → default 0.00, scala 2
        if (credit == null) credit = BigDecimal.ZERO;
        credit = credit.setScale(2, RoundingMode.HALF_UP);
    }

    @PreUpdate
    void preUpdate() {
        if (credit != null) {
            credit = credit.setScale(2, RoundingMode.HALF_UP);
        }
        if (email != null) email = email.trim().toLowerCase();
    }

    /* -------- getters/setters fluent -------- */

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public User setCredit(BigDecimal credit) {
        this.credit = credit;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public User setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}


