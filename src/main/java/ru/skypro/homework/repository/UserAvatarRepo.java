package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.User;
import ru.skypro.homework.model.UserAvatar;

import java.util.Optional;

@Repository
public interface UserAvatarRepo extends JpaRepository<UserAvatar, Integer> {

    Optional<UserAvatar> findById(Integer id);

    Optional<UserAvatar> findByUser(User user);

}
