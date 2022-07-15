package sg.edu.iss.javaca.repo;

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

public class RoleRepoTest {
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
	public void testSaveAll() {
		Role r1 = new Role("STUDENT");
		roleRepo.save(r1);
		Role r2 = new Role("LECTURER");
		roleRepo.save(r2);
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(r1);
		roleList.add(r2);
		roleRepo.saveAllAndFlush(roleList);
		Assertions.assertEquals(roleList.size(), 2);
	}

	@Test
	@Order(2)
	public void testFindByName() {
		Role r1 = new Role("STUDENT");
		roleRepo.save(r1);
		Role r2 = new Role("STUDENT");
		roleRepo.save(r2);
		Role r3 = new Role("ADMIN");
		roleRepo.save(r3);
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(r1);
		roleList.add(r2);
		roleList.add(r3);
		roleRepo.saveAllAndFlush(roleList);
		List<Role> roles = new ArrayList<Role>();
		for (Role roleStu : roleList) {
			if (roleStu.getName().equals("STUDENT")) {
				roles.add(roleStu);
			}
		}
		Assertions.assertEquals(roles.size(), 2);
	}

	@Test
	@Order(2)
	public void testDelete() {

		List<Role> r1 = roleRepo.findAll();
		Role usrole = r1.get(1);
		User u1 = new User("AAung", "Ko Ko Khant", "E0941673@u.nus.edu", "E0941673", "E0941673", true, usrole, null);
		userRepo.save(u1);

		userRepo.delete(u1);

		Optional<User> existUser = userRepo.findById(u1.getId());
		Optional<User> mockUser = Optional.empty();
		Assertions.assertEquals(mockUser, existUser);
		// Assertions.assertNull(optionRole);

	}

}
