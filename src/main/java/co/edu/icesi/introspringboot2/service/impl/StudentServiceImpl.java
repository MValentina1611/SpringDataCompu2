package co.edu.icesi.introspringboot2.service.impl;

import co.edu.icesi.introspringboot2.entity.Course;
import co.edu.icesi.introspringboot2.entity.Enrollment;
import co.edu.icesi.introspringboot2.entity.Student;
import co.edu.icesi.introspringboot2.repository.EnrollmentRepository;
import co.edu.icesi.introspringboot2.repository.StudentRepository;
import co.edu.icesi.introspringboot2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${app.pagination.size}")
    private int pageSize;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional
    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getByProgram(String program){
        return studentRepository.findByProgram(program);
    }

    @Override
    public Page<Student> findAll(int page){
        Pageable pageable = PageRequest.of(page, pageSize);
        return studentRepository.findAll(pageable);
    }

    @Override
    public List<Student> getStudentsByCourse(Course course) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);
        return enrollments.stream().map(Enrollment::getStudent).toList();
    }

    @Override
    public Student getStudentByID(long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    @Override
    public Student getStudentByCode(String code) {
        return studentRepository.findByCode(code).orElseThrow();
    }

}
