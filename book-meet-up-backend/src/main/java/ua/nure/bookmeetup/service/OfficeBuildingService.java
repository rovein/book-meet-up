package ua.nure.bookmeetup.service;

import ua.nure.bookmeetup.dto.OfficeBuildingDto;
import java.util.List;

public interface OfficeBuildingService {

    List<OfficeBuildingDto> findAllOfficeBuildings();

    OfficeBuildingDto addOfficeBuilding(OfficeBuildingDto officeBuildingDto);

    OfficeBuildingDto updateOfficeBuilding(OfficeBuildingDto officeBuildingDto);

    OfficeBuildingDto findOfficeBuildingById(Long id);

    void deleteOfficeBuilding(OfficeBuildingDto officeBuildingDto);

}
