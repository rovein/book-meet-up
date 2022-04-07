package ua.nure.bookmeetup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.dto.OfficeBuildingDto;
import ua.nure.bookmeetup.dto.mapper.OfficeBuildingMapper;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.exception.EntityNotFoundException;
import ua.nure.bookmeetup.repository.OfficeBuildingRepository;
import ua.nure.bookmeetup.service.OfficeBuildingService;

import javax.transaction.Transactional;
import java.util.List;

import static ua.nure.bookmeetup.dto.mapper.OfficeBuildingMapper.toOfficeBuilding;
import static ua.nure.bookmeetup.dto.mapper.OfficeBuildingMapper.toOfficeBuildingDto;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_OFFICE_BUILDING_BY_ID;

@Service
public class OfficeBuildingServiceImpl implements OfficeBuildingService {

    private final OfficeBuildingRepository officeBuildingRepository;

    @Autowired
    public OfficeBuildingServiceImpl(OfficeBuildingRepository officeBuildingRepository) {
        this.officeBuildingRepository = officeBuildingRepository;
    }

    @Override
    public List<OfficeBuildingDto> findAllOfficeBuildings() {
        return toOfficeBuildingDto(officeBuildingRepository.findAll());
    }

    @Override
    public OfficeBuildingDto addOfficeBuilding(OfficeBuildingDto officeBuildingDto) {
        OfficeBuilding officeBuilding = toOfficeBuilding(officeBuildingDto, new OfficeBuilding());
        return toOfficeBuildingDto(officeBuildingRepository.save(officeBuilding));
    }

    @Override
    @Transactional
    public OfficeBuildingDto updateOfficeBuilding(OfficeBuildingDto officeBuildingDto) {
        return officeBuildingRepository.findById(officeBuildingDto.getId())
                .map(initialBuilding -> {
                    OfficeBuilding updatedBuilding = toOfficeBuilding(officeBuildingDto, initialBuilding);
                    return toOfficeBuildingDto(updatedBuilding);
                }).orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_OFFICE_BUILDING_BY_ID));
    }

    @Override
    public void deleteOfficeBuilding(OfficeBuildingDto officeBuildingDto) {
        officeBuildingRepository.deleteById(officeBuildingDto.getId());
    }

    @Override
    public OfficeBuildingDto findOfficeBuildingById(Long id) {
        return officeBuildingRepository.findById(id)
                .map(OfficeBuildingMapper::toOfficeBuildingDto)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_OFFICE_BUILDING_BY_ID));
    }

}
