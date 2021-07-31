package com.lahiru.demo.controller;

import com.lahiru.demo.dto.TeacherDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TeacherController {

    private static List<TeacherDTO> teachers = Stream.of(
            new TeacherDTO(0, "Lahiru"),
            new TeacherDTO(1, "Loom"))
            .collect(Collectors.toList());

    @GetMapping("/api/v1/teacher")
    public List<TeacherDTO> getTeacher() {
        return teachers;
    }

    @PostMapping("/api/v1/teacher")
    public String addTeacher(@RequestBody TeacherDTO teacherDTO) {

        teachers.add(teacherDTO);
        return "Welcome to Teacher Resource - addTeacher";
    }

    @PutMapping("/api/v1/teacher")
    public String updateTeacher(@RequestBody TeacherDTO teacherDTO) {

        TeacherDTO existingTeacherDTO = teachers.get(teacherDTO.getId());
        existingTeacherDTO.setName(teacherDTO.getName());
        return "Welcome to Teacher Resource - updateTeacher";
    }

    @DeleteMapping("/api/v1/teacher")
    public String deleteTeacher(@RequestBody TeacherDTO teacherDTO) {

        teachers.remove(teacherDTO);
        return "Welcome to Teacher Resource - deleteTeacher";
    }

}
