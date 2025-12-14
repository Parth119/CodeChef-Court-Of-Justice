package com.vit.codechef.courtOfJustice.repository;

import com.vit.codechef.courtOfJustice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
// For finding user during login
    Optional<UserEntity> findByUsername(String username);
}