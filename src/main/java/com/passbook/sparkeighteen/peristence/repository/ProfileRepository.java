package com.passbook.sparkeighteen.peristence.repository;

import com.passbook.sparkeighteen.peristence.entity.ProfileEntity;
import com.passbook.sparkeighteen.peristence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Profile repository is for communicate to database for user profile update task.
 */
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    /**
     * Find by user optional.
     *
     * @param user is for find specific user.
     * @return the optional return the user or null.
     */
    Optional<ProfileEntity> findByUser(UserEntity user);

}
