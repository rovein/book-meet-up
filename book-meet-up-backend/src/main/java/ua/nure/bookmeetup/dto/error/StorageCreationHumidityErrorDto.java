package ua.nure.bookmeetup.dto.error;

import lombok.Data;

@Data
public class StorageCreationHumidityErrorDto {

    private final Double actualHumidity;

    private final Integer maxHumidity;

    private final String errorKey = "StorageCreationHumidityError";

}
