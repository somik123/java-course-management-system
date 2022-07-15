package sg.edu.iss.javaca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.repo.CourseRepo;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepo courseRepository;

	@Override
	public List<Course> getallCourseswithoutpagination() {
		List<Course> course = courseRepository.findAll();
		return course;
	}

	@Override
	public List<Course> getallCourses(int pageNo) {

		List<Course> courses;
		Pageable paging = PageRequest.of(pageNo, 5);
		Page<Course> paageCourse = courseRepository.findAll(paging);
		paageCourse.getSize();
		courses = paageCourse.getContent();
		return courses;
	}

	@Override
	public int gettotalcoursepage() {
		Pageable paging = PageRequest.of(0, 5);
		Page<Course> paageCourse = courseRepository.findAll(paging);
		return paageCourse.getTotalPages();
	}

	@Override
	public void createCourse(Course course) {
		// TODO Auto-generated method stub

	}

	@Override
	public Course getCourseById(Integer id) {
		return courseRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteAll() {
		courseRepository.deleteAll();
	}

	@Override
	public void saveAll(List<Course> courseList) {
		courseRepository.saveAllAndFlush(courseList);
	}

	@Override
	public void saveCourse(Course course) {
		courseRepository.save(course);
	}

	@Override
	public void delete(Course course) {
		courseRepository.delete(course);

	}

	@Override
	public Course findCourse(Integer courseId) {
		return courseRepository.findById(courseId).orElse(null);
	}

	@Override
	public List<Course> findCourseByUser(User user) {
		return courseRepository.findCourseByUserId(user.getId());
	}

	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		return courseRepository.findAll();
	}

	@Override
	public Course findCourseByName(String name) {
		// TODO Auto-generated method stub
		return courseRepository.findCourseByName(name);
	}
}
