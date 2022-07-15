package sg.edu.iss.javaca.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import sg.edu.iss.javaca.domain.User;

public interface UserService {

	public User findByUsername(String username);

	public User findByEmail(String email);

	public User findByUserId(Integer id);

	public void register(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

	public void forgotPassword(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

	public boolean verify(String verificationCode);

	public User reset(String verificationCode);

	public void updatePassword(User user);

	public void update(User user);

	public void save(User user);

	public void saveAll(List<User> userList);

	public void delete(User user);

	public void deleteAll();

	// CRUD all users
	public List<User> findAll();

	// List all Students/Lecturer/Admins
	public List<User> findAllGuestswithoutPG();

	public List<User> findAllStudentswithoutPG();

	public List<User> findAllLecturerswithoutPG();

	public List<User> findAllAdminswithoutPG();

	public List<User> findAllGuestswithPagination(int pageNo);

	public List<User> findAllStudentswithPagination(int pageNo);

	public List<User> findAllLecturerswithPagination(int pageNo);

	public List<User> findAllAdminswithPagination(int pageNo);

	public int gettotalguestsspage();

	public int gettotalStudentsspage();

	public int gettotalLecturerspage();

	public int gettotalAdminspage();

	public List<User> findUsersBySearchString(String searchTxt);
}
