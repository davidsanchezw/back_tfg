package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.ScheduleTime;
import com.uc3m.tfg.repository.ScheduleTimeRepository;

@Service
public class ScheduleTimeServiceImpl implements ScheduleTimeService{

	@Autowired
	private ScheduleTimeRepository scheduleTimeRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<ScheduleTime> findAll() {
		return scheduleTimeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ScheduleTime> findAll(Pageable pageable) {
		return scheduleTimeRepository.findAll(pageable);
	}

	@Override
	public Optional<ScheduleTime> findById(Long id) {
		return scheduleTimeRepository.findById(id);
	}

	@Override
	public ScheduleTime save(ScheduleTime scheduleTime) {
		return scheduleTimeRepository.save(scheduleTime);
	}

	@Override
	public void deleteById(Long id) {
		scheduleTimeRepository.deleteById(id);
	}
	
}
