package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.ScheduleTime;

public interface ScheduleTimeService {
	
	public Iterable<ScheduleTime> findAll();
	
	public Page<ScheduleTime> findAll(Pageable pageable);
	
	public Optional<ScheduleTime> findById(Long id);
	
	public ScheduleTime save(ScheduleTime scheduleTime);
	
	public void deleteById(Long id);	
}
