package com.javaSecurity.JWTAuthorize.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.javaSecurity.JWTAuthorize.model.Role;
import com.javaSecurity.JWTAuthorize.repository.RoleRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	RoleRepository roleRepository;

	@Test
	public void testCreateRoles() {
		Role admin =new Role("ROLE_ADMIN");
		Role editor=new Role("ROLE_EDITOR");
		Role customer=new Role("ROLE_CUSTOMER");
		
		roleRepository.save(admin);
		roleRepository.save(editor);
		roleRepository.save(customer);
			
		long numberOfRoles= roleRepository.count();
		assertEquals(3, numberOfRoles);
	}
	@Test
	public void testListRole() {
		List<Role> listRoles= roleRepository.findAll();
		assertThat(listRoles.size()).isGreaterThan(0);
		listRoles.forEach(System.out::println);
	}

}

