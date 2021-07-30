package com.uc3m.tfg.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.*;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.service.GroupService;
import com.uc3m.tfg.service.UserService;


@RestController
@RequestMapping("/api/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	
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
		if (groupService.findByGroupName(group.getName()).isPresent())
			return ResponseEntity.status(HttpStatus.FOUND).body(groupService.findByGroupName(group.getName()));
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
	
	// Add users file - Import excel
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/add/{id}")
	public ResponseEntity<List<User>> importExcelFile(@PathVariable Long id, @RequestParam("file") MultipartFile files) throws IOException {
        HttpStatus status = HttpStatus.OK;
        List<User> userList = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        
        Group group = groupService.findById(id).get();
		List<Group> listGroups = new ArrayList<>();
		listGroups.add(group);
		
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
            	User user = new User();

                XSSFRow row = worksheet.getRow(index);

                user.setFirstName(row.getCell(0).getStringCellValue());
                user.setLastName(row.getCell(1).getStringCellValue());
                user.setEmail(row.getCell(2).getStringCellValue());
                user.setTypeUser((int)row.getCell(3).getNumericCellValue());
                
                group.addUser(user);
                userService.save(user);                
                userList.add(user);
            }
        }

        return new ResponseEntity<>(userList, status);
    }
	
	// Add an user to a group
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/add/{id}/{user}")
	public ResponseEntity<Map<String, Boolean>> addGroupUser(@PathVariable Long id, @PathVariable Long user){
		if(!userService.findById(user).isPresent() || !groupService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search group
		Optional<Group> group = groupService.findById(id);
		Optional<User> oUser = userService.findById(user);		
		
		group.get().getUsers().add(oUser.get());	
		groupService.save(group.get());
		
		return ResponseEntity.ok().build();
	}
	
	
	// Remove an user from a group
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/remove/{id}/{user}")
	public ResponseEntity<Map<String, Boolean>> removeGroupUser(@PathVariable Long id, @PathVariable Long user){
		if(!userService.findById(id).isPresent() || !groupService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search group
		Optional<Group> group = groupService.findById(id);
		Optional<User> oUser = userService.findById(user);		

		group.get().removeUser(oUser.get());
		groupService.save(group.get());
		
		return ResponseEntity.ok().build();
	}	

	// Get groups by user
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/listByUserID/{id}")
	public List<Group> getGroupsByUserId(@PathVariable Long id){
		Optional<User> user = userService.findById(id);
		List<Group> groups = StreamSupport
				.stream(groupService.findByUser(user.get()).spliterator(), false)
				.collect(Collectors.toList());
		return groups;
	}
	
}