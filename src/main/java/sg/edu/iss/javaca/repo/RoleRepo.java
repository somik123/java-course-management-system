package sg.edu.iss.javaca.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.javaca.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	@Query("SELECT r FROM Role r WHERE r.name=?1")
	public Role findByName(String name);
}

