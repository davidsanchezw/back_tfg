
package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.Team;

public interface TeamService {
	
	public Iterable<Team> findAll();
	
	public Page<Team> findAll(Pageable pageable);
	
	public Optional<Team> findById(Long id);
	
	public Team save(Team team);
	
	public void deleteById(Long id);	
	
}
