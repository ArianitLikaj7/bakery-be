package com.example.bakerybe;

import com.example.bakerybe.dao.CustomUserRepository;
import com.example.bakerybe.entity.CustomUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BakeryBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakeryBeApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(CustomUserRepository repository) {
//        return args -> {
//            var user = CustomUser.builder()
//                    .firstName("Qendirm")
//                    .lastName("Zeneli")
//                    .username("qendrim")
//                    .bakeryId(Long.valueOf(3))
//                    .build();
//            repository.save(user); // Save the user object to persist it in the database
//        };
//    }


}
