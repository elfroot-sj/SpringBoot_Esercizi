package com.example.prenotazione_corse_autobus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users") // evito "user" perché parola sensibile in mysql, meglio un nome esplicito
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Email is required!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Password is required!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; // salva SOLO l'hash a livello service

    //il credito non può essere negativo
    @DecimalMin(value = "0.0", inclusive = true, message = "Credit must not be negative!")
    @NotNull(message = "Credit is required!")
    private BigDecimal credit;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

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


