package sg.edu.iss.javaca.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="enrollment_id")
	private Integer Id;
	
	private String grade;
	private LocalDateTime createDate;
	private LocalDateTime updatedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	@NotNull
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="course_id")
	@NotNull
	private Course course;

	
	public Enrollment(User user, Course course) {
		super();
		this.createDate = LocalDateTime.now();
		this.user = user;
		this.course = course;
	} 
	
	public void updateGrade(String grade) {
		this.grade = grade;
		this.updatedDate = LocalDateTime.now();
	}
}
