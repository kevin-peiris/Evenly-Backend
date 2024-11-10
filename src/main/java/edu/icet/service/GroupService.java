package edu.icet.service;

import edu.icet.dto.AddMember;
import edu.icet.dto.Group;
import edu.icet.dto.User;

import java.util.List;

public interface GroupService {
    boolean addGroup(Group group);

    List<Group> getGroup();

    List<Group> findByName(String name);

    boolean deleteGroup(Integer id);

    boolean updateGroup(Group group);

    Group findById(Integer id);

    List<Group> findByMemberId(Integer id);

}
