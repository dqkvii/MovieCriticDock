package com.aeribmm.filmcritic.DAO;

import com.aeribmm.filmcritic.Model.UserModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByEmail(String email);

    @Transactional
    @Procedure(name = "create_user")
    void createUser(
            @Param("p_user") String username,
            @Param("p_email") String email,
            @Param("p_password") String password
    );

    Optional<User> findByUsername(String username);
}
