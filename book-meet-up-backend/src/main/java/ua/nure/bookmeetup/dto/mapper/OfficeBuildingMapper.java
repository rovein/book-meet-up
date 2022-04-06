package ua.nure.bookmeetup.dto.mapper;

import ua.nure.bookmeetup.dto.OfficeBuildingDto;
import ua.nure.bookmeetup.entity.OfficeBuilding;

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

    public static Set<OfficeBuildingDto> toOfficeBuildingDto(Set<OfficeBuilding> officeBuildings) {
        return officeBuildings.stream().map(OfficeBuildingMapper::toOfficeBuildingDto).collect(Collectors.toSet());
    }

    public static OfficeBuilding toOfficeBuilding(OfficeBuildingDto officeBuildingDto) {
        return new OfficeBuilding()
                .setId(officeBuildingDto.getId() == null ? 0 : officeBuildingDto.getId())
                .setCity(officeBuildingDto.getCity())
                .setStreet(officeBuildingDto.getStreet())
                .setHouse(officeBuildingDto.getHouse())
                .setName(officeBuildingDto.getName());
    }

}
