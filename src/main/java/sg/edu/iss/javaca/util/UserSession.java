package sg.edu.iss.javaca.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sg.edu.iss.javaca.domain.Role;
import sg.edu.iss.javaca.domain.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSession implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Role role;
}
