package com.uc3m.tfg.controller;

import java.util.ArrayList;
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
	@PostMapping("/{task}/{numberTeam}")
	public ResponseEntity<?> createTeams(@PathVariable Long task, @PathVariable int numberTeam) {
		if(!taskService.findById(task).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search task
		Optional<Task> oTask = taskService.findById(task);
				
		//get Id task
		System.out.println(oTask.get().getGroup().getName());
		//Get alumnos con grupo y rol
		List<User> students = StreamSupport
				.stream(userService.findByGroupsAndTypeUser(oTask.get().getGroup(), 1).spliterator(), false)
				.collect(Collectors.toList());		
        
            

		// Ordenar lista por nota
		//Obtener num de grupos teams necesarios
		int numGrops = students.size() / (int)numberTeam;
		if (students.size() % (int)numberTeam != 0) numGrops++;
		
		//añadir alumnos
		int w = 0;
        int max = w + numberTeam;
		Team teamTemp = new Team();
		List<Team> listTeam = new ArrayList<Team>();
		
		//Ordenar primeros con ultimos
		List<User> temp = new ArrayList<User>();
        int last = students.size();
        for (int i=0; i < students.size() && i < last; i++){
            temp.add(students.get(i));
            last--;
            if (i < last) temp.add(students.get(last));
        }        
        students = temp;
		
		// Añade alumnos a los teams
		for(int j = 0; j < numGrops; j++) {
			teamTemp = new Team();
            teamTemp.addList(students.subList(w, max)); 
            teamTemp.setTask(oTask.get());
            listTeam.add(teamTemp);

			w = max;
			 
			if (max + numberTeam < students.size()) max = max + numberTeam;
			else max = students.size();
		}
		;
		//oTask.get().addTeam(team);	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(teamService.saveAll(listTeam));
	}
	
	/*
	 * List<String> lista = new ArrayList();
        lista.add("a");
        lista.add("b");
        lista.add("c");
        lista.add("d");
        lista.add("e");
        lista.add("f");
        int numTeam = 3;
        int numGrops = 2;
        List<List<String>> lista2 = new ArrayList();
        int w = 0;
        int max = w + numTeam;
        List<String> listTemp;
        
        List<String> temp = new ArrayList();
        int last = lista.size();
        for (int i=0; i < lista.size() && i < last; i++){
            temp.add(lista.get(i));
            last--;
            if (i < last) temp.add(lista.get(last));
        }
        
        lista = temp;
        System.out.println(lista);
        
        HECHO DESDE AQUI
        for(int j = 0; j < numGrops; j++) {
            listTemp = lista.subList(w, max); 
			lista2.add(listTemp);
			w = max;
			 
			if (max + numTeam < lista.size()) max = max + numTeam;
			else max = lista.size();
		}
		
        System.out.println(lista2);
	 */
	
	
	
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
	
	// Get team by task
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping("/TeamsByTask/{idTask}")
		public List<Team> getTeamsByTask(@PathVariable Long idTask) {
			
			Optional<Task> oTask = taskService.findById(idTask);			
			List<Team> teams = StreamSupport
					.stream(teamService.findByTask(oTask.get()).spliterator(), false)
					.collect(Collectors.toList());				
			return teams;
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