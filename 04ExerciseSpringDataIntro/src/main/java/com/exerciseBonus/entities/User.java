package com.exerciseBonus.entities;

import com.exerciseBonus.annotations.EmailValidate;
import com.exerciseBonus.annotations.PasswordValidate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User extends Name {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name",nullable = false)
    private String userName;

    private String password;

    @EmailValidate()
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "registered_on")
    private Date registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDate lastTimeLoggedIn;

    @Size(min = 1, max = 120)
    @Column(name = "age",columnDefinition = "TINYINT")
    private int age;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "profile_picture", unique = true, columnDefinition = "LONGBLOB")
    private byte[] profilePicture;

    @OneToOne(mappedBy = "user",targetEntity = Town.class)
    private Town bornTown;

    @OneToMany(mappedBy = "user")
    private Set<Album> personalAlbums;

//ManyToMany self-relation
    @ManyToMany
    @JoinTable(name = "users_friends",
    joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns =
    @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends;

    public User() {
        super();
        this.friends =  new HashSet<User>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Length(min = 4, max = 30)
    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        if(userName.length()< 4 || userName.length()>30){
            throw new IllegalArgumentException("Username length should be between 4 and 30.");
        }
        this.userName = userName;
    }

    @Column(name = "password", nullable = false)
    @PasswordValidate(message = "Password is incorrect!",
            containsDigit = true,
            containsLowercase = true,
            containsSpecialSymbols = true,
            containsUppercase = true)
    public String getPassword() {
        return password;
    }

    @PasswordValidate
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDate getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDate lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
//        if(age < 1 || age > 120){
//            throw new IllegalArgumentException("Age should be between 1 and 120.");
//        }
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    public Set<Album> getPersonalAlbums() {
        return personalAlbums;
    }

    public void setPersonalAlbums(Set<Album> personalAlbums) {
        this.personalAlbums = personalAlbums;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", registeredOn=" + registeredOn +
                ", lastTimeLoggedIn=" + lastTimeLoggedIn +
                ", age=" + age +
                ", isDeleted=" + isDeleted +
                ", profilePicture=" + //Arrays.toString(profilePicture) +
                '}';
    }
}
