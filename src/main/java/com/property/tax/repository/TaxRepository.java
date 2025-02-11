package com.property.tax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.property.tax.dto.ZonalReportDTO;
import com.property.tax.entity.TaxDetails;

public interface TaxRepository extends JpaRepository<TaxDetails, Long>{
	

	@Query("SELECT new com.property.tax.dto.ZonalReportDTO(z.name, t.status, SUM(t.totalTax)) " +
	           "FROM TaxDetails t " +
	           "JOIN t.zone z " +
	           "WHERE t.yearOfAssessment = '2014' " +
	           "GROUP BY z.name, t.status")
	    List<ZonalReportDTO> findZonalReportForYear2014();
}
