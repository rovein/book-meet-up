package ua.nure.bookmeetup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.dto.MeetingRoomDto;
import ua.nure.bookmeetup.dto.mapper.MeetingRoomMapper;
import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.exception.EntityNotFoundException;
import ua.nure.bookmeetup.repository.MeetingRoomRepository;
import ua.nure.bookmeetup.repository.OfficeBuildingRepository;
import ua.nure.bookmeetup.service.MeetingRoomService;

import java.util.Collections;
import java.util.List;

import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_MEETING_ROOM_BY_ID;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_OFFICE_BUILDING_BY_ID;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final OfficeBuildingRepository officeBuildingRepository;

    private final MeetingRoomRepository meetingRoomRepository;

    @Autowired
    public MeetingRoomServiceImpl(OfficeBuildingRepository officeBuildingRepository,
                                  MeetingRoomRepository meetingRoomRepository) {
        this.officeBuildingRepository = officeBuildingRepository;
        this.meetingRoomRepository = meetingRoomRepository;
    }

    @Override
    public List<MeetingRoomDto> findAllMeetingRooms(Long officeBuildingId) {
        return officeBuildingRepository.findById(officeBuildingId)
                .map(office -> MeetingRoomMapper.toMeetingRoomDto(office.getMeetingRooms()))
                .orElse(Collections.emptyList());
    }

    @Override
    public MeetingRoomDto addMeetingRoom(MeetingRoomDto meetingRoomDto, Long officeBuildingId) {
        return saveMeetingRoom(meetingRoomDto,
                officeBuildingRepository.findById(officeBuildingId)
                        .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_OFFICE_BUILDING_BY_ID))
        );
    }

    @Override
    public MeetingRoomDto updateMeetingRoom(MeetingRoomDto meetingRoomDto, Long officeBuildingId) {
        return officeBuildingRepository.findById(officeBuildingId)
                .map(office -> {
                    meetingRoomRepository.findById(meetingRoomDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_MEETING_ROOM_BY_ID));
                    return office;
                })
                .map(office -> saveMeetingRoom(meetingRoomDto, office))
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_OFFICE_BUILDING_BY_ID));
    }

    @Override
    public void deleteMeetingRoom(MeetingRoomDto meetingRoomDto) {
        meetingRoomRepository.deleteById(meetingRoomDto.getId());
    }

    @Override
    public MeetingRoomDto findMeetingRoomById(Long id) {
        return meetingRoomRepository.findById(id)
                .map(MeetingRoomMapper::toMeetingRoomDto)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_MEETING_ROOM_BY_ID));
    }

    private MeetingRoomDto saveMeetingRoom(MeetingRoomDto meetingRoomDto, OfficeBuilding parentBuilding) {
        MeetingRoom meetingRoom = MeetingRoomMapper.toMeetingRoom(meetingRoomDto);
        meetingRoom.setOfficeBuilding(parentBuilding);
        return MeetingRoomMapper.toMeetingRoomDto(meetingRoomRepository.save(meetingRoom));
    }

}
