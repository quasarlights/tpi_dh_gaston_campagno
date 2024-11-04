package com.example.registry_login_logout.service;

import com.example.registry_login_logout.model.User;
import com.example.registry_login_logout.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

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

        // Guardamos el usuario sin la contraseña en la respuesta JSON
        User savedUser = userRepository.save(user);
        savedUser.setPassword(null);
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

    private String generateAlias() throws IOException {
        Path path = Paths.get("C:\\Users\\gasto\\Desktop\\tpi_dh_gaston_campagno\\registry_login_logout\\src\\main\\java\\com\\example\\registry_login_logout\\service\\aliases.txt");
        List<String> words = Files.readAllLines(path);
        System.out.println("WORDS::" + words);

        Random random = new Random();
        return words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size()));
    }

}

