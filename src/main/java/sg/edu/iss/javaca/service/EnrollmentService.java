package sg.edu.iss.javaca.service;

import java.util.List;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.Enrollment;
import sg.edu.iss.javaca.domain.User;

public interface EnrollmentService {

	public List<Enrollment> findByUser(User user);

	public List<Enrollment> findByCourse(Course course);

	public void saveEnrollment(Enrollment enrollmentn);

	public void saveAll(List<Enrollment> enrollList);

	public void deleteAll();

	public List<Enrollment> findStudentEnrollmentByCourse(Course course);

	public List<Enrollment> findLecturerEnrollmentByCourse(Course course);

	public Enrollment findById(Integer Id);

	public Enrollment findByCourseAndUser(Course course, User user);

	public void delete(Enrollment enroll);

}
