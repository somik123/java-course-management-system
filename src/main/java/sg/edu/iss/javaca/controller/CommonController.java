package sg.edu.iss.javaca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import sg.edu.iss.javaca.domain.Role;
import sg.edu.iss.javaca.domain.User;
import sg.edu.iss.javaca.service.RoleService;
import sg.edu.iss.javaca.service.UserService;
import sg.edu.iss.javaca.validator.UserValidator;

@Controller
public class CommonController {
	@Autowired
	ServletContext context;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private RoleService roleService; 

	@RequestMapping(value = "/")
	public String home(Authentication authentication, HttpSession session) {
		if (authentication != null) {
			String userName = authentication.getName();
			User u = userService.findByUsername(userName);
			session.setAttribute("profileImage", u.getProfileImage());
		}
		return "index";
	}

	@GetMapping("/register")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "common/register";
	}

	@PostMapping("/register")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "common/register";
		}

	    
		Role r = roleService.findRoleByName("GUEST");
		userForm.setUserRole(r);

		userService.register(userForm, getSiteURL(request));

		return "common/register_success";
	}

	@GetMapping("/forgot_password")
	public String ForgotPassword(Model model) {
		model.addAttribute("userForm", new User());

		return "common/forgot_password";
	}

	@PostMapping("/forgot_password")
	public String ForgotPassword(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		userValidator.validateForgotPassword(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "common/forgot_password";
		}

		userService.forgotPassword(userForm, getSiteURL(request));

		return "common/forgot_password_success";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		return "common/login";
	}
	
	@GetMapping("/login-success")
	public String loginSuccess(Authentication auth) {
		if (auth == null) {
			return "common/login";
		}
		
		String userName = auth.getName();
		User u = userService.findByUsername(userName);
		if(u.getUserRole().getName().equals("ADMIN"))
			return "redirect:/admin/dashboard";
		else if(u.getUserRole().getName().equals("STUDENT"))
			return "redirect:/student/dashboard";
		else if(u.getUserRole().getName().equals("LECTURER"))
			return "redirect:/lecturer/dashboard";
		else
			return "index";
	}

	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		if (userService.verify(code)) {
			return "common/verify_success";
		} else {
			return "common/verify_fail";
		}
	}

	@GetMapping("/reset_password")
	public String resetPassword(@Param("code") String code, Model model) {
		User user = userService.reset(code);
		if (user != null) {
			model.addAttribute("userForm", user);
			return "common/reset_password";
		} else {
			return "common/verify_fail";
		}
	}

	@PostMapping("/reset_password")
	public String resetPassword(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validateRestPassword(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "common/reset_password";
		}

		User u = userService.findByUserId(userForm.getId());
		u.setPassword(userForm.getPassword());
		userService.updatePassword(u);

		return "common/reset_password_success";
	}

	/* Helper Method */
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	@GetMapping(value = "/setting")
	public String setting(Model model, Authentication authentication) {
		if (authentication == null) {
			return "common/login";
		}
		String userName = authentication.getName();
		User u = userService.findByUsername(userName);
		model.addAttribute("userForm", u);
		return "common/setting";
	}

	@PostMapping(value = "/setting")
	public String setting(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

		User u = userService.findByUserId(userForm.getId());
		u.setFirstMidName(userForm.getFirstMidName());
		u.setLastName(userForm.getLastName());
		userService.update(u);

		return "redirect:/setting?success";
	}

	@GetMapping("/change_password")
	public String changePassword(Model model, Authentication authentication) {
		if (authentication == null) {
			return "common/login";
		}
		String userName = authentication.getName();
		User user = userService.findByUsername(userName);
		if (user != null) {
			model.addAttribute("userForm", user);
			return "common/reset_password";
		} else {
			return "common/verify_fail";
		}
	}

	@GetMapping("/upload_profile_image")
	public String UploadProfileImage(Model model, Authentication authentication) {

		if (authentication == null)
			return "common/login";

		else
			return "common/upload_profile_image";
	}

	@PostMapping("/upload_profile_image")
	public String UploadProfileImage(@RequestParam("image") MultipartFile multipartFile, Authentication authentication,
			HttpSession session) throws IOException, ServletException {

		if (authentication == null) {
			return "common/login";
		}

		File file = ResourceUtils.getFile("classpath:just-creek-353810-0e32bfe96c34.json");
		InputStream in = new FileInputStream(file);
		Credentials credentials = GoogleCredentials.fromStream(in);
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		String bucketName = "sa54team1";
		checkFileExtension(fileName);
		fileName = java.time.LocalDateTime.now().toString() + fileName;

		storage.create(
				BlobInfo.newBuilder(bucketName, fileName)
						.setAcl(new ArrayList<>(Arrays.asList(Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(),
								com.google.cloud.storage.Acl.Role.READER))))
						.setContentType("image/jpeg").build(),
				multipartFile.getBytes()
		// file.openStream()
		);

		System.out.println("https://storage.googleapis.com/sa54team1/" + fileName);

		String profileImage = "https://storage.googleapis.com/sa54team1/" + fileName;
		String userName = authentication.getName();
		User u = userService.findByUsername(userName);
		u.setProfileImage(profileImage);
		userService.update(u);
		session.setAttribute("profileImage", profileImage);

		return "redirect:/setting";
	}

	private void checkFileExtension(String fileName) throws ServletException {
		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
			String[] allowedExt = { ".jpg", ".jpeg", ".png", ".gif", ".PNG", ".JPG" };
			for (String ext : allowedExt) {
				if (fileName.endsWith(ext)) {
					return;
				}
			}
			throw new ServletException("file must be an image");
		}
	}
}
