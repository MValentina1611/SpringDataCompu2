package co.edu.icesi.introspringboot2.controller.api;

import co.edu.icesi.introspringboot2.entity.Course;
import co.edu.icesi.introspringboot2.service.CourseService;
import co.edu.icesi.introspringboot2.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.status(200).body(
                List.of("Curso 1", "Curso 2", "Curso 3", "Curso 4")
        );
    }


}
