package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.CardDetail;
import edu.mum.cs490.project.domain.Guest;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardDetailRepository extends JpaRepository<CardDetail, Integer> {

    CardDetail findByOwner(User owner);

    CardDetail findByGuest(Guest guest);

    List<CardDetail> findByOwner_idAndStatus(Integer ownerId, Status status);

    @Query("update CardDetail c set c.status = 'DISABLED' where c.id = :cardId")
    @Modifying
    void disableCard(@Param("cardId") Integer cardId);

}
