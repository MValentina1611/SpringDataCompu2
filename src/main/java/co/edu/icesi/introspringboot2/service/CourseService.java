package co.edu.icesi.introspringboot2.service;

import co.edu.icesi.introspringboot2.entity.Course;
import co.edu.icesi.introspringboot2.entity.Student;

import java.util.List;

public interface CourseService {
    void createCourse(Course course);
    List<Course> listCourseOfStudent(long studentId);
}
