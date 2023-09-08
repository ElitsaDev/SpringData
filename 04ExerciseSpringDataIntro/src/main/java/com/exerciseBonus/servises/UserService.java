package com.exerciseBonus.servises;

import com.exerciseBonus.entities.User;

import java.time.LocalDate;
import java.util.List;


public interface UserService {
    void persist(User user);

    List<User> findUsersByEmail(String email);

    List<User> findAllByLastTimeLoggedInAfter(LocalDate date);
}
