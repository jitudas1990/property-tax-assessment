package com.property.tax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.property.tax.dto.ZonalReportDTO;
import com.property.tax.repository.PropertyRepository;
import com.property.tax.repository.TaxRepository;
import com.property.tax.repository.ZoneRepository;
@Controller
public class HomeController {
	    @Autowired
	    private ZoneRepository zoneRepository;
	    @Autowired
	    private PropertyRepository propertyDescriptionRepository;
	    @Autowired
	    private TaxRepository taxDetailsRepository;

	    @GetMapping("/")
	    public String home() {
	        return "home";
	    }

	    @GetMapping("/selfAssessmentForm")
	    public String selfAssessmentForm(Model model, RedirectAttributes redirectAttributes) {
	        try {
	            model.addAttribute("zones", zoneRepository.findAll());
	            model.addAttribute("propertyDescriptions", propertyDescriptionRepository.findAll());
	            return "selfAssessmentForm";
	        } catch (Exception ex) {
	            redirectAttributes.addFlashAttribute("message", "Error loading self-assessment form: " + ex.getMessage());
	            return "redirect:/";
	        }
	    }

	    @GetMapping("/zonalReport")
	    public String zonalReport(Model model, RedirectAttributes redirectAttributes) {
	        try {
	            List<ZonalReportDTO> zonalReport = taxDetailsRepository.findZonalReportForYear2014();
	            model.addAttribute("zonalReport", zonalReport);
	            return "zonalReport";
	        } catch (Exception ex) {
	            redirectAttributes.addFlashAttribute("message", "Error loading zonal report: " + ex.getMessage());
	            return "redirect:/";
	        }
	    }
}
