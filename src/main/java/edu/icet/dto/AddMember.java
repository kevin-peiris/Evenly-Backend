package edu.icet.dto;

import lombok.Data;

@Data
public class AddMember {
    private Integer adminId;
    private Integer groupId;
    private Integer userId;
}
