package sg.edu.iss.javaca.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.javaca.domain.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	@Query
	public User findUserByUsernameAndPassword(String username, String password);

	@Query
	public User findByUsername(String username);

	@Query
	public User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
	public User findByVerificationCode(String code);

	// List all Students/Lecturer/Admins

	@Query("SELECT u FROM User u WHERE u.UserRole.name='GUEST' ")
	public List<User> findAllGuests();

	@Query("SELECT u FROM User u WHERE u.UserRole.name='STUDENT' ")
	public List<User> findAllStudents();

	@Query("SELECT u FROM User u WHERE u.UserRole.name='LECTURER' ")
	public List<User> findAllLecturers();

	@Query("SELECT u FROM User u WHERE u.UserRole.name='ADMIN' ")
	public List<User> findAllAdmins();

	// List all Students/Lecturer/Admins

	@Query("SELECT u FROM User u WHERE u.UserRole.name='GUEST' ")
	public Page<User> findAllGuestswithPG(Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.UserRole.name='STUDENT' ")
	public Page<User> findAllStudentswithPG(Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.UserRole.name='LECTURER' ")
	public Page<User> findAllLecturerswithPG(Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.UserRole.name='ADMIN' ")
	public Page<User> findAllAdminswithPG(Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.lastName LIKE CONCAT('%',?1,'%') OR u.firstMidName LIKE CONCAT('%',?1,'%') OR u.username LIKE CONCAT('%',?1,'%') OR u.email LIKE CONCAT('%',?1,'%') ")
	public List<User> findUsersBySearchString(String searchTxt);

}
