package com.exerciseBonus.repositories;

import com.exerciseBonus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    //Get Users by Email Provider
    List<User> findByEmail(String email);

    //Remove Inactive Users - by setting is_deleted=true
    List<User> findByLastTimeLoggedInAfter(LocalDate date);
}
