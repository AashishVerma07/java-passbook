package com.passbook.sparkeighteen.peristence.repository;

import com.passbook.sparkeighteen.peristence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * The interface User repository is for communicating with database to retrieve data.
 */
@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Integer> {
    /**
     * Find by email optional.
     *
     * @param email is for user login or signup.
     * @return the optional return the user or null.
     */
    Optional<UserEntity> findByEmail(String email);

//    Optional<UserEntity> findById(Integer userID);
}