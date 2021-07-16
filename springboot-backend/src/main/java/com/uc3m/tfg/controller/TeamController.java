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

import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.service.TaskService;
import com.uc3m.tfg.service.TeamService;
import com.uc3m.tfg.service.UserService;


@RestController
@RequestMapping("/api/teams")
public class TeamController {

	@Autowired
	private TeamService teamService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;
	
	// Get all teams
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public List<Team> getAllGroups(){
		List<Team> teams = StreamSupport
				.stream(teamService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return teams;
	}
	
	// Create team
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/{task}")
	public ResponseEntity<?> createTeam(@RequestBody Team team, @PathVariable Long task) {
		if(!taskService.findById(task).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search task
		Optional<Task> oTask = taskService.findById(task);
				
		oTask.get().addTeam(team);	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(teamService.save(team));
	}
	
	// Get team by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<?> getTeamById(@PathVariable Long id) {
		
		Optional<Team> oTeam = teamService.findById(id);
		
		if(!oTeam.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oTeam);
	}
	
	// Update team
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody Team teamDetails){
		Optional<Team> team = teamService.findById(id);
		
		if(!team.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails,user.get());
		team.get().setUsers(teamDetails.getUsers());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(teamService.save(team.get()));
	}
	
	// Delete team
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTeam(@PathVariable Long id){
		if(!teamService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		teamService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	// Add an user to a team
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/add/{id}/{user}")
	public ResponseEntity<Map<String, Boolean>> addTeamUser(@PathVariable Long id, @PathVariable Long user){
		if(!userService.findById(user).isPresent() || !teamService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search group
		Optional<Team> team = teamService.findById(id);
		Optional<User> oUser = userService.findById(user);		
		
		team.get().getUsers().add(oUser.get());	
		teamService.save(team.get());
		
		return ResponseEntity.ok().build();
	}
	
	
	// Remove an user from a team
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/remove/{id}/{user}")
	public ResponseEntity<Map<String, Boolean>> removeTeamUser(@PathVariable Long id, @PathVariable Long user){
		if(!userService.findById(id).isPresent() || !teamService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search group
		Optional<Team> team = teamService.findById(id);
		Optional<User> oUser = userService.findById(user);		

		team.get().removeUser(oUser.get());
		teamService.save(team.get());
		
		return ResponseEntity.ok().build();
	}	

	
}