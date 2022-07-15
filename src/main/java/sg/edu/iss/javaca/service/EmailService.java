package sg.edu.iss.javaca.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import sg.edu.iss.javaca.domain.Course;
import sg.edu.iss.javaca.domain.User;

public interface EmailService {

	public void sendEmail(User user, String subject, String content)
			throws MessagingException, UnsupportedEncodingException;

	public void sendEmailVerification(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException;

	public void sendEmailForgotPassword(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException;

	public void sendEmailEnrolment(User user, Course course) throws MessagingException, UnsupportedEncodingException;

	public void sendEmailSaveNewUser(User user) throws MessagingException, UnsupportedEncodingException;

	public void sendEmailExistingUser(User user, User existingUser)
			throws MessagingException, UnsupportedEncodingException;
}
