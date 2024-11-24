package com.example.registry_login_logout.repository;

import com.example.registry_login_logout.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Puedes agregar consultas personalizadas aquí si las necesitas
}
