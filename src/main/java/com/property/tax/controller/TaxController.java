package com.property.tax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.property.tax.entity.TaxDetails;
import com.property.tax.repository.TaxRepository;

import jakarta.validation.Valid;

@Controller
public class TaxController {
	@Autowired
    private TaxRepository taxDetailsRepository;

    @PostMapping("/submitTaxDetails")
    public String submitTaxDetails(@Valid TaxDetails taxDetails, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("message", "Error: Please check your form input.");
                return "redirect:/";
            }

            taxDetails.setTotalTax(calculateTax(taxDetails));
            taxDetailsRepository.save(taxDetails);

            redirectAttributes.addFlashAttribute("message", "Tax details are saved successfully");
            return "redirect:/";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "An error occurred while submitting tax details: " + ex.getMessage());
            return "redirect:/error";
        }
    }

    private double calculateTax(TaxDetails taxDetails) {
        double taxRate = 10; // Default tax rate
        if ("1".equals(taxDetails.getZoneClassification())) taxRate += 5; // Adjust based on zone
        if ("RCC".equals(taxDetails.getPropertyDescription())) taxRate += 2; // Adjust based on description
        return taxDetails.getBuiltUpArea() * taxRate;
    }
}
