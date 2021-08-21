package com.uc3m.tfg.controller;

import java.time.LocalDateTime;
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
import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.service.CommentService;
import com.uc3m.tfg.service.GroupService;
import com.uc3m.tfg.service.ResponseStatementService;
import com.uc3m.tfg.service.TaskService;
import com.uc3m.tfg.service.TeamService;
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
	@Autowired
	private TeamService teamService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private GroupService groupService;
	
	// Get all comments
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public List<Comment> getAllComments(){
		List<Comment> comment = StreamSupport
				.stream(commentService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return comment;
	}
	
	// Create comment individual
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/{user}/{idTask}")
	public ResponseEntity<?> createComment(@RequestBody Comment comment, @PathVariable Long user,  @PathVariable Long idTask) {
		
		// Search task and group
		Optional<Task> oTask = taskService.findById(idTask);
		Optional<User> oUser = userService.findById(user);
		
		List<Comment> myComments =  StreamSupport
				.stream(commentService.findByTaskAndUser(oTask.get(), oUser.get()).spliterator(), false)
				.collect(Collectors.toList());
				
		// Get list of responses
		List<ResponseStatement> responses = StreamSupport
				.stream(responseStatementService.findByTask(oTask.get()).spliterator(), false)
				.collect(Collectors.toList());
		int responseChoosen = 0;
		int smallerSize = 99;
		boolean asigned = false;
		int commentCompleted = 0;
		int commentCompletedTemp = 0;
		
		// Change initial values
		do {		
			if(responses.get(responseChoosen).getId() == responseStatementService.findByTaskAndUser(oTask.get(), oUser.get()).get().getId())
				responseChoosen++;
			for (int w = 0; w < myComments.size(); w++) {
				if(responseStatementService.findByComments(myComments.get(w)).get().getId() == responses.get(responseChoosen).getId())
					responseChoosen++;				
			}
		} while(responses.get(responseChoosen).getId() == responseStatementService.findByTaskAndUser(oTask.get(), oUser.get()).get().getId());
		
		// Compare user responses
		for (int i = 0; i < responses.size(); i++) {
			asigned = false;
			
			if(responses.get(i).getId() == responseStatementService.findByTaskAndUser(oTask.get(), oUser.get()).get().getId())
				asigned = true;	
						
			// List of comments
			for (int w = 0; w < myComments.size(); w++) {
				if(responseStatementService.findByComments(myComments.get(w)).get().getId() == responses.get(i).getId())
					asigned = true;				
			}
			
			// Compare responses asigned
			if(!asigned){
				// Select less completed
				if (responses.get(i).getComments().size() < responses.get(responseChoosen).getComments().size()) {
					responseChoosen = i;
					smallerSize = responses.get(i).getComments().size();
				}
			}
		}

		// Select less asigned		
		for (int i = 0; i < responses.size(); i++) {
			asigned = false;
			
			if(responses.get(i).getId() == responseStatementService.findByTaskAndUser(oTask.get(), oUser.get()).get().getId())
				asigned = true;
			
			for (int w = 0; w < myComments.size(); w++) {				
				if(responseStatementService.findByComments(myComments.get(w)).get().getId() == responses.get(i).getId())
					asigned = true;
			}
			
			// Compare responses asigned
			if(!asigned){
			// Select less completed
				if (responses.get(i).getComments().size() == smallerSize) {
					for (int j = 0; j < smallerSize; j++) { // revisar
						if (responses.get(i).getComments().get(j).getTime() != null){
							commentCompletedTemp++;
						}
					}
					if (commentCompletedTemp < commentCompleted) {
						responseChoosen = i;
						commentCompleted = commentCompletedTemp;
					}

				}
			}
		}
		
		
		//Assign el new comment
		Comment commentUser = new Comment();
		oUser.get().addComment(commentUser);
		responses.get(responseChoosen).addComment(commentUser);
		oTask.get().addComment(commentUser);

		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(commentUser));
	}
	
	// Create comment team
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/team/{team}/{idTask}")
	public ResponseEntity<?> createCommentTeam(@RequestBody Comment comment, @PathVariable Long team,  @PathVariable Long idTask) {
		// Search task and group
		Optional<Task> oTask = taskService.findById(idTask);
		Optional<Team> oTeam = teamService.findById(team);
		
		List<Comment> ourComments =  StreamSupport
				.stream(commentService.findByTaskAndTeam(oTask.get(), oTeam.get()).spliterator(), false)
				.collect(Collectors.toList());
				
		// Get list of responses
		List<ResponseStatement> responses = StreamSupport
				.stream(responseStatementService.findByTask(oTask.get()).spliterator(), false)
				.collect(Collectors.toList());
		int responseChoosen = 0;
		int smallerSize = 99;
		boolean asigned = false;
		int commentCompleted = 0;
		int commentCompletedTemp = 0;
		
		// Change initial values
		do {		
			if(responses.get(responseChoosen).getId() == responseStatementService.findByTeam(oTeam.get()).get().getId())
				responseChoosen++;
			for (int w = 0; w < ourComments.size(); w++) {
				if(responseStatementService.findByComments(ourComments.get(w)).get().getId() == responses.get(responseChoosen).getId())
					responseChoosen++;				
			}
		} while(responses.get(responseChoosen).getId() == responseStatementService.findByTeam(oTeam.get()).get().getId());
		
		
		
		// Compare team responses
		for (int i = 0; i < responses.size(); i++) {
			asigned = false;

			if(responses.get(i).getId() == responseStatementService.findByTeam(oTeam.get()).get().getId())
				asigned = true;	
						
			// List of comments
			for (int w = 0; w < ourComments.size(); w++) {
				if(responseStatementService.findByComments(ourComments.get(w)).get().getId() == responses.get(i).getId())
					asigned = true;				
			}
			
			// Compare responses asigned
			if(!asigned){
				// Select less completed
				if (responses.get(i).getComments().size() < responses.get(responseChoosen).getComments().size()) {
					responseChoosen = i;
					smallerSize = responses.get(i).getComments().size();
				}
			}
		}		

		
		// Select less asigned		
		for (int i = 0; i < responses.size(); i++) {
			asigned = false;
			
			if(responses.get(i).getId() == responseStatementService.findByTeam(oTeam.get()).get().getId())
				asigned = true;
			
			for (int w = 0; w < ourComments.size(); w++) {
				if(responseStatementService.findByComments(ourComments.get(w)).get().equals(responses.get(i)))
					asigned = true;
			}
			// Compare responses asigned
			if(!asigned){
				// Select less completed
				if (responses.get(i).getComments().size() == smallerSize) {
					for (int j = 0; j < smallerSize; j++) { 
						if (responses.get(i).getComments().get(j).getTime() != null){
							commentCompletedTemp++;
						}
					}
					if (commentCompletedTemp < commentCompleted) {
						responseChoosen = i;
						commentCompleted = commentCompletedTemp;
					}

				}
			}
		}
		
		//Assign el new comment
		Comment commentTeam = new Comment();
		oTeam.get().addComment(commentTeam);
		responses.get(responseChoosen).addComment(commentTeam);				
		oTask.get().addComment(commentTeam);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(comment));
	}
	
	// First comment individual
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/first/{idTask}")
	public ResponseEntity<?> firstComment(@PathVariable Long idTask) {


		// Search task and group
		Optional<Task> oTask = taskService.findById(idTask);
		Optional<Group> oGroup = groupService.findByTasks(oTask.get());
		
		// Get list of responses and users
		List<ResponseStatement> responses = StreamSupport
				.stream(responseStatementService.findByTask(oTask.get()).spliterator(), false)
				.collect(Collectors.toList());
		List<User> users = StreamSupport
				.stream(userService.findByGroupsAndTypeUser(oGroup.get(), 1).spliterator(), false)
				.collect(Collectors.toList());
		
		Comment comment = new Comment();
		for (int i = 0; i < responses.size(); i++) {
			if (responses.get(i).getId() != responseStatementService.findByTaskAndUser(oTask.get(), users.get(i)).get().getId()) {
				comment = new Comment();
				users.get(i).addComment(comment);
				responses.get(i).addComment(comment);
				oTask.get().addComment(comment);
			} else if (i < responses.size() - 1){
				comment = new Comment();
				users.get(i).addComment(comment);
				responses.get(i + 1).addComment(comment);
				oTask.get().addComment(comment);
				comment = new Comment();
				users.get(i + 1).addComment(comment);
				responses.get(i).addComment(comment);
				oTask.get().addComment(comment);
				i++;
			}else {
				users.get(i - 1).deleteComment(comment);
				responses.get(i - 1).deleteComment(comment);
				comment = new Comment();
				users.get(i).addComment(comment);
				responses.get(i - 1).addComment(comment);
				oTask.get().addComment(comment);
				comment = new Comment();
				users.get(i - 1).addComment(comment);
				responses.get(i).addComment(comment);
				oTask.get().addComment(comment);
				i++;
			}
			
			commentService.save(comment);
		}
		
		return new ResponseEntity<>("Comentarios individuales creados", HttpStatus.OK);
	}
	
	// First comment team
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/firstTeam/{idTask}")
	public ResponseEntity<?> firstCommentTeam(@PathVariable Long idTask) {
		
		// Search task and group
		Optional<Task> oTask = taskService.findById(idTask);
		
		// Get list of responses and users
		List<ResponseStatement> responses = StreamSupport
				.stream(responseStatementService.findByTask(oTask.get()).spliterator(), false)
				.collect(Collectors.toList());
		List<Team> teams = StreamSupport
				.stream(teamService.findByTask(oTask.get()).spliterator(), false)
				.collect(Collectors.toList());
		
		Comment comment = new Comment();
		for (int i = 0; i < responses.size(); i++) {			
			if (responses.get(i).getId() != responseStatementService.findByTeam(teams.get(i)).get().getId()) {
				comment = new Comment();
				teams.get(i).addComment(comment);
				responses.get(i).addComment(comment);
				oTask.get().addComment(comment);
			} else if (i < responses.size() - 1){
				comment = new Comment();
				teams.get(i).addComment(comment);
				responses.get(i + 1).addComment(comment);
				oTask.get().addComment(comment);
				comment = new Comment();
				teams.get(i + 1).addComment(comment);
				responses.get(i).addComment(comment);
				oTask.get().addComment(comment);
				i++;
			} else {
				teams.get(i - 1).deleteComment(comment);
				responses.get(i - 1).deleteComment(comment);
				comment = new Comment();
				teams.get(i).addComment(comment);
				responses.get(i - 1).addComment(comment);
				oTask.get().addComment(comment);
				comment = new Comment();
				teams.get(i - 1).addComment(comment);
				responses.get(i).addComment(comment);
				oTask.get().addComment(comment);
				i++;
			}
		}
		
		return new ResponseEntity<>("Comentarios de equipo creados", HttpStatus.OK);
	}
		
	// Get comment by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<?> getCommentById(@PathVariable Long id) {
		
		Optional<Comment> oComment = commentService.findById(id);
		
		if(!oComment.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oComment);
	}
	
	// Get comment by TaskAndUser
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/TaskAndUser/{idTask}/{idUser}")
	public List<Comment> getCommentByTaskAndUser(@PathVariable Long idTask, @PathVariable Long idUser) {
		
		Optional<Task> oTask = taskService.findById(idTask);
		Optional<User> oUser = userService.findById(idUser);
		
		List<Comment> comments =  StreamSupport
				.stream(commentService.findByTaskAndUser(oTask.get(), oUser.get()).spliterator(), false)
				.collect(Collectors.toList());		
		
		return comments;
	}
	
	// Get comment by TaskAndTeam
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/TaskAndTeam/{idTask}/{idTeam}")
	public List<Comment> getCommentByTaskAndTeam(@PathVariable Long idTask, @PathVariable Long idTeam) {
		Optional<Task> oTask = taskService.findById(idTask);
		Optional<Team> oTeam = teamService.findById(idTeam);
		
		List<Comment> comments =  StreamSupport
				.stream(commentService.findByTaskAndTeam(oTask.get(), oTeam.get()).spliterator(), false)
				.collect(Collectors.toList());		
		
		return comments;
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
		comment.get().setTime(LocalDateTime.now());
		
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