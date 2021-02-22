package com.uc3m.tfg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long>{

}
