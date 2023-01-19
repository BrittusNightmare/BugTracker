package com.bbenn.systems.bugtracker.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<Users> getUsers(){
        return userRepository.findAll();

    }

    public void addNewUser(Users user) {
        Optional<Users> userOptional =  userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new IllegalStateException("email already in use");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists){
            throw new IllegalStateException("No existing user with id " + userId);
        }
        userRepository.deleteById(userId);
    }
    @Transactional
    public void updateUser(Long userId, String name, String email, String role){
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No existing user with id " + userId));
        if (name != null && name.length() > 0 && !Objects.equals(user.getName(), name)){
            user.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)){
            Optional<Users> usersOptional = userRepository.findUserByEmail(email);
            if (usersOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }

        if (role != null && role.length() > 0 && !Objects.equals(user.getRole(), UserRoles.valueOf(role.toUpperCase()))){
            user.setRole(UserRoles.valueOf(role.toUpperCase()));
        }
    }
}
