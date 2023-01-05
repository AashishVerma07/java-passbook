package com.passbook.sparkeighteen.peristence.repository;

import com.passbook.sparkeighteen.peristence.entity.TransactionEntity;
import com.passbook.sparkeighteen.peristence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Transaction repository helps to communicate with database where in the data is retrieved / found.
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    /**
     * Find by user optional.
     * @param user function to perform on this particular user
     * @return the optional - return th user or null
     */
     
    Page<TransactionEntity> findByUser(Pageable page, UserEntity user);

}

    Optional<List<TransactionEntity>> findByUser(UserEntity user);}

