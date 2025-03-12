package co.edu.icesi.introspringboot2.controller;

import co.edu.icesi.introspringboot2.entity.Course;
import co.edu.icesi.introspringboot2.entity.Student;
import co.edu.icesi.introspringboot2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @ResponseBody
    public String create(@RequestBody Course course) {
        courseService.createCourse(course);
        return "Creando un curso " + course.getName();
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Course>> create() {
        var list = courseService.listCourseOfStudent(1);
        return ResponseEntity.status(200).body(list);
    }

}
