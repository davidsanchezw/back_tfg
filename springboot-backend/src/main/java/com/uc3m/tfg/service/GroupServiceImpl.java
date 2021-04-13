package com.uc3m.tfg.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.repository.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	private GroupRepository groupRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Group> findAll() {
		return groupRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Group> findAll(Pageable pageable) {
		return groupRepository.findAll(pageable);
	}

	@Override
	public Optional<Group> findById(Long id) {
		return groupRepository.findById(id);
	}

	@Override
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	@Override
	public void deleteById(Long id) {
		groupRepository.deleteById(id);
	}
	
}
