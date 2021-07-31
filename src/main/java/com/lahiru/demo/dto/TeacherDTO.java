package com.lahiru.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherDTO {

    private Integer id;
    private String name;

    public TeacherDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
