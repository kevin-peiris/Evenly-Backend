package edu.icet.controller;

import edu.icet.dto.AddMember;
import edu.icet.dto.Group;
import edu.icet.dto.RemoveMember;
import edu.icet.dto.User;
import edu.icet.service.impl.GroupServiceImpl;
import edu.icet.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    final GroupServiceImpl groupService;
    final UserServiceImpl userService;

    @PostMapping("/group")
    public ResponseEntity<String> add(@RequestBody Group group) {
        group.addMember(group.getAdmin());
        if (groupService.addGroup(group)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Group added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to add Group data.");
        }
    }

    @PostMapping("/group/add-member")
    public ResponseEntity<String> add(@RequestBody AddMember member) {
        if (addMember(member)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Member added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to add Member.");
        }
    }

    private boolean addMember(AddMember member){
        Group group = groupService.findById(member.getGroupId());
        User user = userService.findById(member.getUserId());

        group.addMember(user);
        return groupService.updateGroup(group);
    }

    private boolean removeMember(RemoveMember member){
        Group group = groupService.findById(member.getGroupId());
        User user = userService.findById(member.getUserId());

        group.removeMember(user);
        return groupService.updateGroup(group);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (groupService.deleteGroup(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Group deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to delete Group data.");
        }
    }

    @GetMapping("/group")
    public ResponseEntity<List<Group>> get() {
        List<Group>groups=groupService.getGroup();
        if (groups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/group")
    public ResponseEntity<String> update(@RequestBody Group group){
        if (groupService.updateGroup(group)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Group updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to update Group data.");
        }
    }
}
