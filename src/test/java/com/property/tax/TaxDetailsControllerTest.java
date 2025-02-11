import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class TaxDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TaxDetailsController taxDetailsController; // The controller you're testing

    @Mock
    private TaxDetailsRepository taxDetailsRepository; // Mocked dependency for saving data

    @Mock
    private BindingResult bindingResult; // Mocked BindingResult

    @Mock
    private RedirectAttributes redirectAttributes; // Mocked RedirectAttributes

    @Mock
    private TaxDetails taxDetails; // Mocked TaxDetails

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taxDetailsController).build();
    }

    @Test
    void testSubmitTaxDetails_Success() throws Exception {
        // Mock valid form submission
        when(bindingResult.hasErrors()).thenReturn(false);  // Simulate no validation errors
        when(taxDetailsRepository.save(any(TaxDetails.class))).thenReturn(taxDetails);  // Mock the save method
        when(taxDetails.getTotalTax()).thenReturn(1000.0);  // Mock the totalTax calculation

        // Perform the POST request
        mockMvc.perform(post("/submitTaxDetails")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("field1", "value1")  // Add actual form fields here
                        .param("field2", "value2"))
                .andExpect(status().is3xxRedirection())  // Expect redirection
                .andExpect(redirectedUrl("/"))  // Redirect to the home page
                .andExpect(flash().attribute("message", "Tax details are saved successfully"));
    }

    @Test
    void testSubmitTaxDetails_ErrorInForm() throws Exception {
        // Mock form submission with errors
        when(bindingResult.hasErrors()).thenReturn(true);  // Simulate validation errors

        // Perform the POST request
        mockMvc.perform(post("/submitTaxDetails")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("field1", "value1")  // Add actual form fields here
                        .param("field2", "value2"))
                .andExpect(status().is3xxRedirection())  // Expect redirection
                .andExpect(redirectedUrl("/"))  // Redirect back to the home page
                .andExpect(flash().attribute("message", "Error: Please check your form input."));
    }

    @Test
    void testSubmitTaxDetails_ExceptionHandling() throws Exception {
        // Mock exception during processing
        when(bindingResult.hasErrors()).thenReturn(false);  // No errors
        when(taxDetailsRepository.save(any(TaxDetails.class))).thenThrow(new RuntimeException("Database error"));

        // Perform the POST request
        mockMvc.perform(post("/submitTaxDetails")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("field1", "value1")  // Add actual form fields here
                        .param("field2", "value2"))
                .andExpect(status().is3xxRedirection())  // Expect redirection due to error
                .andExpect(redirectedUrl("/error"))  // Redirect to error page
                .andExpect(flash().attribute("message", "An error occurred while submitting tax details: Database error"));
    }
}
