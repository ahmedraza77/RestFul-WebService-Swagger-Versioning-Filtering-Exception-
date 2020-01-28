package com.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservices.restfulwebservices.post.*;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;


	//Retrieve all Users - GET /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}


	//Retrieve one User - GET /users/{id} -> /users/1
	@GetMapping("/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user= userRepository.findById(id);

		if(!user.isPresent()) {
			throw new UserNotFoundException("Id : " + id);
		}

		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));

		return resource;
	}


	//Delete a User - DELETE /users/{id} -> /users/1
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		Optional<User> user= userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("Id : " + id);
		}

		userRepository.deleteById(id);
	}


	//Create a User - POST /users
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}



	//Retrieve all posts for a User - GET /users/{id}/posts
	@GetMapping("/users/{id}/posts")
	public List<Post> AllPostsByUser(@PathVariable int id){
		Optional<User> user=userRepository.findById(id);

		if(!user.isPresent()) {
			throw new UserNotFoundException("ID: "+id);
		}

		return user.get().getPost();
	}



	//Create a posts for a User - POST /users/{id}/posts
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> createUserPost(@PathVariable int id, @Valid @RequestBody Post post) {

		Optional<User> user= userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("ID: "+id);
		}

		User savedUser = user.get();
		post.setUser(savedUser);
		postRepository.save(post);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{id}").buildAndExpand(post.getId()).toUri();

		return ResponseEntity.created(location).build();
	} 



	//Retrieve details of a post only if it belongs to that User - GET /users/{id}/posts/{post_id}
	
//	@GetMapping("/users/{id}/posts/{post_id}")
//	public Post getPostByUser(@PathVariable("id") int id, @PathVariable("post_id") int postId) {
//
//		Optional<Post> optionalPost = postRepository.findByPostIdAndUserId(id, postId);
//
//		if(!optionalPost.isPresent()) {
//			throw new PostNotFoundException("User not present, ID: "+id);
//		}
//		
//		return optionalPost.get();
//
//	}

}
