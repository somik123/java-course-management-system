package sg.edu.iss.javaca.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.Enrollment;
import sg.edu.iss.javaca.domain.Role;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.service.CourseService;
import sg.edu.iss.javaca.service.EnrollmentService;
import sg.edu.iss.javaca.service.RoleService;
import sg.edu.iss.javaca.service.UserService;

@Controller
public class InstallController {
	@Autowired
	private CourseService courseService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EnrollmentService enrollService;
	

	@RequestMapping("/install")
	public String setupDatabase() {

		enrollService.deleteAll();
		userService.deleteAll();
		courseService.deleteAll();
		roleService.deleteAll();

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(new Role("GUEST"));
		roleList.add(new Role("STUDENT"));
		roleList.add(new Role("LECTURER"));
		roleList.add(new Role("ADMIN"));
		roleService.saveAll(roleList);

		
		
		List<Course> courseList = new ArrayList<Course>();
		courseList.add(new Course("Software Analysis and Design", 80, 6, null));
		courseList.add(new Course("Enterprise Solutions Design and Development", 80, 8, null));
		courseList.add(new Course("Mobile Application Development", 80, 6, null));
		courseList.add(new Course("Machine Learning Application Development", 80, 6, null));
		courseList.add(new Course("Web Application Development", 80, 6, null));
		courseList.add(new Course("Application Development Project", 80, 6, null));
		courseService.saveAll(courseList);

		
		
		List<User> userList = new ArrayList<User>();
		userList.add(new User("Aung", "Ko Ko Khant", "E0941673@u.nus.edu", "E0941673", "E0941673", true,
				roleList.get(1), null));
		userList.add(
				new User("Aye", "Mya Phoo", "E0941666@u.nus.edu", "E0941666", "E0941666", true, roleList.get(1), null));
		userList.add(new User("Cheong", "Li Yuan", "E0008064@u.nus.edu", "E0008064", "E0008064", true, roleList.get(1),
				null));
		userList.add(new User("Khan", "Sher Mostafa Somik", "somik@u.nus.edu", "somik", "somik", true, roleList.get(1),
				null));
		userList.add(new User("Nyan", "Htet Win Maung", "E0941691@u.nus.edu", "E0941691", "E0941691", true,
				roleList.get(1), null));
		userList.add(
				new User("Xie", "Tingting", "E0941694@u.nus.edu", "E0941694", "E0941694", true, roleList.get(1), null));
		userList.add(
				new User("Zhao", "Ziyou", "E0320219@u.nus.edu", "E0320219", "E0320219", true, roleList.get(1), null));
		userList.add(new User("Guest", "Guest", "guest@example.com", "guest", "guest", true, roleList.get(0), null));

		userList.add(new User("Student", "Student", "student@example.com", "student", "student", true, roleList.get(1),
				null));

		userList.add(new User("Lecturer", "Lecturer", "lecturer@example.com", "lecturer", "lecturer", true,
				roleList.get(2), null));
		userList.add(new User("Chai", "Yuenkwan", "yuenkwan@u.nus.edu", "yuenkwan", "yuenkwan", true,
				roleList.get(2), null));
		userList.add(new User("Lee", "Chukmunn", "chukmunn@u.nus.edu", "chukmunn", "chukmunn", true,
				roleList.get(2), null));
		userList.add(new User("Liu", "Fan", "fan@u.nus.edu", "fan", "fan", true,
				roleList.get(2), null));
		userList.add(new User("Nguyen", "Tritin", "tritin@u.nus.edu", "tin", "tin", true,
				roleList.get(2), null));
		userList.add(new User("Tan", "Cherwah", "cherwah@u.nus.edu", "cherwah", "cherwah", true,
				roleList.get(2), null));
		userList.add(new User("Tan", "MengyokeEsther", "esther@u.nus.edu", "esther", "esther", true,
				roleList.get(2), null));
		userList.add(new User("R_Asia", "Suria", "suria@u.nus.edu", "suria", "suria", true,
				roleList.get(2), null));

		userList.add(new User("Admin", "Admin", "admin@example.com", "admin", "admin", true, roleList.get(3), null));
		userList.add(new User("Lim", "Hweizhong", "hweizhong@u.nus.edu", "hweizhong", "hweizhong", true, roleList.get(3), null));
		userList.add(new User("Chin", "Callie", "callie@u.nus.edu", "callie", "callie", true, roleList.get(3), null));
		userList.add(new User("Lim", "Elsie", "elsie@u.nus.edu", "elsie", "elsie", true, roleList.get(3), null));
		userList.add(new User("Sin", "Pameline", "Pameline@u.nus.edu", "pameline", "pameline", true, roleList.get(3), null));
		userService.saveAll(userList);

		
		
		
		List<Enrollment> enrollList = new ArrayList<Enrollment>();

		userList = userService.findAll();
		enrollList.add(new Enrollment(userList.get(5), courseList.get(2)));
		enrollList.add(new Enrollment(userList.get(5),courseList.get(0)));
		enrollList.add(new Enrollment(userList.get(2),courseList.get(2)));
		enrollList.add(new Enrollment(userList.get(2),courseList.get(5)));
		enrollList.add(new Enrollment(userList.get(0),courseList.get(1)));
		enrollList.add(new Enrollment(userList.get(0),courseList.get(3)));
		enrollList.add(new Enrollment(userList.get(0),courseList.get(5)));
		enrollList.add(new Enrollment(userList.get(4),courseList.get(4)));
		enrollList.add(new Enrollment(userList.get(4),courseList.get(1)));
		enrollList.add(new Enrollment(userList.get(4),courseList.get(5)));
		enrollList.add(new Enrollment(userList.get(6),courseList.get(0)));
		enrollList.add(new Enrollment(userList.get(6),courseList.get(3)));
		enrollList.add(new Enrollment(userList.get(3),courseList.get(4)));
		enrollList.add(new Enrollment(userList.get(3),courseList.get(5)));
		enrollList.add(new Enrollment(userList.get(10),courseList.get(0)));
		enrollList.add(new Enrollment(userList.get(10),courseList.get(5)));
		enrollList.add(new Enrollment(userList.get(11),courseList.get(3)));
		enrollList.add(new Enrollment(userList.get(11),courseList.get(2)));
		enrollList.add(new Enrollment(userList.get(12),courseList.get(1)));
		enrollList.add(new Enrollment(userList.get(12),courseList.get(4)));
		enrollList.add(new Enrollment(userList.get(12),courseList.get(5)));
		enrollList.add(new Enrollment(userList.get(14),courseList.get(2)));
		enrollList.add(new Enrollment(userList.get(14),courseList.get(3)));
		enrollList.add(new Enrollment(userList.get(14),courseList.get(0)));
		enrollList.add(new Enrollment(userList.get(15),courseList.get(0)));
		enrollList.add(new Enrollment(userList.get(16),courseList.get(4)));
		enrollList.add(new Enrollment(userList.get(16),courseList.get(3)));
		enrollList.add(new Enrollment(userList.get(16),courseList.get(1)));
		enrollList.add(new Enrollment(userList.get(8),courseList.get(1)));
		enrollList.add(new Enrollment(userList.get(8),courseList.get(0)));
		enrollList.add(new Enrollment(userList.get(8),courseList.get(5)));
		enrollList.add(new Enrollment(userList.get(8),courseList.get(3)));
		enrollList.add(new Enrollment(userList.get(9),courseList.get(0)));
		enrollList.add(new Enrollment(userList.get(9),courseList.get(1)));
		enrollList.add(new Enrollment(userList.get(9),courseList.get(4)));
		
		enrollService.saveAll(enrollList);

		return "index";
	}
}
