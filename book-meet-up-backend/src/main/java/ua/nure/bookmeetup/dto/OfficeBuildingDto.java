package ua.nure.bookmeetup.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class OfficeBuildingDto {

    private Long id;

    private String city;

    private String street;

    private String house;

    private String name;

}
