package com.example.Nimish.BlogApp;

import com.example.Nimish.BlogApp.config.appConstants;
import com.example.Nimish.BlogApp.entities.role;
import com.example.Nimish.BlogApp.repositories.roleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private roleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));

		try{
			role role = new role();
			role.setId(appConstants.ADMIN_USER);
			role.setName("ADMIN_USER");

			role role1= new role();
			role1.setId(appConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");

			List<role> roles = List.of(role,role1);

			List<role> result =  this.roleRepo.saveAll(roles);

			result.forEach(r->{
				System.out.println(r.getName());
			});

		}catch(Exception e){
			e.printStackTrace();
		}
	}




}
