package sg.edu.iss.javaca.model;

import sg.edu.iss.javaca.domain.Course;

public class EnrollModel {
	public Course course;
	public int avail;

	public EnrollModel(Course course, int avail) {
		this.course = course;
		this.avail = avail;
	}
}
