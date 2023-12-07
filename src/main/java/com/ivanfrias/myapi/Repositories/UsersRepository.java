package com.ivanfrias.myapi.Repositories;

import com.ivanfrias.myapi.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.email = :parametro1 AND u.password = :parametro2")
    boolean login(@Param("parametro1") String parametro1, @Param("parametro2") String parametro2);

    Optional<Users> findOneByEmail(String email);
}
