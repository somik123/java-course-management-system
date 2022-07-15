package sg.edu.iss.javaca.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.service.CourseService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")

public class APIController {

	@Autowired
	CourseService courseService;

	@GetMapping("/courses")
	public List<Course> getCourses() {
		List<Course> courseList = courseService.findAll();
		List<Course> newCourseList = new ArrayList<>();
		for (Course c : courseList) {
			//c.setEnrollments(null);
			newCourseList.add(c);
		}
		return newCourseList;

	}

	@PostMapping("/courses")
	public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
		try {
			Course newCourse = new Course(course.getName(), course.getSize(), course.getCredits(), null);
			courseService.saveCourse(newCourse);
			return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable("id") Integer id) {
		Course course = courseService.findCourse(id);
		
		if (course != null) {
			course.setEnrollments(null);
			return new ResponseEntity<>(course, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/courses/edit/{id}")
	public ResponseEntity<Course> editCourse(@PathVariable("id") int id, @Valid @RequestBody Course course) {

		Course getCourse = courseService.findCourse(id);
		if (getCourse != null) {
			getCourse.setName(course.getName());
			getCourse.setCredits(course.getCredits());
			getCourse.setSize(course.getSize());
			courseService.saveCourse(getCourse);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") int id) {
		try {
			courseService.delete(courseService.findCourse(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/courses")
	public ResponseEntity<HttpStatus> deleteAllCourses() {
		try {
			courseService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
