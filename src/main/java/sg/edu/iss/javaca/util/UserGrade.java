package sg.edu.iss.javaca.util;

import java.util.ArrayList;
import java.util.List;

public class UserGrade {
	private List<String> grades = new ArrayList<>();
	private List<Double> gpa = new ArrayList<>();

	public UserGrade() {
		super();
		// TODO Auto-generated constructor stub
		this.grades.add("A+");
		this.grades.add("A");
		this.grades.add("A-");
		this.grades.add("B+");
		this.grades.add("B");
		this.grades.add("B-");
		this.grades.add("C+");
		this.grades.add("C");
		this.grades.add("C-");
		this.grades.add("D+");
		this.grades.add("D");
		this.grades.add("D-");

		this.gpa.add(5.0);
		this.gpa.add(5.0);
		this.gpa.add(4.5);
		this.gpa.add(4.0);
		this.gpa.add(3.5);
		this.gpa.add(3.0);
		this.gpa.add(2.5);
		this.gpa.add(2.0);
		this.gpa.add(1.5);
		this.gpa.add(1.0);
		this.gpa.add(0.0);
	}

	public Double getGPA(String grade) throws Exception {
		int index = this.grades.indexOf(grade);
		return this.gpa.get(index);
	}

	public List<String> getAllGrades() {
		return this.grades;
	}
}
