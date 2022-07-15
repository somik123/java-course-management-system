package sg.edu.iss.javaca.repo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EnrollmentRepoTest {
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
    public void testFindEnrollByUserId() {
        enrollmentRepo.deleteAll();
        enrollmentRepo.flush();

        User u1= userRepo.findByUsername("somik");
        Course c1=courseRepo.findAll().get(0);
        Enrollment en1=new Enrollment(u1,c1);
      
        enrollmentRepo.save(en1);
        
        List<Enrollment> enrollobject=enrollmentRepo.findByUserId(u1.getId());
       
        assertEquals(enrollobject.size(),1) ;
    }
    
    @Test
    @Order(2)
    public void testFindEnrollByCourseId() {
        enrollmentRepo.deleteAll();
        enrollmentRepo.flush();
        User u2= userRepo.findByUsername("somik");
        Course c2=courseRepo.findAll().get(1);
        Enrollment en1=new Enrollment(u2,c2);
      
        enrollmentRepo.save(en1);
        
        List<Enrollment> enrollobject=enrollmentRepo.findByCourseId(c2.getId());
       
        assertEquals(enrollobject.size(),1) ;
    }
     
    @Test
    @Order(3)
    
    public void testFindStudentEnrollByCourseId() {

        Course c2=courseRepo.findAll().get(1);
		/* userRepo.saveAll(temp); */
        List<Enrollment> en1=new ArrayList<Enrollment>();        
        en1 = enrollmentRepo.findByCourseId(c2.getId());
        enrollmentRepo.saveAll(en1);
    
        List<Enrollment> enrollobject=enrollmentRepo.findByCourseId(c2.getId());
        assertEquals(enrollobject.size(),1) ;
    } 
  
   
    @Test
    @Order(4)
    public void testSaveEnrollment() {
        
        Role r1=new Role("STUDENT");
        roleRepo.save(r1);
        User u3=new User("Cheong", "Li Yuan", "E0008064@u.nus.edu", "E0008064", "E0008064", true, r1, null);
        userRepo.save(u3);
        Course c3=new Course("Mobile Application Development", 80, 6, null);
        courseRepo.save(c3);
        Enrollment en3=new Enrollment(u3,c3);
        ArrayList<Enrollment> enList=new ArrayList<Enrollment>();
        enList.add(en3);
        Assertions.assertEquals(enList.size(),1);
    }
        
    
    
    
    
    
}
        
        

    	
    	
    
    
        
        
        
        
        

       

       

   




