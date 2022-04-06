package ua.nure.bookmeetup.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ua.nure.bookmeetup.entity.role.UserRole;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotEmpty(message = "First name can`t be empty")
    private String firstName;

    @NotEmpty(message = "Last name can`t be empty")
    private String lastName;

    @NotEmpty(message = "Phone number can`t be empty")
    private String phoneNumber;

    @NotEmpty(message = "Email can`t be empty")
    @Email(message = "Invalid email")
    protected String email;

    protected String password;

    @ApiModelProperty(hidden = true)
    private UserRole role;

    @ApiModelProperty(hidden = true)
    private Boolean isLocked;

    private String country;

    public EmployeeDto setLockStatus(boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    @ApiModelProperty(hidden = true)
    public Boolean getLockStatus() {
        return isLocked;
    }

}
