package com.exerciseBonus.servises;

import com.exerciseBonus.entities.User;
import com.exerciseBonus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void persist(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllByLastTimeLoggedInAfter(LocalDate date) {
        List<User> users = userRepository.findByLastTimeLoggedInAfter(date);
        users.forEach(u-> u.setDeleted(true));
        return users;
    }
}
