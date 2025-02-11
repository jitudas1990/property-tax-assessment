package com.property.tax;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.property.tax.entity.PropertyDescription;
import com.property.tax.entity.Zone;
import com.property.tax.repository.PropertyRepository;
import com.property.tax.repository.ZoneRepository;

@WebMvcTest(HomeControllerTest.class)
public class HomeControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ZoneRepository zoneRepository;

	@MockBean
	private PropertyRepository propertyDescriptionRepository;
	

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("error"));
	}

	@Test
	public void testSelfAssessmentFormSuccess() throws Exception {
		Zone zone = new Zone();
		zone.setId(1l);
		zone.setName("zone");
		List<Zone> list = new ArrayList<>();
		list.add(zone);
		PropertyDescription propertyDescription = new PropertyDescription();
		propertyDescription.setDescription("House");
		List<PropertyDescription> list1 = new ArrayList<>();
		list1.add(propertyDescription);
		when(zoneRepository.findAll()).thenReturn(list);
		when(propertyDescriptionRepository.findAll()).thenReturn(list1);
		mockMvc.perform(get("/selfAssessmentForm")).andExpect(status().isOk());
				
	}

	@Test
    public void testSelfAssessmentFormException() throws Exception {
		  when(zoneRepository.findAll()).thenThrow(new RuntimeException("Database error"));
	        mockMvc.perform(get("/selfAssessmentForm"))
	                .andExpect(status().is2xxSuccessful());
	                
	}
}
