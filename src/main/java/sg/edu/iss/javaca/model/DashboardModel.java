package sg.edu.iss.javaca.model;

public class DashboardModel {
	public int courseCount;
	public int studentCount;
	public int lecturerCount;

	public DashboardModel(int courseCount, int studentCount, int lecturerCount) {
		this.courseCount = courseCount;
		this.studentCount = studentCount;
		this.lecturerCount = lecturerCount;
	}
}
