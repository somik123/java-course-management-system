package sg.edu.iss.javaca.service;

import java.util.List;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.User;

public interface CourseService {
	public List<Course> getallCourseswithoutpagination();
	public List<Course> getallCourses(int pageNo);
	public  void createCourse(Course course);
	public Course getCourseById(Integer id);
	public void saveCourse(Course course);
	public void deleteAll();
	public void saveAll(List<Course> courseList);
	public void delete(Course course);
	public Course findCourse(Integer courseId);
	public List<Course> findCourseByUser(User user);
	public List<Course> findAll();
	public Course findCourseByName(String name);
	public int gettotalcoursepage();
	
}
