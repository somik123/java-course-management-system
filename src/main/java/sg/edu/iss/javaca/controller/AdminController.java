package sg.edu.iss.javaca.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.Enrollment;
import sg.edu.iss.javaca.domain.Role;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.model.DashboardModel;
import sg.edu.iss.javaca.service.CourseService;
import sg.edu.iss.javaca.service.EmailService;
import sg.edu.iss.javaca.service.EnrollmentService;
import sg.edu.iss.javaca.service.RoleService;
import sg.edu.iss.javaca.service.UserService;
import sg.edu.iss.javaca.validator.UserValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private EnrollmentService enrollService;

	@Autowired
	private EmailService emailService;

	@GetMapping(value = "/dashboard")
	public String adminDashboard(Model model) {
		List<Course> courseList = courseService.getallCourseswithoutpagination();
		int courseCount = courseList.size();
		int studentCount = userService.findAllStudentswithoutPG().size();
		int lecturerCount = userService.findAllLecturerswithoutPG().size();
		DashboardModel dashboard = new DashboardModel(courseCount, studentCount, lecturerCount);

		List<Double> seatAvailablesPercent = new ArrayList<>();
		List<Integer> seatAvailables = new ArrayList<>();

		for (Course c : courseList) {
			List<Enrollment> e1 = enrollService.findStudentEnrollmentByCourse(c);
			// List<Enrollment> e2 = enrollService.findLecturerEnrollmentByCourse(c);
			seatAvailablesPercent.add((e1.size() * 100.0 / c.getSize()));
			seatAvailables.add(c.getSize() - e1.size());
		}

		model.addAttribute("dashboard", dashboard);
		model.addAttribute("seatAvailablesPercent", seatAvailablesPercent);
		model.addAttribute("seatAvailables", seatAvailables);
		model.addAttribute("size", courseList.size() - 1);
		model.addAttribute("courses", courseList);

		return "admin/dashboard";
	}

	@GetMapping(value = "/createcourse")
	public ModelAndView createcourse() {

		Course course = new Course();
		ModelAndView mav = new ModelAndView("admin/course-form", "course", course);
		mav.addObject("status", "Create Course Form");
		return mav;
	}

	@GetMapping(value = "/courses")
	public ModelAndView showallCourses(Model model, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		List<Course> courseList = courseService.getallCourses(pageNo);
		int totalPages = courseService.gettotalcoursepage();

		if (pageNo == totalPages - 1) {
			pageNo = pageNo - 1;
		}
		if (pageNo == -1) {
			pageNo = 0;
		}
		model.addAttribute("pageNo", pageNo);
		return new ModelAndView("admin/manageCourse", "courseList", courseList);
	}

	@GetMapping(value = "/editcourse/{id}")
	public ModelAndView editCourse(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("admin/course-form");
		Course course = courseService.getCourseById(id);
		mav.addObject("course", course);
		mav.addObject("status", "Update Course Form");
		return mav;
	}

	@PostMapping(value = "/savecourse")
	public ModelAndView saveCourse(@ModelAttribute @Valid Course course, BindingResult result) {

		ModelAndView mav = new ModelAndView("redirect:/admin/courses");
		if (result.hasErrors()) {
			return new ModelAndView("admin/course-form");
		}
		courseService.saveCourse(course);
		return mav;
	}

	@GetMapping(value = "/deletecourse/{id}")
	public ModelAndView deleteCourse(@PathVariable Integer id) {

		Course course = courseService.getCourseById(id);

		if (course != null) {
			List<Enrollment> enrollList = enrollService.findByCourse(course);
			if (enrollList.size() > 0) {
				for (Enrollment enroll : enrollList) {
					enrollService.delete(enroll);
				}
			}
			courseService.delete(course);
		}

		return new ModelAndView("redirect:/admin/courses");
	}

	@GetMapping("/users/Guests")
	public ModelAndView listGuests(Model model, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		int totalPages = userService.gettotalguestsspage();
		if (pageNo == totalPages) {
			pageNo = pageNo - 1;
		}
		List<User> users = userService.findAllGuestswithPagination(pageNo);
		if (pageNo == -1) {
			pageNo = 0;
		}
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("userType", "guest");
		return new ModelAndView("admin/manageUsers", "users", users);
	}

	@GetMapping("/users/Students")
	public ModelAndView listStudents(Model model, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		List<User> users = userService.findAllStudentswithPagination(pageNo);
		int totalPages = userService.gettotalguestsspage();
		if (pageNo == totalPages) {
			pageNo = pageNo - 1;
		}
		if (pageNo == -1) {
			pageNo = 0;
		}
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("userType", "student");
		return new ModelAndView("admin/manageUsers", "users", users);
	}

	@GetMapping("/users/Lecturers")
	public ModelAndView listLecturers(Model model, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		List<User> users = userService.findAllLecturerswithPagination(pageNo);
		int totalPages = userService.gettotalguestsspage();
		if (pageNo == totalPages) {
			pageNo = pageNo - 1;
		}
		if (pageNo == -1) {
			pageNo = 0;
		}
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("userType", "lecturer");
		return new ModelAndView("admin/manageUsers", "users", users);
	}

	@GetMapping("/users/Admins")
	public ModelAndView listAdmins(Model model, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		/* List<User> users = userService.findAllAdminswithPagination(pageNo); */
		int totalPages = userService.gettotalguestsspage();
		if (pageNo == totalPages) {
			pageNo = pageNo - 1;
		}
		List<User> users = userService.findAllAdminswithPagination(pageNo);
		if (pageNo == -1) {
			pageNo = 0;
		}
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("userType", "admin");
		return new ModelAndView("admin/manageUsers", "users", users);
	}

	@GetMapping("/search")
	public ModelAndView searchUsers(@RequestParam(defaultValue = "") String searchTxt, Model model) {
		List<User> users = new ArrayList<>();

		if (searchTxt.length() == 0)
			users = userService.findAll();
		else
			users = userService.findUsersBySearchString(searchTxt);

		return new ModelAndView("admin/manageUsers", "users", users);
	}

	@GetMapping("/deleteuser/{id}")
	public String deleteUser(@PathVariable Integer id, @Param("role") String role) {

		role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase() + "s";
		User u = userService.findByUserId(id);
		if (u != null) {
			List<Enrollment> enrollList = enrollService.findByUser(u);
			if (enrollList.size() > 0) {
				for (Enrollment enroll : enrollList) {
					enrollService.delete(enroll);
				}
			}
			userService.delete(u);
		}
		return "redirect:/admin/users/" + role;
	}

	@GetMapping("/adduser")
	public String addUser(Model model) {

		User u = new User();
		Role role = new Role();
		List<Role> roleList = roleService.findAll();

		model.addAttribute("user", u);
		model.addAttribute("role", role);
		model.addAttribute("roleList", roleList);
		return "admin/user-form";
	}

	@GetMapping("/edituser/{id}")
	public String addUser(Model model, @PathVariable Integer id) {
		User u = userService.findByUserId(id);
		Role role = roleService.findRoleById(u.getUserRole().getId());
		List<Role> roleList = roleService.findAll();

		model.addAttribute("role", role);
		model.addAttribute("user", u);
		model.addAttribute("roleList", roleList);
		return "admin/user-form";
	}

	@PostMapping("/saveuser")
	public String saveUser(@ModelAttribute User user, BindingResult result, @ModelAttribute Role role,
			Model model) throws UnsupportedEncodingException, MessagingException {

		String roleName = role.getName();
		roleName = roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase() + "s";

		userValidator.validateAddUser(user, result);
		
		if (result.hasErrors()) {
			List<Role> roleList = roleService.findAll();
			model.addAttribute("role", role);
			model.addAttribute("user", user);
			model.addAttribute("roleList", roleList);
			return "admin/user-form";
		}

		if (user != null) {
			Role userRole = roleService.findRoleByName(role.getName());
			user.setUserRole(userRole);

			if (user.getId() != null) { // existing user
				User existingUser = userService.findByUserId(user.getId());
				emailService.sendEmailExistingUser(user, existingUser);
				userService.update(user);
			} else { // new user
				emailService.sendEmailSaveNewUser(user);
				userService.save(user);
			}
		}

		return "redirect:/admin/users/" + roleName;
	}

	@RequestMapping("course-enrollment")
	public String manageEnrollment(Model model) {

		List<Course> courseList = courseService.getallCourseswithoutpagination();
		List<Integer> activeStudents = new ArrayList<>();
		List<Integer> activeLecturers = new ArrayList<>();

		for (Course c : courseList) {
			List<Enrollment> e1 = enrollService.findStudentEnrollmentByCourse(c);
			List<Enrollment> e2 = enrollService.findLecturerEnrollmentByCourse(c);
			activeStudents.add(e1.size());
			activeLecturers.add(e2.size());
		}

		model.addAttribute("courses", courseList);
		model.addAttribute("activeStudents", activeStudents);
		model.addAttribute("activeLecturers", activeLecturers);
		model.addAttribute("size", courseList.size() - 1);
		model.addAttribute("gradeAble", false);

		return "/admin/courseEnrollment";

	}

	@GetMapping("manage-enrolment/{id}")
	public String manageEnrollment(@PathVariable Integer id, Model model) {

		// To display existing enrollment
		Course c = courseService.getCourseById(id);
		List<User> userList = new ArrayList<>();

		List<Enrollment> enrollStu = enrollService.findStudentEnrollmentByCourse(c);
		List<Enrollment> enrollLec = enrollService.findLecturerEnrollmentByCourse(c);

		// userList.addAll(userService.findAllStudentsByCourse(c));
		// userList.addAll(userService.findAllLecturersByCourse(c));

		for (Enrollment e : enrollStu) {
			userList.add(e.getUser());
		}
		for (Enrollment e : enrollLec) {
			userList.add(e.getUser());
		}

		// To display add user list for new enrollment
		User newUser = new User();
		List<User> allUsers = new ArrayList<>();
		allUsers.addAll(userService.findAllLecturerswithoutPG());
		allUsers.addAll(userService.findAllStudentswithoutPG());

		model.addAttribute("course", c);
		model.addAttribute("users", userList);
		model.addAttribute("allUsers", allUsers);
		model.addAttribute("newUser", newUser);

		return "admin/manageEnrolment";
	}

	@PostMapping("manage-enrolment/{id}")
	public String addEnrollment(@PathVariable Integer id, @ModelAttribute User user) {

		if (user != null && id != null) {
			Course c = courseService.getCourseById(id);
			User u = userService.findByUserId(user.getId());
			if (c != null && u != null) {
				Enrollment getEnroll = enrollService.findByCourseAndUser(c, u);
				if (getEnroll == null) {
					Enrollment enroll = new Enrollment(u, c);
					enrollService.saveEnrollment(enroll);
				}
			}
		}

		return "redirect:/admin/manage-enrolment/" + id + "?success";
	}

	@RequestMapping("manage-enrolment/{courseId}/{userId}")
	public String manageEnrollment(@PathVariable Integer courseId, @PathVariable Integer userId) {

		Course c = courseService.getCourseById(courseId);
		User u = userService.findByUserId(userId);
		if (c != null && u != null) {
			Enrollment enroll = enrollService.findByCourseAndUser(c, u);
			if (enroll != null) {
				enrollService.delete(enroll);
			}
		}
		return "redirect:/admin/manage-enrolment/" + courseId;

	}
}
