package sg.edu.iss.javaca.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.Enrollment;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.model.EnrollModel;
import sg.edu.iss.javaca.service.CourseService;
import sg.edu.iss.javaca.service.EmailService;
import sg.edu.iss.javaca.service.EnrollmentService;
import sg.edu.iss.javaca.service.UserService;
import sg.edu.iss.javaca.util.UserGrade;

@Controller
@RequestMapping(value = "/student")
public class StudentController {

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private EmailService emailService;

	private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);
	
	@GetMapping(value = "/dashboard")
	public String StudentDashboard(Model model) {
		List<Course> courseList = courseService.getallCourseswithoutpagination();
		List<Double> seatAvailablesPercent = new ArrayList<>();
		List<Integer> seatAvailables = new ArrayList<>();

		for (Course c : courseList) {
			List<Enrollment> e1 = enrollmentService.findStudentEnrollmentByCourse(c);
			// List<Enrollment> e2 = enrollService.findLecturerEnrollmentByCourse(c);
			seatAvailablesPercent.add((e1.size() * 100.0 / c.getSize()));
			seatAvailables.add(c.getSize() - e1.size());
		}

		model.addAttribute("seatAvailablesPercent", seatAvailablesPercent);
		model.addAttribute("seatAvailables", seatAvailables);
		model.addAttribute("size", courseList.size() - 1);
		model.addAttribute("courses", courseList);
		return "student/dashboard";
	}

	@GetMapping(value = "/courses")
	public ModelAndView GetAllUnEnrolledCourses(Model model, Authentication auth) {

		String name = auth.getName();
		User studentUser = userService.findByUsername(name);
		List<Course> coursesDone = enrollmentService.findByUser(studentUser).stream().map(e -> e.getCourse())
				.collect(Collectors.toList());
		List<Course> result = new ArrayList<Course>();
		List<Course> courses = courseService.getallCourseswithoutpagination();
		for (Course course : courses) {
			if (!coursesDone.contains(course)) {
				result.add(course);
			}
		}
		ModelAndView mv = new ModelAndView("student/course", "courseList", result);
		return mv;
	}

	@GetMapping(value = "/enroll-course")
	public ModelAndView GetAllUnEnrollCourses(Model model, Authentication auth) {

		String name = auth.getName();
		User studentUser = userService.findByUsername(name);
		List<Course> coursesDone = enrollmentService.findByUser(studentUser).stream().map(e -> e.getCourse())
				.collect(Collectors.toList());
		List<EnrollModel> result = new ArrayList<EnrollModel>();
		List<Course> courses = courseService.getallCourseswithoutpagination();
		for (Course course : courses) {
			if (!coursesDone.contains(course)) {
				int enrolled = enrollmentService.findByCourse(course).size();
				result.add(new EnrollModel(course, course.getSize() - enrolled));
			}
		}
		ModelAndView mv = new ModelAndView("student/enrolCourse", "courseList", result);
		return mv;
	}

	@GetMapping(value = "/enroll/{cid}")
	public String Enroll(Model model, Authentication auth, @PathVariable int cid)
			throws UnsupportedEncodingException, MessagingException {

		Course course = courseService.getCourseById(cid);
		if (course == null) {
			LOG.info("no such course");
			return "error/404";
		}
		boolean isAvailiable = enrollmentService.findByCourse(course).size() < course.getSize();
		if (!isAvailiable) {
			return "error/400";
		}

		String name = auth.getName();
		User studentUser = userService.findByUsername(name);

		boolean enrolled = enrollmentService.findByUser(studentUser).stream().map(e -> e.getCourse())
				.collect(Collectors.toList()).contains(course);
		if (enrolled) {
			return "error/400";
		}

		Enrollment enrollment = new Enrollment(studentUser, course);

		enrollmentService.saveEnrollment(enrollment);
		emailService.sendEmailEnrolment(studentUser, course);
		return "redirect:/student/GPA";
	}

	@GetMapping(value = "/GPA")
	public ModelAndView GetGPA(Model model, Authentication auth) {

		String name = auth.getName();
		User studentUser = userService.findByUsername(name);

		String studentName = studentUser.getLastName() + " " + studentUser.getFirstMidName();

		List<Enrollment> enrollments = enrollmentService.findByUser(studentUser);
		double gpa = 0;
		double creditSum = 0;
		if (enrollments.size() == 0) {
			return new ModelAndView("student/grades", "enrollments", null);
		}

		UserGrade userGrade = new UserGrade();
		for (Enrollment enrollment : enrollments) {
			if (enrollment.getGrade() != null) {
				try {
					gpa += userGrade.getGPA(enrollment.getGrade()) * enrollment.getCourse().getCredits();
				} catch (Exception e) {
					e.printStackTrace();
				}
				creditSum += enrollment.getCourse().getCredits();
			}
		}

		ModelAndView mView;

		if (creditSum > 0) {
			gpa = gpa / creditSum;
			mView = new ModelAndView("student/grades", "gpa", gpa);
		} else
			mView = new ModelAndView("student/grades", "gpa", "");

		mView.addObject("enrollments", enrollments);
		mView.addObject("studentName", studentName);

		return mView;
	}


}
