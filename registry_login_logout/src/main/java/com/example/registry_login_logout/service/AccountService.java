package com.example.registry_login_logout.service;

import com.example.registry_login_logout.dto.AccountResponse;
import com.example.registry_login_logout.dto.SaldoRequest;
import com.example.registry_login_logout.model.Account;
import com.example.registry_login_logout.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public boolean existById(Long id){
        return accountRepository.existsById(id);
    }

    @Transactional
    public Optional<Account> findById(Long id){
        return accountRepository.findById(id);
    }

    public Account saveAccount (Account account){
        return accountRepository.save(account);
    }

    public void ingressMoney(Long id, SaldoRequest money){
        Double saldo = money.getSaldo();
        System.out.println("SSSaldo: " + saldo);
        if (saldo <= 0){
            throw new IllegalArgumentException("El saldo debe ser mayor que cero.");
        }
        System.out.println("imprimo id desde pathvariable: " + id);

        Optional optionalAccount = accountRepository.findById(id);
        //Account account = optionalAccount.get();
        System.out.println("OPTIONAL: " + optionalAccount);
        System.out.println("OPTIONAL is present: " + optionalAccount.isPresent());
        //account.setSaldo(account.getSaldo() + saldo);
            //return account;
    }
}
