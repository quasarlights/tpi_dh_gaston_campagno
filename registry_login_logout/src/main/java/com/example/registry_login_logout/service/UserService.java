package com.example.registry_login_logout.service;

import com.example.registry_login_logout.model.User;
import com.example.registry_login_logout.repository.UserRepository;
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
    private UserRepository userRepository;

    // Método para registrar un usuario
    public User registerUser(User user) throws IOException {
        System.out.println("USER" + user + "FROM SERVICE");
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El usuario ya está registrado");
        }

        user.setCvu(generateCVU());
        System.out.println("PASANDO A GENERATE ALIAS");
        user.setAlias(generateAlias());
        System.out.println(user);
        // Guardamos el usuario sin la contraseña en la respuesta JSON
        User savedUser = userRepository.save(user);
        savedUser.setPassword(null);
        System.out.println("SAVED USER: " + savedUser);
        return savedUser;

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

