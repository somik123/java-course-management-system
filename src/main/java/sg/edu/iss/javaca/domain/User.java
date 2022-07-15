package sg.edu.iss.javaca.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer Id;
	private String lastName;
	private String firstMidName;
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode;
	
	@Column(nullable = true)
	private String profileImage;

	@Transient
	private String passwordConfirm;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role UserRole;

	// @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	// private List<Enrollment> Enrollments;

	public User(String lastName, String firstMidName, String email, String username, String password, Role userRole,
			List<Enrollment> enrollments) {
		this(lastName, firstMidName, email, username, password, true, userRole, enrollments);
	}

	public User(String lastName, String firstMidName, String email, String username, String password, boolean enabled,
			Role userRole, List<Enrollment> enrollments) {
		super();
		this.lastName = lastName;
		this.firstMidName = firstMidName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		UserRole = userRole;
		// Enrollments = enrollments;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

}
