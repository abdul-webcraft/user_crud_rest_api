package com.crud;

import com.crud.config.AppConstants;
import com.crud.model.Role;
import com.crud.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class UserCrudAppApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserCrudAppApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        try {
            Role adminRole=new Role();
            adminRole.setId(AppConstants.ADMIN_USER);
            adminRole.setName("ADMIN_USER");

            Role normalRole=new Role();
            normalRole.setId(AppConstants.NORMAL_USER);
            normalRole.setName("NORMAL_USER");

            List<Role> roles = List.of(adminRole, normalRole);
            List<Role> saveRoles = roleRepository.saveAll(roles);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
