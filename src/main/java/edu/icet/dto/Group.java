package edu.icet.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {
    private Integer id;
    private String name;
    private User admin;
    private List<User> members;

    public void addMember(User user) {
        if(this.members == null)
            this.members = new ArrayList<>();
        this.members.add(user);
    }
    public void removeMember(User user) {
        this.members.remove(user);
    }
}



















