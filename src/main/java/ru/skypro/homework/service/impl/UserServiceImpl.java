package ru.skypro.homework.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepo;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User by username " + email + " not found."));
    }

    @Override
    public void updateUserInfo(User user) {
        User savedUser = findUserByEmail(user.getEmail());

        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setPhone(user.getPhone());

        userRepo.save(savedUser);
    }

    @Override
    public ResponseEntity changeUserPassword(String username, String currentPassword, String newPassword) {
        Optional<User> user = userRepo.findByEmail(username);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User currentUser = user.get();

        if (!encoder.matches(currentPassword, user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            currentUser.setPassword(encoder.encode(newPassword));
            userRepo.save(currentUser);
        }

        return ResponseEntity.ok().build();
    }

}
