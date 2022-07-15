package sg.edu.iss.javaca.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.Enrollment;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.service.CourseService;
import sg.edu.iss.javaca.service.EnrollmentService;
import sg.edu.iss.javaca.service.UserService;
import sg.edu.iss.javaca.util.UserGrade;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {

	@Autowired
	UserService userService;

	@Autowired
	CourseService courseService;

	@Autowired
	EnrollmentService enrollService;
	
	@GetMapping(value = "/dashboard")
	public String LecturerDashboard(Model model,Authentication auth) {
		User u = userService.findByUsername(auth.getName());
		// User u = userService.findByUserId(13);
		List<Course> courseList = courseService.findCourseByUser(u);
		List<Integer> activeStudents = new ArrayList<>();

		for (Course c : courseList) {
			List<Enrollment> e = enrollService.findStudentEnrollmentByCourse(c);
			activeStudents.add(e.size());
		}
		model.addAttribute("courses", courseList);
		model.addAttribute("activeStudents", activeStudents);
		model.addAttribute("size", courseList.size() - 1);
		return "lecturer/dashboard";
	}

	@GetMapping("/course-taught")
	public String viewCoursesTaught(Model model, Authentication auth) {
		User u = userService.findByUsername(auth.getName());
		// User u = userService.findByUserId(13);
		List<Course> courseList = courseService.findCourseByUser(u);
		List<Integer> activeStudents = new ArrayList<>();

		for (Course c : courseList) {
			List<Enrollment> e = enrollService.findStudentEnrollmentByCourse(c);
			activeStudents.add(e.size());
		}
		model.addAttribute("courses", courseList);
		model.addAttribute("activeStudents", activeStudents);
		model.addAttribute("size", courseList.size() - 1);
		model.addAttribute("gradeAble", true);
		return "/lecturer/courseList";
	}

	@GetMapping("/course-enrollment")
	public String viewCourseEnrollment(Model model) {


		List<Course> courseList = courseService.getallCourseswithoutpagination();
		List<Integer> activeStudents = new ArrayList<>();

		for (Course c : courseList) {
			List<Enrollment> e = enrollService.findStudentEnrollmentByCourse(c);
			activeStudents.add(e.size());
		}
		model.addAttribute("courses", courseList);
		model.addAttribute("activeStudents", activeStudents);
		model.addAttribute("size", courseList.size() - 1);
		model.addAttribute("gradeAble", false);
		return "/lecturer/courseList";
	}

	@GetMapping("/course-enrollment/{id}")
	public ModelAndView viewCourseEnrollment(Model model, @PathVariable Integer id) {

		
		Course c = courseService.findCourse(id);
		List<Enrollment> enroll = enrollService.findStudentEnrollmentByCourse(c);
		List<User> users = new ArrayList<>();
		for(Enrollment e : enroll) {
			users.add(e.getUser());
		}
		
		return new ModelAndView("lecturer/studentList", "users", users);
	}

	@GetMapping("/grade-course/{id}")
	public String gradeCourse(@PathVariable Integer id, Model model) {

		Enrollment enroll = new Enrollment();

		Course c = courseService.getCourseById(id);
		List<Enrollment> enrollList = enrollService.findStudentEnrollmentByCourse(c);
		
		UserGrade userGrade = new UserGrade();
		
		List<String> grades = userGrade.getAllGrades();

		model.addAttribute("grades", grades);
		model.addAttribute("enrollList", enrollList);
		model.addAttribute("enroll", enroll);

		return "/lecturer/gradeCourse";
	}

	@RequestMapping("/save-grade")
	public String gradeCourseByStudent(@ModelAttribute Enrollment enroll) {

		if (enroll != null) {
			Enrollment getEnroll = enrollService.findById(enroll.getId());
			getEnroll.updateGrade(enroll.getGrade());
			enrollService.saveEnrollment(getEnroll);

			return "redirect:/lecturer/student-performance/" + getEnroll.getUser().getId();
		}

		return "redirect:/lecturer/course-taught";
	}

	@GetMapping("/student-performance")
	public ModelAndView listStudents() {

		List<User> users = userService.findAllStudentswithoutPG();
		return new ModelAndView("lecturer/studentList", "users", users);
	}

	@GetMapping("/student-performance/{id}")
	public ModelAndView viewStudentPerformance(@PathVariable Integer id) {

		User studentUser = userService.findByUserId(id);

		String studentName = studentUser.getLastName() + " " + studentUser.getFirstMidName();

		List<Enrollment> enrollments = enrollService.findByUser(studentUser);
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
					// TODO Auto-generated catch block
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
