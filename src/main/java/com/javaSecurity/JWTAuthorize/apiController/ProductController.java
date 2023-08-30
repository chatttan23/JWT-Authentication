package com.javaSecurity.JWTAuthorize.apiController;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaSecurity.JWTAuthorize.model.Product;
import com.javaSecurity.JWTAuthorize.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@GetMapping
	@RolesAllowed({"Role_CUSTOMER","ROLE_EDITOR","ROLE_ADMIN"})
	public List<Product> list() {
		return productRepository.findAll();
	}
	
	@PostMapping
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<Product> create(@RequestBody @Valid Product product){
		@Valid
		Product saveProduct =productRepository.save(product);
		URI productURI=URI.create("/products/"+saveProduct.getId());
		return ResponseEntity.created(productURI).body(saveProduct);
		
	}
}
