package sg.edu.iss.javaca.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.javaca.domain.*;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Integer> {
	@Query("SELECT e FROM Enrollment e WHERE e.user.id=?1")
	public List<Enrollment> findByUserId(Integer userId);
	
	@Query("SELECT e FROM Enrollment e WHERE e.course.id=?1")
	public List<Enrollment> findByCourseId(Integer courseId);

	@Query("SELECT e FROM Enrollment e WHERE e.course.id=?1 AND e.user.UserRole.name='STUDENT'")
	public List<Enrollment> findStudentEnrollmentByCourseId(Integer courseId);

	@Query("SELECT e FROM Enrollment e WHERE e.course.id=?1 AND e.user.UserRole.name='LECTURER'")
	public List<Enrollment> findLecturerEnrollmentByCourseId(Integer id);

	@Query("SELECT e FROM Enrollment e WHERE e.course.id=?1 AND e.user.id=?2")
	public Enrollment findByUserIdAndCourseId(Integer courseId, Integer userId);
}
