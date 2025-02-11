package com.property.tax.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ZonalReportDTO {
	private String zoneName;
    private String propertyType;
    private double amountCollected;

    public ZonalReportDTO(String zoneName, String propertyType, double amountCollected) {
        this.zoneName = zoneName;
        this.propertyType = propertyType;
        this.amountCollected = amountCollected;
    }

}
