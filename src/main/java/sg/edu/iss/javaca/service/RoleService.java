package sg.edu.iss.javaca.service;

import java.util.List;

import sg.edu.iss.javaca.domain.Role;

public interface RoleService {
	public void deleteAll();

	public void saveAll(List<Role> roleList);

	public List<Role> findAll();

	public Role findRoleById(Integer Id);

	public Role findRoleByName(String name);
}
