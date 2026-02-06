package com.chinmayie.chinspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ChinspringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChinspringApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository, InteractionRepository interactionRepository) {
        return args -> {
            Customer alex = new Customer(
                    "Alex Johnson",
                    21,
                    "alex@gmail.com",
                    "1234567890",
                    "ACTIVE",
                    "HIGH",
                    LocalDateTime.now().plusDays(2),
                    "admin@admin.com"
            );
            
            Customer jamila = new Customer(
                    "Jamila Ahmed",
                    22,
                    "jamila@gmail.com",
                    "0987654321",
                    "LEAD",
                    "MEDIUM",
                    LocalDateTime.now().minusDays(1), // Overdue
                    "admin@admin.com"
            );

            customerRepository.saveAll(List.of(alex, jamila));

            interactionRepository.save(new Interaction(
                    "Initial discovery call. Very interested in our services.",
                    "CALL",
                    LocalDateTime.now().minusDays(3),
                    alex
            ));

            interactionRepository.save(new Interaction(
                    "Sent follow-up email with pricing details.",
                    "EMAIL",
                    LocalDateTime.now().minusDays(1),
                    alex
            ));

            interactionRepository.save(new Interaction(
                    "Lead came through the website form.",
                    "MEETING",
                    LocalDateTime.now().minusWeeks(1),
                    jamila
            ));
        };
    }
}
