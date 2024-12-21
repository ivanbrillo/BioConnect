package org.unipi.bioconnect.seeder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.unipi.bioconnect.model.Role;
import org.unipi.bioconnect.model.User;
import org.unipi.bioconnect.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${dataseeder.enabled}")
    private boolean isDataSeederEnabled;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!isDataSeederEnabled) {
            System.out.println("DataSeeder is disabled.");
            return;
        }
        // ! TEST - Svuota la collezione
        //userRepository.deleteAll();

        // Crea e aggiungi utenti aggiornati
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setRole(Role.valueOf("ADMIN"));

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setRole(Role.valueOf("REGISTERED"));
        // Aggiungi commenti dinamicamente con il formato desiderato
        //user2.addComment("P123", "Questo è il primo commento");
        //user2.addComment("P456", "Secondo commento");

        User user3 = new User();
        user3.setUsername("guest");
        user3.setPassword(passwordEncoder.encode("password"));
        user3.setRole(Role.valueOf("UNREGISTERED"));

        // Salva gli utenti nella collezione
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        System.out.println("Dati fittizi aggiornati in MongoDB.");
    }
}
