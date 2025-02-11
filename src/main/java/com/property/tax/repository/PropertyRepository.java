package com.property.tax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.property.tax.entity.PropertyDescription;

public interface PropertyRepository extends JpaRepository<PropertyDescription, Long>{

}
