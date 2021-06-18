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
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.ScheduleTime;
import com.uc3m.tfg.service.ScheduleTimeService;
import com.uc3m.tfg.service.TaskService;

@RestController
@RequestMapping("/api/times")
public class ScheduleTimeController {
	
	@Autowired	
	private ScheduleTimeService scheduleTimeService;
	@Autowired	
	private TaskService taskService;

	// Get all times
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping
		public List<ScheduleTime> getAllTimes(){
			List<ScheduleTime> times = StreamSupport
					.stream(scheduleTimeService.findAll().spliterator(), false)
					.collect(Collectors.toList());
			return times;
		}
		
		// Create time
		@CrossOrigin(origins = "http://localhost:4200")
		@PostMapping("/{task}")
		public ResponseEntity<?> createScheduleTime(@RequestBody ScheduleTime times, @PathVariable Long task) {
			if(!taskService.findById(task).isPresent()) {
				return ResponseEntity.notFound().build();
			}
			// Search task
			Optional<Task> oTask = taskService.findById(task);
					
			oTask.get().setScheduleTime(times);	
			
			return ResponseEntity.status(HttpStatus.CREATED).body(scheduleTimeService.save(times));
		}
		
		// Get time by id
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping("/{id}")
		public ResponseEntity<?> getScheduleTimeById(@PathVariable Long id) {
			
			Optional<ScheduleTime> oScheduleTime = scheduleTimeService.findById(id);
			
			if(!oScheduleTime.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(oScheduleTime);
		}
		
		// Update time
		@CrossOrigin(origins = "http://localhost:4200")
		@PutMapping("/{id}")
		public ResponseEntity<?> updateScheduleTime(@PathVariable Long id, @RequestBody ScheduleTime timeDetails){
			Optional<ScheduleTime> time = scheduleTimeService.findById(id);
			
			if(!time.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			
			//BeanUtils.copyProperties(userDetails,user.get());
			time.get().setPresentation(timeDetails.getPresentation());
			time.get().setRevision(timeDetails.getRevision());
			time.get().setFinalPresentation(timeDetails.getFinalPresentation());
			time.get().setFinalRevision(timeDetails.getFinalRevision());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(scheduleTimeService.save(time.get()));
		}
		
		// Delete time
		@CrossOrigin(origins = "http://localhost:4200")
		@DeleteMapping("/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteScheduleTime(@PathVariable Long id){
			if(!scheduleTimeService.findById(id).isPresent()) {
				return ResponseEntity.notFound().build();
			}
			
			// Search group
			Optional<ScheduleTime> time = scheduleTimeService.findById(id);

			time.get().getTask().setScheduleTime(null);
			
			scheduleTimeService.deleteById(id);
			return ResponseEntity.ok().build();
		}
}
