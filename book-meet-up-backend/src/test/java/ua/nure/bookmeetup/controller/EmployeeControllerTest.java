package ua.nure.bookmeetup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.nure.bookmeetup.dto.EmployeeDto;
import ua.nure.bookmeetup.exception.EntityNotFoundException;
import ua.nure.bookmeetup.service.EmployeeService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = {WebSecurityConfigurer.class, OncePerRequestFilter.class})},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class EmployeeControllerTest {

    private final EmployeeDto employee = new EmployeeDto().setId(1L).setPhoneNumber("test")
            .setEmail("test@gmail.com").setFirstName("test").setLastName("test");
    private final List<EmployeeDto> employees = List.of(employee, new EmployeeDto().setId(2L));

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployees() throws Exception {
        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{}, {}]"))
                .andExpect(jsonPath("$[0]['id']", is(1)))
                .andExpect(jsonPath("$[1]['id']", is(2)));
    }

    @Test
    public void testGetAllEmployeesIfEmpty() throws Exception {
        when(employeeService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetMEmployeeByEmail() throws Exception {
        when(employeeService.findByEmail(anyString())).thenReturn(employees.get(0));

        mockMvc.perform(get("/employees/test@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testGetMEmployeeByEmailIfNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(employeeService).findByEmail(anyString());
        mockMvc.perform(get("/employees/test@gmail.com")).andExpect(status().isNotFound());
    }

    @Test
    public void testAddEmployee() throws Exception {
        when(employeeService.create(any())).thenReturn(employee);
        verifyCreateOrUpdateAction(post("/employees"));
    }

    @Test
    public void testAddEmployeeValidationFailure() throws Exception {
        verifyFailureCreateOrUpdateAction(post("/employees"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(employeeService.findByEmail(anyString())).thenReturn(employee);
        when(employeeService.update(any())).thenReturn(employee);
        verifyCreateOrUpdateAction(put("/employees"));
    }

    @Test
    public void testUpdateEmployeeValidationFailure() throws Exception {
        verifyFailureCreateOrUpdateAction(put("/employees"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        when(employeeService.findByEmail(anyString())).thenReturn(employee);
        doNothing().when(employeeService).delete(employee);
        mockMvc.perform(delete("/employees/test@gmail.com")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployeeWhenEmailIsNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(employeeService).findByEmail(anyString());
        mockMvc.perform(delete("/employees/test@gmail.com")).andExpect(status().isNoContent());
    }

    private void verifyCreateOrUpdateAction(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.phoneNumber", is("test")))
                .andExpect(jsonPath("$.email", is("test@gmail.com")))
                .andExpect(jsonPath("$.firstName", is("test")))
                .andExpect(jsonPath("$.lastName", is("test")));
    }

    private void verifyFailureCreateOrUpdateAction(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        EmployeeDto invalidProvider = new EmployeeDto().setEmail("invalid");
        mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProvider)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"))
                .andExpect(jsonPath("$.phoneNumber", is("Phone number can`t be empty")))
                .andExpect(jsonPath("$.firstName", is("First name can`t be empty")))
                .andExpect(jsonPath("$.lastName", is("Last name can`t be empty")))
                .andExpect(jsonPath("$.email", is("Invalid email")));
    }

}
