package ua.nure.bookmeetup.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.bookmeetup.dto.EmployeeDto;
import ua.nure.bookmeetup.service.EmployeeService;

import javax.validation.Valid;
import java.util.List;

import static ua.nure.bookmeetup.validation.BindingResultValidator.errorBody;

@CrossOrigin
@RestController
@RequestMapping("employees")
@Api(tags = "3. Employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ApiOperation(value = "Returns a list of all employees", nickname = "getAllEmployees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "Finds employee by email", nickname = "getEmployeeByEmail")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable String email) {
        return ResponseEntity.ok(employeeService.findByEmail(email));
    }

    @PostMapping
    @ApiOperation(value = "Adds new employee", nickname = "addEmployee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(employeeService.create(employeeDto));
    }

    @PutMapping
    @ApiOperation(value = "Updates the employee", nickname = "updateEmployee")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(employeeService.update(employeeDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes employee by ID", nickname = "deleteEmployee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
