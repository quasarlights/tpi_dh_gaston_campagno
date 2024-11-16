package com.example.database_service.repository;

import com.example.database_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos adicionales de búsqueda pueden agregarse aquí
    User findByEmail(String email);
}
