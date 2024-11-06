package edu.icet.repository;

import edu.icet.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity,Integer> {
    List<GroupEntity> findByName(String name);
}
