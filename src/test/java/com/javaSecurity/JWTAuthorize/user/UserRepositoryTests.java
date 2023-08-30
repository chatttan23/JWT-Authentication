package com.javaSecurity.JWTAuthorize.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.javaSecurity.JWTAuthorize.model.Role;
import com.javaSecurity.JWTAuthorize.model.User;
import com.javaSecurity.JWTAuthorize.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;

	@Test
	public void testCreateUser() {
		PasswordEncoder passEncoder = new BCryptPasswordEncoder();
		String rawPassword="abi123";
		String encPassword = passEncoder.encode(rawPassword);
		
		User newUser = new User("abi@gmail.com", encPassword);
		User saveUser = userRepository.save(newUser);
		assertThat(saveUser).isNotNull();
		assertThat(saveUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testAssignRolesToUser() {
		int userId=2;
		;
		User user=userRepository.findById(userId).get();
		user.addRole(new Role(3));
		user.addRole(new Role(2));
		
		User updatedUser = userRepository.save(user);
		assertThat(updatedUser.getRoles()).hasSize(2);
	}
}
