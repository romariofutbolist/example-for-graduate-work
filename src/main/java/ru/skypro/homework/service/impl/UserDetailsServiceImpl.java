package ru.skypro.homework.service.impl;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exceptions.InvalidPasswordException;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.repository.UserRepo;

import java.util.Optional;

import static liquibase.repackaged.net.sf.jsqlparser.util.validation.metadata.NamedObject.user;


/**Интерфейс UserDetailsService используется
 для получения данных,
 связанных с пользователем.*/
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsManager {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    /*процесс поиска пользователя.*/
    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        Optional<ru.skypro.homework.model.User> byEmail = userRepo.findByEmail(email);
        System.out.println(byEmail);
        var user = byEmail.get();
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        logger.info("Password in manager after login: {}", user.getPassword());
        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }


    @Override
    public void createUser(UserDetails user) {
        logger.info("Password in manager: {}", user.getPassword());
        ru.skypro.homework.model.User result = new ru.skypro.homework.model.User();
        result.setEmail(user.getUsername());
        result.setPassword(user.getPassword());
        logger.info("Password in manager after create entity: {}", result.getPassword());
        userRepo.save(result);
    }

    @Override
    public void updateUser(UserDetails user) {
        ru.skypro.homework.model.User updated = userRepo.findByEmail(user.getUsername()).get();
        if(updated == null){
            throw new UserNotFoundException("Can't found user to update");
        }
        updated.setEmail(user.getUsername());
        updated.setPassword(user.getPassword());
        userRepo.save(updated);
    }

    @Override
    public void deleteUser(String username) {
        ru.skypro.homework.model.User check = userRepo.findByEmail(username).get();
        if(check == null){
            throw new UserNotFoundException("User not found");
        }
        userRepo.delete(check);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        logger.info("Hi in CustomUserDetailsService in changePassword()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Loggin User for change password: {}", authentication.getName());
        ru.skypro.homework.model.User update = userRepo.findByEmail(authentication.getName()).get();
        if(update == null){
            throw new UserNotFoundException("User not fond");
        }
        if(update.getPassword().equals(passwordEncoder.encode(oldPassword))){
            throw new InvalidPasswordException("Invalid password");
        }
        logger.info("Old password: {}", update.getPassword());
        update.setPassword(passwordEncoder.encode(newPassword));
        logger.info("New password: {}", update.getPassword());
        userRepo.save(update);
    }

    @Override
    public boolean userExists(String username) {
        Optional<ru.skypro.homework.model.User> check = userRepo.findByEmail(username);
        return check.isPresent();
    }
}