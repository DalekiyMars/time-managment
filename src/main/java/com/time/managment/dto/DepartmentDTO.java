package com.time.managment.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DepartmentDTO {
    private UserDTO user;
    private Integer department;
}
