package sg.edu.iss.javaca.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.repo.RoleRepo;
import sg.edu.iss.javaca.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RoleRepo roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmailService emailService;

	@Override
	public void save(User user) {
		// roleRepository.save(user.getUserRole());
		String userPass = user.getPassword();
		if (userPass.length() == 0) {
			userPass = user.getUsername();
		}
		user.setPassword(bCryptPasswordEncoder.encode(userPass));
		user.setUserRole(user.getUserRole());
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByUserId(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public void saveAll(List<User> userList) {
		for (User user : userList) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		userRepository.saveAllAndFlush(userList);
	}

	@Override
	public void register(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
		roleRepository.save(user.getUserRole());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole(user.getUserRole());
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(false);
		userRepository.save(user);
		emailService.sendEmailVerification(user, siteURL);
	}

	@Override
	public void forgotPassword(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
		user = userRepository.findByEmail(user.getEmail());
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(false);
		userRepository.save(user);
		emailService.sendEmailForgotPassword(user, siteURL);
	}

	@Override
	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);

			return true;
		}

	}

	@Override
	public User reset(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return null;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			user.setPassword(null);
			user.setPasswordConfirm(null);
			userRepository.save(user);

			return user;
		}

	}

	@Override
	public void updatePassword(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.deleteById(user.getId());
	}

	// for general retrieval
	@Override
	public List<User> findAllGuestswithoutPG() {
		return userRepository.findAllGuests();
	}

	@Override
	public List<User> findAllStudentswithoutPG() {
		return userRepository.findAllStudents();
	}

	@Override
	public List<User> findAllLecturerswithoutPG() {
		return userRepository.findAllLecturers();
	}

	@Override
	public List<User> findAllAdminswithoutPG() {
		return userRepository.findAllAdmins();
	}

	// for client site retrieval
	@Override
	public List<User> findAllGuestswithPagination(int pageNo) {
		List<User> users;
		Pageable paging = PageRequest.of(pageNo, 5);
		Page<User> paageGuest = userRepository.findAllGuestswithPG(paging);
		paageGuest.getSize();
		users = paageGuest.getContent();
		return users;
	}

	@Override
	public List<User> findAllStudentswithPagination(int pageNo) {
		List<User> users;
		Pageable paging = PageRequest.of(pageNo, 5);
		Page<User> paageStudent = userRepository.findAllStudentswithPG(paging);
		paageStudent.getSize();
		users = paageStudent.getContent();
		return users;
	}

	@Override
	public List<User> findAllLecturerswithPagination(int pageNo) {
		List<User> users;
		Pageable paging = PageRequest.of(pageNo, 5);
		Page<User> paageLecturer = userRepository.findAllLecturerswithPG(paging);
		paageLecturer.getSize();
		users = paageLecturer.getContent();
		return users;
	}

	@Override
	public List<User> findAllAdminswithPagination(int pageNo) {
		List<User> users;
		Pageable paging = PageRequest.of(pageNo, 5);
		Page<User> paageAdmin = userRepository.findAllAdminswithPG(paging);
		paageAdmin.getSize();
		users = paageAdmin.getContent();
		return users;
	}

	@Override
	public int gettotalguestsspage() {
		Pageable paging = PageRequest.of(0, 5);
		Page<User> paageCourse = userRepository.findAllGuestswithPG(paging);
		return paageCourse.getTotalPages();
	}

	@Override
	public int gettotalStudentsspage() {
		Pageable paging = PageRequest.of(0, 5);
		Page<User> paageCourse = userRepository.findAllStudentswithPG(paging);
		return paageCourse.getTotalPages();
	}

	@Override
	public int gettotalLecturerspage() {
		Pageable paging = PageRequest.of(0, 5);
		Page<User> paageCourse = userRepository.findAllLecturerswithPG(paging);
		return paageCourse.getTotalPages();
	}

	@Override
	public int gettotalAdminspage() {
		Pageable paging = PageRequest.of(0, 5);
		Page<User> paageCourse = userRepository.findAllAdminswithPG(paging);
		return paageCourse.getTotalPages();
	}
	
	@Override
	public List<User> findUsersBySearchString(String searchTxt){
		return userRepository.findUsersBySearchString(searchTxt);
	}

}