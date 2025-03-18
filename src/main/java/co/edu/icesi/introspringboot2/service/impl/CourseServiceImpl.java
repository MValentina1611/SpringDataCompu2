package co.edu.icesi.introspringboot2.service.impl;

import co.edu.icesi.introspringboot2.entity.Course;
import co.edu.icesi.introspringboot2.entity.Student;
import co.edu.icesi.introspringboot2.repository.CourseRepository;
import co.edu.icesi.introspringboot2.repository.EnrollmentRepository;
import co.edu.icesi.introspringboot2.repository.StudentRepository;
import co.edu.icesi.introspringboot2.service.CourseService;
import co.edu.icesi.introspringboot2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        if(courseRepository.findByName(course.getName()).isEmpty()) {
            return courseRepository.save(course);
        }else{
            throw new RuntimeException("Course already exists");
        }
    }

    @Override
    public List<Course> listCourseOfStudent(long studentId) {
        var enrollments = enrollmentRepository.findByStudent_Id(studentId);
        return enrollments.stream().map(  enrollment -> enrollment.getCourse()  ).toList();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id).orElseThrow(()-> new RuntimeException("Course not found"));
        /*
        Optional<Course> optCourse = courseRepository.findById(id);
        if(optCourse.isPresent()){
            return optCourse.get();
        }else{
            throw new RuntimeException("There is no course with id " + id);
        }
        */
    }

    @Override
    public void deleteCourse(long courseId) {
        if(courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        }else{
            throw new RuntimeException("Course not found");
        }
    }

}
