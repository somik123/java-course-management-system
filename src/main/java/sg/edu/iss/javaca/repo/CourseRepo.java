package sg.edu.iss.javaca.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.javaca.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Integer> {

	@Query("SELECT c FROM Enrollment e JOIN e.course c WHERE e.user.id=?1")
	public List<Course> findCourseByUserId(Integer id);

	@Query
	public Course findCourseByName(String name);

	public Page<Course> findAll(Pageable pageable);
}
