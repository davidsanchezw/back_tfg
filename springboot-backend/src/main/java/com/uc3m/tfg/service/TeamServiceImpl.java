package com.uc3m.tfg.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamRepository teamRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Team> findAll() {
		return teamRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Team> findAll(Pageable pageable) {
		return teamRepository.findAll(pageable);
	}

	@Override
	public Optional<Team> findById(Long id) {
		return teamRepository.findById(id);
	}
	
	@Override
	public Iterable<Team> findByTask(Task task){
		return teamRepository.findByTask(task);
	}
	
	@Override
	public Optional<Team> findByTaskAndUser(Task task, User user) {
		return teamRepository.findByTaskAndUsers(task, user);
	}

	@Override
	public Team save(Team team) {
		return teamRepository.save(team);
	}
	
	@Override
	public List<Team> saveAll(List<Team> teams) {
		return teamRepository.saveAll(teams);
	}

	@Override
	public void deleteById(Long id) {
		teamRepository.deleteById(id);
	}	
}
