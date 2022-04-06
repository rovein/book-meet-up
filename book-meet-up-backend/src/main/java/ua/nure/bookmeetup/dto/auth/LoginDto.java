package ua.nure.bookmeetup.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors
@NoArgsConstructor
public class LoginDto {

    private String email;

    private String password;

}
