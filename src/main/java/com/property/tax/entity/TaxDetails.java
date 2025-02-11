package com.property.tax.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TaxDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotNull
    private Integer yearOfAssessment;

    @NotBlank
    private String ownerName;

    
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String zoneClassification;
    
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @NotBlank
    private String propertyDescription;

    @NotBlank
    private String status;

    @NotNull
    private Integer constructedYear;

    @NotNull
    private Integer builtUpArea;

    private Double totalTax;
}
