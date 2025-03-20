package co.edu.icesi.introspringboot2.controller;


import co.edu.icesi.introspringboot2.entity.Student;
import co.edu.icesi.introspringboot2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String student(Model model) {
        var students = studentService.getAllStudents();
        model.addAttribute("greeting", "Hola mundo");
        model.addAttribute("students", students);
        model.addAttribute("student", new Student());
        return "student";
    }

    @PostMapping
    public String saveStudent(@ModelAttribute Student student) {
        //Almacenar
        studentService.createStudent(student);
        return "redirect:/student";
    }


}
