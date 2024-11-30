package com.example.registry_login_logout.controller;

import com.example.registry_login_logout.dto.SaldoRequest;
import com.example.registry_login_logout.exception.ResourceNotFoundException;
import com.example.registry_login_logout.model.Account;
import com.example.registry_login_logout.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/consultar/{id}")
    public Account getAccountById(@PathVariable Long id){
        Optional<Account> optionalAccount = accountService.findById(id);
        Account account = optionalAccount.get();
        account.setSaldo(account.getSaldo() + 10.00);
        //System.out.println(account);

        return account;
    }

    @GetMapping("/exist/{id}")
    public boolean existAccountById(@PathVariable Long id) {
        return accountService.existById(id);
    }

    @GetMapping ("/{id}")
    public Account ingressMoney(@PathVariable Long id) {
        Optional<Account> optionalAccount = accountService.findById(id);
        Account account = optionalAccount.get();
        return account;

    }


    @PutMapping("/{id}/transferences")
    public ResponseEntity<Account> updateAccountPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        // Buscar la cuenta existente
        Account account = accountService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con id: " + id));

        // Actualizar los campos que están en el mapa
        if (updates.containsKey("alias")) {
            account.setAlias((String) updates.get("alias"));
        }
        if (updates.containsKey("cvu")) {
            account.setCvu((String) updates.get("cvu"));
        }
        if (updates.containsKey("saldo")) {
            account.setSaldo((Double) updates.get("saldo"));
        }

        // Guardar los cambios
        accountService.saveAccount(account);

        return ResponseEntity.ok(account);
    }
}
