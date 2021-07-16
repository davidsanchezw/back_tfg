package com.uc3m.tfg.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.Team;
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
	public Team save(Team team) {
		return teamRepository.save(team);
	}

	@Override
	public void deleteById(Long id) {
		teamRepository.deleteById(id);
	}
	

	
}
