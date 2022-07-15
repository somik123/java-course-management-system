package sg.edu.iss.javaca.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.User;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendEmail(User user, String subject, String content)
			throws MessagingException, UnsupportedEncodingException {
		String fromAddress = "sa54team1@gmail.com";
		String senderName = "SA54 Team 1 Java CA";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}

	@Override
	public void sendEmailVerification(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "SA54 Team 1.";

		content = content.replace("[[name]]", user.getFirstMidName() + " " + user.getLastName());
		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		sendEmail(user, subject, content);
	}

	@Override
	public void sendEmailForgotPassword(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String subject = "Forgot Password";
		String content = "Dear [[name]],<br>" + "Please click the link below to reset your password:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">Reset</a></h3>" + "Thank you,<br>" + "SA54 Team 1.";

		content = content.replace("[[name]]", user.getFirstMidName() + " " + user.getLastName());
		String resetURL = siteURL + "/reset_password?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", resetURL);

		sendEmail(user, subject, content);
	}

	@Override
	public void sendEmailEnrolment(User user, Course course) throws MessagingException, UnsupportedEncodingException {
		String subject = "Registration for [[courseName]]";
		String content = "Dear [[name]],<br>" + "You have successfully enrolled for the course: [[courseName]]<br>"
				+ "Thank you,<br>" + "SA54 Team 1.";

		content = content.replace("[[courseName]]", course.getName());
		subject = subject.replace("[[courseName]]", course.getName());
		content = content.replace("[[name]]", user.getFirstMidName() + " " + user.getLastName());

		sendEmail(user, subject, content);
	}

	@Override
	public void sendEmailSaveNewUser(User user) throws MessagingException, UnsupportedEncodingException {
		String subject = "Account creation by admin";
		String content = "Dear [[name]],<br>"
				+ "An account has been created by the admin with the following details:<br>"
				+ "Username: [[userName]]<br>" + "Role: [[role]]<br>" + "Account status: [[accountStatus]]<br>"
				+ "Thank you,<br>" + "SA54 Team 1.";

		content = content.replace("[[name]]", user.getFirstMidName() + " " + user.getLastName());
		content = content.replace("[[userName]]", user.getUsername());
		content = content.replace("[[role]]", user.getUserRole().getName());
		if (user.isEnabled()) {
			content = content.replace("[[accountStatus]]", "Enabled");
		} else {
			content = content.replace("[[accountStatus]]", "Disabled");
		}

		sendEmail(user, subject, content);
	}

	@Override
	public void sendEmailExistingUser(User user, User existingUser)
			throws MessagingException, UnsupportedEncodingException {
		String subject = "Account details modification by admin";
		String content = "Dear [[name]],<br>" + "The following details has been modified by the admin:<br><table>";
		content = content.replace("[[name]]", user.getFirstMidName() + " " + user.getLastName());

		if (!user.getPassword().equals(existingUser.getPassword())) {
			content += "(Your password has been changed)<br>";
		}

		if (!user.getUsername().equals(existingUser.getUsername())) {
			content += "<tr><td>Previous Username: [[oldUN]]</td>     <td>New UserName: [[newUN]]</td></tr>";
			content = content.replace("[[oldUN]]", existingUser.getUsername());
			content = content.replace("[[newUN]]", user.getUsername());
		}

		if (!user.getEmail().equals(existingUser.getEmail())) {
			content += "<tr><td>Previous Email: [[oldEmail]]</td>     <td>New Email: [[newEmail]]</td></tr>";
			content = content.replace("[[oldEmail]]", existingUser.getEmail());
			content = content.replace("[[newEmail]]", user.getEmail());
		}

		if (!user.getFirstMidName().equals(existingUser.getFirstMidName())) {
			content += "<tr><td>Previous First & Middle Name: [[oldFM]]</td>     <td>New First & Middle Name: [[newFM]]</td></tr>";
			content = content.replace("[[oldFM]]", existingUser.getFirstMidName());
			content = content.replace("[[newFM]]", user.getFirstMidName());
		}

		if (!user.getLastName().equals(existingUser.getLastName())) {
			content += "<tr><td>Previous Last Name: [[oldLN]]</td>     <td>New Last Name: [[newLN]]</td></tr>";
			content = content.replace("[[oldLN]]", existingUser.getLastName());
			content = content.replace("[[newLN]]", user.getLastName());
		}

		if (user.isEnabled() != existingUser.isEnabled()) {
			content += "<tr><td>Previous Account Status:  [[oldAS]]</td>     <td>New Account Status: [[newAS]]</td></tr>";
			if (existingUser.isEnabled()) {
				content = content.replace("[[oldAS]]", "Enabled");
				content = content.replace("[[newAS]]", "Disabled");
			} else {
				content = content.replace("[[oldAS]]", "Disabled");
				content = content.replace("[[newAS]]", "Enabled");
			}
		}

		if (!user.getUserRole().getName().equals(existingUser.getUserRole().getName())) {
			content += "<tr><td>Previous User Role: [[oldUR]]</td>     <td>New User Role: [[newUR]]</td></tr>";
			content = content.replace("[[oldUR]]", existingUser.getUserRole().getName());
			content = content.replace("[[newUR]]", user.getUserRole().getName());
		}

		content += "</table><br>Thank you,<br>" + "SA54 Team 1.";

		sendEmail(user, subject, content);
	}
}
