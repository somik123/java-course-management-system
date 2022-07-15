package sg.edu.iss.javaca.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.iss.javaca.JavaCaApplication;
import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.Enrollment;
import sg.edu.iss.javaca.domain.Role;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.service.CourseService;
import sg.edu.iss.javaca.service.EnrollmentService;
import sg.edu.iss.javaca.service.RoleService;
import sg.edu.iss.javaca.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JavaCaApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepoTest {
	 @Autowired
	    EnrollmentRepo enrollmentRepo;
	    EnrollmentService enrollmentSer;

	    @Autowired
	    UserRepo userRepo;
	    UserService userSer;

	    @Autowired
	    RoleRepo roleRepo;
	    RoleService roleSer;

	    @Autowired
	    CourseRepo courseRepo;
	    CourseService courseSer;
	    
    @Test
    @Order(1)
	public void testCreatCourse()  {
    	List<Course> courseList = new ArrayList<Course>();
		courseList.add(new Course("Software Analysis", 80, 6, null));
		courseList.add(new Course(" Design and Development", 80, 8, null));
		courseList.add(new Course("C#", 80, 6, null));
		courseRepo.saveAllAndFlush(courseList);
		Course course1=courseList.get(0);
		Assertions.assertNotNull(course1.getId());
    }

   
    @Test
    @Order(2)
    
    public void testGetAllCourses() {
	   	List<Course> courses=courseRepo.findAll();
        Assertions.assertNotNull(courses);
    }

    @Test
    @Order(3)
    public void testGetCoursesByUserId() {

    	User u1=userRepo.findByUsername("somik");
        Course c1=courseRepo.findAll().get(0);
        Enrollment en1=new Enrollment(u1,c1);
        enrollmentRepo.save(en1);
        List<Enrollment> enrollobject=enrollmentRepo.findByUserId(u1.getId());
        Assertions.assertNotNull(enrollobject.size()) ;
    }
    
    
      
    	

       	
       }
       


