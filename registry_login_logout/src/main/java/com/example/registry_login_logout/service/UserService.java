package com.example.registry_login_logout.service;

import com.example.registry_login_logout.dto.KeycloakUserRequest;
import com.example.registry_login_logout.feign.KeycloakServiceClient;
import com.example.registry_login_logout.model.Account;
import com.example.registry_login_logout.model.Users;
import com.example.registry_login_logout.repository.AccountRepository;
import com.example.registry_login_logout.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    KeycloakServiceClient keycloakServiceClient;

    // Método para registrar un usuario


    public Users registerUser(Users users) throws IOException {
        // Validar si el email ya está registrado
        if (userRepository.existsByEmail(users.getEmail())) {
            throw new IllegalArgumentException("El usuario ya está registrado");
        }

        // Crear la cuenta asociada
        Account account = createAccount();
        System.out.println("ACCOUNT CREATION: " + account);

        users.setAccount(account);
        users= userRepository.save(users);
        System.out.println("Persisted User: " +
users);

        // Extraer valores de nyap
        String[] nyapParts = users.getNyap().split(" ", 2);
        String firstName = nyapParts[0];
        String lastName = nyapParts.length > 1 ? nyapParts[1] : "";

        // Crear KeycloakUserRequest
        KeycloakUserRequest keycloakUserRequest = new KeycloakUserRequest(
                users.getEmail(),         // username
                users.getPassword(),      // password
                users.getEmail(),         // email
                firstName,               // firstName
                lastName                 // lastName
        );

        try {
            keycloakServiceClient.createUserInKeycloak(keycloakUserRequest);
        } catch (Exception e) {
            // Maneja el error según sea necesario
            throw new RuntimeException("Error al registrar usuario en Keycloak", e);
        }

        // Establecer la relación bidireccional
        return users;
    }


    private Account createAccount() throws IOException {
        Account account = new Account();
        account.setCvu(generateCVU());
        account.setAlias(generateAlias());

        // Guardar la cuenta en la base de datos
        return accountRepository.save(account);
    }



    private String generateCVU() {
        Random random = new Random();
        StringBuilder cvu = new StringBuilder();
        for (int i = 0; i < 22; i++) {
            cvu.append(random.nextInt(10));
        }
        System.out.println("CVU::" + cvu);
        return cvu.toString();
    }

    public String generateAlias() throws IOException {
        // Ruta del archivo
        Path path = Paths.get("C:\\Users\\gasto\\Desktop\\tpi_dh_gaston_campagno\\registry_login_logout\\src\\main\\java\\com\\example\\registry_login_logout\\service\\aliases.txt");

        // Leer las palabras desde el archivo, eliminar espacios y saltos de línea innecesarios
        List<String> words = Files.readAllLines(path)
                .stream()
                .map(String::trim) // Eliminar espacios antes y después
                .filter(word -> !word.isEmpty()) // Eliminar palabras vacías
                .collect(Collectors.toList());

        // Verificar si la lista está vacía
        if (words.isEmpty()) {
            throw new IllegalArgumentException("La lista de palabras está vacía.");
        }

        // Imprimir las palabras leídas para verificar
        System.out.println("WORDS::" + words);

        // Generar el alias utilizando ThreadLocalRandom para una mejor eficiencia
        String alias = words.get(ThreadLocalRandom.current().nextInt(words.size())) + "." +
                words.get(ThreadLocalRandom.current().nextInt(words.size())) + "." +
                words.get(ThreadLocalRandom.current().nextInt(words.size()));

        // Imprimir el alias generado para verificar
        System.out.println("Generated Alias: " + alias);

        return alias;
    }


}

