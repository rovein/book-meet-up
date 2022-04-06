package ua.nure.bookmeetup.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ua.nure.bookmeetup.entity.role.UserRole;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TokenDto {

    private String token;

    private String email;

    private UserRole userRole;

}
