package sg.edu.iss.javaca.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="course_id")
	private Integer Id;
	
	@NotNull
	@NotBlank(message = "Course name cannot be blank. ")
	private String name;
	
	@NotNull(message = "Course size must not null. ")
	@Range(min=10,max=100,message = "Course size valid range is from 10 to 100. ")
	private Integer size;
	
	@NotNull(message = "Course credits cannot be null. ")
	@Range(min = 2, max = 10, message = "Course credits valid range is from 2 to 10. ")
	private Integer credits;
	
	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	@JsonIgnore 
	private List<Enrollment> enrollments;

	public Course(String name, Integer size, Integer credits, List<Enrollment> enrollments) {
		super();
		this.name = name;
		this.size = size;
		this.credits = credits;
		//this.enrollments = enrollments;
	}
}
