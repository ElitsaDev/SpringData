package com.exerciseBonus;

import com.exerciseBonus.entities.Town;
import com.exerciseBonus.entities.User;
import com.exerciseBonus.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;

    @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    public ConsoleRunner() {
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setAge(40);
        user.setUserName("Petar");
        user.setPassword("aA123%");
        user.setEmail("user_az@abv.bg");
        user.setLastTimeLoggedIn(LocalDate.of(1997, 1, 1));

        File picture = new File("src\\main\\resources\\files\\smallPicture.jpg");
        byte[] pictureBytes = new byte[(int) picture.length()];
        FileInputStream fileInputStream = new FileInputStream(picture);
        int read = fileInputStream.read(pictureBytes);

        user.setProfilePicture(pictureBytes);
        fileInputStream.close();
        userService.persist(user);

        Town town = new Town("Blagoevgrad", "Bulgaria");
        user.setBornTown(town);


        User user2 = new User();
        user2.setUserName("Elica");
        user2.setPassword("eli1Sw&%");
        user2.setEmail("eli_and@abv.com");
        user2.setLastTimeLoggedIn(LocalDate.of(2021, 10, 15));
        userService.persist(user2);
/*
todo find by domain only, not full email
*/
        List<User> users = userService.findUsersByEmail("user_az@abv.bg");
        if (!users.isEmpty()) {
            users.forEach(u -> System.out.println(u.getUserName() + " " + u.getEmail()));
        } else {
            System.out.println("No users found with email domain abv.bg");
        }

        LocalDate year2000 = LocalDate.of(2000, 12, 31);
        List<User> deletedUser = userService.findAllByLastTimeLoggedInAfter(year2000);
        deletedUser.forEach(d -> System.out.println(d.getUserName() + " " + d.isDeleted()));
    }
}
