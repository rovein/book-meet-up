package ua.nure.bookmeetup.dto.error;

import lombok.Data;

@Data
public class StorageCreationTemperatureErrorDto {

    private final Double actualTemperature;

    private final Integer minTemperature;

    private final Integer maxTemperature;

    private final String errorKey = "StorageCreationTemperatureError";

}
