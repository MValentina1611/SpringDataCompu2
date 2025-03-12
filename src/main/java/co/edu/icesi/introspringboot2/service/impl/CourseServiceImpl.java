package co.edu.icesi.introspringboot2.service.impl;

import co.edu.icesi.introspringboot2.entity.Course;
import co.edu.icesi.introspringboot2.entity.Student;
import co.edu.icesi.introspringboot2.repository.EnrollmentRepository;
import co.edu.icesi.introspringboot2.repository.StudentRepository;
import co.edu.icesi.introspringboot2.service.CourseService;
import co.edu.icesi.introspringboot2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void createCourse(Course course) {

    }

    @Override
    public List<Course> listCourseOfStudent(long studentId) {
        var enrollments = enrollmentRepository.findByStudent_Id(studentId);
        return enrollments.stream().map(  enrollment -> enrollment.getCourse()  ).toList();
    }

}
