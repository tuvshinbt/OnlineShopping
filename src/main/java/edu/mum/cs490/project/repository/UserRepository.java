package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {

    T getByUsername(String username);

    @Query("SELECT count(id) > 0 FROM User where id <> :id AND username = :username")
    Boolean existByIdNotAndUsername(@Param("id") Integer id, @Param("username") String username);

  /*  @Modifying
    @Query("UPDATE User AS u SET u.password = :#{#user.password} WHERE u.id = :#{#user.id}")
    void updatePassword(@Param("user") User user);*/
}
