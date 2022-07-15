package sg.edu.iss.javaca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.javaca.domain.Role;
import sg.edu.iss.javaca.repo.RoleRepo;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepo roleRepo;

	@Override
	public List<Role> findAll() {
		return roleRepo.findAll();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		roleRepo.deleteAll();
	}

	@Override
	public void saveAll(List<Role> roleList) {
		// TODO Auto-generated method stub
		roleRepo.saveAllAndFlush(roleList);
	}

	@Override
	public Role findRoleById(Integer Id) {
		return roleRepo.findById(Id).get();
	}

	@Override
	public Role findRoleByName(String name) {
		return roleRepo.findByName(name);
	}

}
