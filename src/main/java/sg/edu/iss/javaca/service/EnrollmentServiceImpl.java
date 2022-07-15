package sg.edu.iss.javaca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.Enrollment;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.repo.EnrollmentRepo;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	private EnrollmentRepo enrollmentRepo;

	@Override
	public Enrollment findById(Integer Id) {
		return enrollmentRepo.findById(Id).get();
	}

	@Override
	public List<Enrollment> findByUser(User user) {
		return enrollmentRepo.findByUserId(user.getId());
	}

	@Override
	public List<Enrollment> findByCourse(Course course) {
		return enrollmentRepo.findByCourseId(course.getId());
	}

	@Override
	public List<Enrollment> findStudentEnrollmentByCourse(Course course) {
		return enrollmentRepo.findStudentEnrollmentByCourseId(course.getId());
	}

	@Override
	public List<Enrollment> findLecturerEnrollmentByCourse(Course course) {
		// TODO Auto-generated method stub
		return enrollmentRepo.findLecturerEnrollmentByCourseId(course.getId());
	}

	@Override
	public void saveEnrollment(Enrollment enrollment) {
		enrollmentRepo.save(enrollment);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		enrollmentRepo.deleteAll();
	}

	@Override
	public void saveAll(List<Enrollment> enrollList) {
		// TODO Auto-generated method stub
		enrollmentRepo.saveAllAndFlush(enrollList);
	}

	@Override
	public Enrollment findByCourseAndUser(Course course, User user) {
		// TODO Auto-generated method stub
		return enrollmentRepo.findByUserIdAndCourseId(course.getId(), user.getId());
	}

	@Override
	public void delete(Enrollment enroll) {
		// TODO Auto-generated method stub
		enrollmentRepo.delete(enroll);
	}

}
