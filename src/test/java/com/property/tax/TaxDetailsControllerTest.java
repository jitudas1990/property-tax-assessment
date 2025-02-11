package com.property.tax;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.property.tax.controller.TaxController;
import com.property.tax.entity.TaxDetails;
import com.property.tax.repository.TaxRepository;

//@RunWith(SpringRunner.class)
@WebMvcTest(TaxController.class)
public class TaxDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxRepository taxDetailsRepository;

    @MockBean
    private BindingResult bindingResult;

    @MockBean
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {
        // Set up any common configuration or mocks
    }

    @Test
    public void testSubmitTaxDetailsSuccess() throws Exception {
        TaxDetails taxDetails = new TaxDetails();
        //when(bindingResult.hasErrors()).thenReturn(false);
        when(taxDetailsRepository.save(any(TaxDetails.class))).thenReturn(taxDetails);

        mockMvc.perform(post("/submitTaxDetails")
                .flashAttr("taxDetails", taxDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("message", "Tax details are saved successfully"));

        verify(taxDetailsRepository, times(1)).save(any(TaxDetails.class));
    }

    @Test
    public void testSubmitTaxDetailsValidationError() throws Exception {
        TaxDetails taxDetails = new TaxDetails();
      //  when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/submitTaxDetails")
                .flashAttr("taxDetails", taxDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("message", "Error: Please check your form input."));

        verify(taxDetailsRepository, times(0)).save(any(TaxDetails.class));
    }

    @Test
    public void testSubmitTaxDetailsException() throws Exception {
        TaxDetails taxDetails = new TaxDetails();
        //when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Test Exception")).when(taxDetailsRepository).save(any(TaxDetails.class));

        mockMvc.perform(post("/submitTaxDetails")
                .flashAttr("taxDetails", taxDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
                .andExpect(flash().attribute("message", "An error occurred while submitting tax details: Test Exception"));

        verify(taxDetailsRepository, times(1)).save(any(TaxDetails.class));
    }
}