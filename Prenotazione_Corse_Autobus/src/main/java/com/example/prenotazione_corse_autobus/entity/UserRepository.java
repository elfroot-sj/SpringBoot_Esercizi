package com.example.prenotazione_corse_autobus.entity;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    // findById è già incluso da CrudRepository
    User findByEmail(String email);
}
