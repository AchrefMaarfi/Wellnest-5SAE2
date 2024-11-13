package com.esprit.wellnest.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.esprit.wellnest.model.User;

import java.util.List;

@Dao
public interface UserDAO{

    // Insertion d'un nouvel utilisateur
    @Insert
    void insert(User user);

    // Requête pour authentifier un utilisateur (à améliorer avec du hashage)
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);


    // Requête pour trouver un utilisateur par son nom d'utilisateur
    @Query("SELECT * FROM users WHERE username = :username")
    User findByUsername(String username);

    // Mise à jour des informations d'un utilisateur existant
    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);

    // Récupérer tous les utilisateurs (exemple pour compléter les fonctionnalités)
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User findByEmail(String email);

    // Méthode pour mettre à jour l'utilisateur
    @Update
    void updateUser(User user);

    @Query("SELECT * FROM users WHERE resetToken = :token")
    User findByToken(String token);

    //getnom by id
    @Query("SELECT username FROM users WHERE id = :userId")
    String getUserNameById(int userId);
}
