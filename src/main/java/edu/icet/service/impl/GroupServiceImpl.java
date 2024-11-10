package edu.icet.service.impl;

import edu.icet.dto.AddMember;
import edu.icet.dto.Group;
import edu.icet.entity.GroupEntity;
import edu.icet.repository.GroupRepository;
import edu.icet.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    final GroupRepository repository;

    @Override
    public boolean addGroup(Group group) {
        try {
            GroupEntity entity = new ModelMapper().map(group, GroupEntity.class);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Group> getGroup() {
        List<GroupEntity> entityList = repository.findAll();
        List<Group> groupList = new ArrayList<>();

        for (GroupEntity entity : entityList) {
            groupList.add(new ModelMapper().map(entity, Group.class));
        }
        return groupList;
    }

    @Override
    public List<Group> findByName(String name) {
        List<GroupEntity> entityList = repository.findAll();
        List<Group> groupList = new ArrayList<>();

        for (GroupEntity entity : entityList) {
            groupList.add(new ModelMapper().map(entity, Group.class));
        }
        return groupList;
    }

    @Override
    public boolean deleteGroup(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateGroup(Group group) {
        try {
            GroupEntity entity = new ModelMapper().map(group, GroupEntity.class);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Group findById(Integer id) {
        try {
            Optional<GroupEntity> entity = repository.findById(id);
            if (entity!=null) {
                Group group = new ModelMapper().map(entity, Group.class);
                return group;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Group> findByMemberId(Integer id) {
        List<GroupEntity> entityList = repository.findByMemberId(id);
        List<Group> groupList = new ArrayList<>();

        for (GroupEntity entity : entityList) {
            groupList.add(new ModelMapper().map(entity, Group.class));
        }
        return groupList;
    }

}
