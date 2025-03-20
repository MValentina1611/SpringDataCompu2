package co.edu.icesi.introspringboot2.controller;

import co.edu.icesi.introspringboot2.entity.Course;
import co.edu.icesi.introspringboot2.service.CourseService;
import co.edu.icesi.introspringboot2.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("professors", professorService.getAllProfessor());
        return "course";
    }


    @PostMapping
    public String createCourse(@ModelAttribute Course course) {
        courseService.createCourse(course);
        return "redirect:/course";
    }


}
