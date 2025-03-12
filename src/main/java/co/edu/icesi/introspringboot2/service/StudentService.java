package co.edu.icesi.introspringboot2.service;

import co.edu.icesi.introspringboot2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    void createStudent(Student student);
    List<Student > getAllStudents();
    List<Student> getByProgram(String program);
    Page<Student> findAll(int page);
}
