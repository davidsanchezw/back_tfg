package com.uc3m.tfg.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uc3m.tfg.model.Comment;
import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.service.CommentService;
import com.uc3m.tfg.service.ResponseStatementService;
import com.uc3m.tfg.service.UserService;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private ResponseStatementService responseStatementService;
	@Autowired
	private UserService userService;
	
	// Get all comments
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public List<Comment> getAllComments(){
		List<Comment> comment = StreamSupport
				.stream(commentService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return comment;
	}
	
	// Create comment
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/{user}/{response}")
	public ResponseEntity<?> createComment(@RequestBody Comment comment, @PathVariable Long user,  @PathVariable Long response) {
		if(!userService.findById(user).isPresent() || !responseStatementService.findById(response).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search user and response
		Optional<User> oUser = userService.findById(user);
		Optional<ResponseStatement> oResponse = responseStatementService.findById(response);
				
		oUser.get().addComment(comment);
		oResponse.get().addComment(comment);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(comment));
	}
		
	// Get team by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<?> getCommentById(@PathVariable Long id) {
		
		Optional<Comment> oComment = commentService.findById(id);
		
		if(!oComment.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oComment);
	}
	
	// Update comment
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails){
		Optional<Comment> comment = commentService.findById(id);
		
		if(!comment.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails,user.get());
		comment.get().setCommentText(commentDetails.getCommentText());
		comment.get().setCalificationResponse(commentDetails.getCalificationResponse());
		comment.get().setCalificationCommentator(commentDetails.getCalificationCommentator());
		comment.get().setTime(commentDetails.getTime());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(comment.get()));
	}
	
	// Delete comment
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteComment(@PathVariable Long id){
		if(!commentService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		commentService.deleteById(id);
		return ResponseEntity.ok().build();
	}
		
}