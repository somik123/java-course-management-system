package sg.edu.iss.javaca.repo;

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
import sg.edu.iss.javaca.domain.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JavaCaApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepoTest {
	@Autowired
	EnrollmentRepo enrollmentRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	CourseRepo courseRepo;

	@Test
	@Order(1)
	public void testFindUserByUsername() {

		User u1 = userRepo.findByUsername("somik");
		User u2 = userRepo.findByUsername("E0941666");
		ArrayList<User> users = new ArrayList<User>();
		users.add(u1);
		users.add(u2);
		userRepo.saveAll(users);
		Assertions.assertEquals(users.size(), 2);

	}

	@Test
	@Order(2)
	public void testFindAllStudents() {
		List<User> allU = userRepo.findAll();
		List<User> temp = new ArrayList<User>();
		for (User user : allU) {
			if (user.getUserRole().equals("STUDENT")) {
				temp.add(user);
			}
		}
		Assertions.assertNotNull(temp);
	}

	@Test
	@Order(3)
	public void testFindAllLecturer() {
		List<User> allU = userRepo.findAll();
		List<User> temp = new ArrayList<User>();
		for (User user : allU) {
			if (user.getUserRole().equals("LECTURER")) {
				temp.add(user);
			}
		}
		Assertions.assertNotNull(temp);
	}

	@Test
	@Order(4)
	public void testFindAllAdmin() {
		List<User> allU = userRepo.findAll();
		List<User> temp = new ArrayList<User>();
		for (User user : allU) {
			if (user.getUserRole().equals("ADMIN")) {
				temp.add(user);
			}
		}
		Assertions.assertNotNull(temp);
	}

}
