package edu.icet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private UserEntity admin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> members = new ArrayList<>();

    public void addMember(UserEntity user) {
        if (this.members == null) {
            this.members = new ArrayList<>();
        }
        this.members.add(user);
    }

    public void removeMember(UserEntity user) {
        this.members.remove(user);
    }
}
