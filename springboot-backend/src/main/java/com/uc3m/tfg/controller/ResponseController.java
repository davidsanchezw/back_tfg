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
import com.uc3m.tfg.model.ResponseAnswer;
import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.service.CommentService;
import com.uc3m.tfg.service.ResponseAnswerService;
import com.uc3m.tfg.service.ResponseStatementService;
import com.uc3m.tfg.service.TaskService;
import com.uc3m.tfg.service.TeamService;
import com.uc3m.tfg.service.UserService;


@RestController
@RequestMapping("/api/response")
public class ResponseController {

	@Autowired
	private ResponseStatementService responseStatementService;
	@Autowired
	private ResponseAnswerService responseAnswerService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	
	// Get all ResponseStatements
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/statements")
	public List<ResponseStatement> getAllResponseStatements(){
		List<ResponseStatement> responseStatement = StreamSupport
				.stream(responseStatementService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return responseStatement;
	}
	
	// Get all ResponseAnswers
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping("/answers")
		public List<ResponseAnswer> getAllResponseAnswers(){
			List<ResponseAnswer> responseAnswer = StreamSupport
					.stream(responseAnswerService.findAll().spliterator(), false)
					.collect(Collectors.toList());
			return responseAnswer;
		}
	
	
	// Get ResponseStatements by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/statements/{id}")
	public ResponseEntity<?> getResponseStatementById(@PathVariable Long id) {
		
		Optional<ResponseStatement> oResponseStatement = responseStatementService.findById(id);
		
		if(!oResponseStatement.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oResponseStatement);
	}
	
	// Get ResponseAnswer by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/answers/{id}")
	public ResponseEntity<?> getResponseAnswerById(@PathVariable Long id) {
		
		Optional<ResponseAnswer> oResponseAnswer = responseAnswerService.findById(id);
		
		if(!oResponseAnswer.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oResponseAnswer);
	}
	
	// Create ResponseStatements
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/statements/{task}/{user}")
	public ResponseEntity<?> createResponseStatements(@RequestBody ResponseStatement responseStatements, @PathVariable Long task, @PathVariable Long user) {
		if(!userService.findById(user).isPresent() || !taskService.findById(task).isPresent()) {
			return ResponseEntity.notFound().build();
			
		}		
		// Search task
				Optional<Task> oTask = taskService.findById(task);
				
				oTask.get().addStatement(responseStatements);	
		
		// Search User
				Optional<User> oUser = userService.findById(user);
				
				oUser.get().addStatement(responseStatements);	
				
		return ResponseEntity.status(HttpStatus.CREATED).body(responseStatementService.save(responseStatements));
	}
	
	// Create ResponseStatements Team
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/statements/team/{team}")
	public ResponseEntity<?> createResponseStatementsTeam(@RequestBody ResponseStatement responseStatements, @PathVariable Long team) {
		if(!teamService.findById(team).isPresent()) {
			return ResponseEntity.notFound().build();
			
		}		
		// Search team
				Optional<Team> oTeam = teamService.findById(team);
				
				oTeam.get().setResponseStatement(responseStatements);			
		
				
		return ResponseEntity.status(HttpStatus.CREATED).body(responseStatementService.save(responseStatements));
	}
		
	// Create ResponseAnswer
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/answers/{statement}")
	public ResponseEntity<?> createResponseAnswer(@RequestBody ResponseAnswer responseAnswer, @PathVariable Long statement) {
		if(!responseStatementService.findById(statement).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search ResponseStatements
				Optional<ResponseStatement> oResponseStatement = responseStatementService.findById(statement);
				
				oResponseStatement.get().addAnswer(responseAnswer);	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseAnswerService.save(responseAnswer));
	}
	
	// Update ResponseStatements
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/statements/{id}")
	public ResponseEntity<?> updateResponseStatement(@PathVariable Long id, @RequestBody ResponseStatement responseStatementDetails){
		Optional<ResponseStatement> responseStatements = responseStatementService.findById(id);
		
		if(!responseStatements.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails,user.get());
		responseStatements.get().setLastTime(responseStatementDetails.getLastTime());
		responseStatements.get().setStatement(responseStatementDetails.getStatement());

		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseStatementService.save(responseStatements.get()));
	}
	
	// Update ResponsAnswers
		@CrossOrigin(origins = "http://localhost:4200")
		@PutMapping("/answers/{id}")
		public ResponseEntity<?> updateResponseAnswer(@PathVariable Long id, @RequestBody ResponseAnswer responseAnswerDetails){
			Optional<ResponseAnswer> responseAnswer = responseAnswerService.findById(id);
			
			if(!responseAnswer.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			
			//BeanUtils.copyProperties(userDetails,user.get());
			responseAnswer.get().setCorrect(responseAnswerDetails.isCorrect());
			responseAnswer.get().setAnswer(responseAnswerDetails.getAnswer());

			
			return ResponseEntity.status(HttpStatus.CREATED).body(responseAnswerService.save(responseAnswer.get()));
		}
	
	// Delete ResponseStatements
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/statements/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteResponseStatement(@PathVariable Long id){
		if(!responseStatementService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		responseStatementService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	// Delete ResponseAnswer
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/answers/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteResponseAnswer(@PathVariable Long id){
		if(!responseAnswerService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		responseAnswerService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	//getResponseByTaskAndUser		
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{idTask}/{idUser}")
	public ResponseEntity<?> getResponseByTaskAndUser(@PathVariable Long idTask, @PathVariable Long idUser) {
		Optional<Task> task = taskService.findById(idTask);
		Optional<User> user = userService.findById(idUser);
		
		Optional<ResponseStatement> oResponseStatement = responseStatementService.findByTaskAndUser(task.get(), user.get());
					
		
		return ResponseEntity.ok(oResponseStatement);
	}
	
	//getResponseByTeam	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/team/{idTeam}")
	public ResponseEntity<?> getResponseByTeam(@PathVariable Long idTeam) {
		Optional<Team> team = teamService.findById(idTeam);
		
		Optional<ResponseStatement> oResponseStatement = responseStatementService.findByTeam(team.get());
		
		return ResponseEntity.ok(oResponseStatement);
	}
	
	//getResponsesByTask	
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping("/task/{idTask}")
		public List<ResponseStatement> getResponseByTask(@PathVariable Long idTask) {
			Optional<Task> task = taskService.findById(idTask);
			
			List<ResponseStatement> responses = StreamSupport
					.stream(responseStatementService.findByTask(task.get()).spliterator(), false)
					.collect(Collectors.toList());
			return responses;
		}
	
	//getResponseByComment
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping("/comment/{idComment}")
		public ResponseEntity<?> getResponseByComment(@PathVariable Long idComment) {
			Optional<Comment> comment = commentService.findById(idComment);
			
			Optional<ResponseStatement> oResponseStatement = responseStatementService.findByComments(comment.get());
			
			return ResponseEntity.ok(oResponseStatement);
		}
	
}