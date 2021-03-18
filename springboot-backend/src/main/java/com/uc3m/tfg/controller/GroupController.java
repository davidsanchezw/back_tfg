package com.uc3m.tfg.controller;

import java.util.HashMap;
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

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.service.GroupService;


@RestController
@RequestMapping("/api/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	// Get all groups
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public List<Group> getAllGroups(){
		List<Group> groups = StreamSupport
				.stream(groupService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return groups;
	}
	
	// Create group
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public ResponseEntity<?> createGroup(@RequestBody Group group) {
		return ResponseEntity.status(HttpStatus.CREATED).body(groupService.save(group));
	}
	
	// Get group by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<?> getGroupById(@PathVariable Long id) {
		
		Optional<Group> oGroup = groupService.findById(id);
		
		if(!oGroup.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oGroup);
	}
	
	// Update group
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody Group groupDetails){
		Optional<Group> group = groupService.findById(id);
		
		if(!group.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails,user.get());
		group.get().setName(groupDetails.getName());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(groupService.save(group.get()));
	}
	
	// Delete group
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteGroup(@PathVariable Long id){
		if(!groupService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		groupService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}