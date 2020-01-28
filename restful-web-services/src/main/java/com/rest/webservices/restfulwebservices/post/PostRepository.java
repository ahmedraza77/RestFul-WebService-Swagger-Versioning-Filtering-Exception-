package com.rest.webservices.restfulwebservices.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	@Query("SELECT p FROM POST p where p.user_id = :id AND p.id = :postId")
	public Optional<Post> findByPostIdAndUserId(@Param("id") Integer id, @Param("postId") Integer postId);
	
}
