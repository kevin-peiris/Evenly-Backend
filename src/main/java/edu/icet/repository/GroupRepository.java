package edu.icet.repository;

import edu.icet.dto.Group;
import edu.icet.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity,Integer> {
    List<GroupEntity> findByName(String name);
    @Query("SELECT g FROM GroupEntity g JOIN g.members m WHERE m.id = :memberId")
    List<GroupEntity> findByMemberId(@Param("memberId") Integer memberId);
}
