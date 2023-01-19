package com.bbenn.systems.bugtracker.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            Users testUser = new Users(1L,
                    "Test User",
                    "test_user@example.com",
                    UserRoles.TESTER);

            Users developerUser = new Users(
                    "Developer User",
                    "developer_user@example.com",
                    UserRoles.DEVELOPER);
            repository.saveAll(List.of(testUser, developerUser));
        };
    }
}
