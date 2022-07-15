	package sg.edu.iss.javaca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Integer Id;
	
	@NotNull
	private String name;
	
	//@OneToMany(mappedBy = "UserRole", fetch = FetchType.EAGER)
	//private List<User> users;

	public Role(String name) {
		super();
		this.name = name;
	}
	

}
