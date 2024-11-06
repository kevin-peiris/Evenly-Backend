package edu.icet.repository;


import edu.icet.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    List<UserEntity> findByName(String name);

    Optional<UserEntity> findByEmail(String email);
}
