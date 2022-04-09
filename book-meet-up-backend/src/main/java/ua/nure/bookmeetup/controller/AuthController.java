package ua.nure.bookmeetup.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.bookmeetup.dto.EmployeeDto;
import ua.nure.bookmeetup.dto.auth.LoginDto;
import ua.nure.bookmeetup.dto.auth.TokenDto;
import ua.nure.bookmeetup.entity.user.User;
import ua.nure.bookmeetup.security.JwtTokenUtil;
import ua.nure.bookmeetup.security.UserDetailsImpl;
import ua.nure.bookmeetup.service.EmployeeService;

import javax.validation.Valid;

import static ua.nure.bookmeetup.validation.BindingResultValidator.errorBody;

@RestController
@CrossOrigin
@RequestMapping(path = "/auth")
@Api(tags = "1. Authorization")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final EmployeeService employeeService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
            EmployeeService employeeService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    @ApiOperation(
            value = "Performs user login to the system",
            nickname = "loginUser"
    )
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        final String token = new JwtTokenUtil().generateToken(userDetails);

        User user = ((UserDetailsImpl) userDetails).getUser();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new TokenDto().setToken(token).setEmail(user.getEmail()).setUserRole(user.getRole().getName()));
    }

    @PostMapping("/register/employee")
    @ApiOperation(
            value = "Registers a new employee",
            nickname = "registerEmployee"
    )
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody EmployeeDto employeeDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(employeeService.create(employeeDto));
    }

}
