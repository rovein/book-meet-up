package ua.nure.bookmeetup.dto.mapper;

import ua.nure.bookmeetup.dto.OfficeBuildingDto;
import ua.nure.bookmeetup.entity.OfficeBuilding;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OfficeBuildingMapper {

    public static OfficeBuildingDto toOfficeBuildingDto(OfficeBuilding officeBuilding) {
        return new OfficeBuildingDto()
                .setId(officeBuilding.getId())
                .setCity(officeBuilding.getCity())
                .setStreet(officeBuilding.getStreet())
                .setHouse(officeBuilding.getHouse())
                .setName(officeBuilding.getName());
    }

    public static List<OfficeBuildingDto> toOfficeBuildingDto(List<OfficeBuilding> officeBuildings) {
        return officeBuildings.stream().map(OfficeBuildingMapper::toOfficeBuildingDto).collect(Collectors.toList());
    }

    public static OfficeBuilding toOfficeBuilding(OfficeBuildingDto officeBuildingDto, OfficeBuilding officeBuilding) {
        return officeBuilding
                .setCity(officeBuildingDto.getCity())
                .setStreet(officeBuildingDto.getStreet())
                .setHouse(officeBuildingDto.getHouse())
                .setName(officeBuildingDto.getName());
    }

}
