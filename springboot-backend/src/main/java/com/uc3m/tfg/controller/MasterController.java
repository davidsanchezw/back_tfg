package com.uc3m.tfg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.uc3m.tfg.exception.ResourceNotFoundException;
import com.uc3m.tfg.model.Master;
import com.uc3m.tfg.repository.MasterRepository;


@RestController
@RequestMapping("/api/v1/")
public class MasterController {

	@Autowired
	private MasterRepository masterRepository;
	
	//get all masters
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/masters")
	public List<Master> getAllMasters(){
		return masterRepository.findAll();
	}
	
	//create master restapi
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/masters")
	public Master createMaster(@RequestBody Master master) {
		return masterRepository.save(master);
	}
	
	// get master by id rest api
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/masters/{id}")
	public ResponseEntity<Master> getMasterById(@PathVariable Long id) {
		
		Master master = masterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Master not exist with id: " + id));
		return ResponseEntity.ok(master);
	}
	
	// update master rest api
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/masters/{id}")
	public ResponseEntity<Master> updateMaster(@PathVariable Long id, @RequestBody Master masterDetails){
		Master master = masterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Master not exist with id: " + id));
		master.setFirstName(masterDetails.getFirstName());
		master.setLastName(masterDetails.getLastName());
		master.setEmail(masterDetails.getEmail());
		Master updatedMaster = masterRepository.save(master);
		return ResponseEntity.ok(updatedMaster);
	}
	
	// delete master rest api
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/masters/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteMaster(@PathVariable Long id){
		Master master = masterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Master not exist with id: " + id));
		
		masterRepository.delete(master);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
