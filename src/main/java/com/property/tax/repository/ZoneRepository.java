package com.property.tax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.property.tax.entity.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long>{

}
