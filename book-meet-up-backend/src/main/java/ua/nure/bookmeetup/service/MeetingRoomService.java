package ua.nure.bookmeetup.service;

import ua.nure.bookmeetup.dto.MeetingRoomDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomService {

    List<MeetingRoomDto> findAllMeetingRooms(Long officeBuildingId);

    MeetingRoomDto addMeetingRoom(MeetingRoomDto meetingRoomDto, Long officeBuildingId);

    MeetingRoomDto updateMeetingRoom(MeetingRoomDto meetingRoomDto, Long officeBuildingId);

    MeetingRoomDto findMeetingRoomById(Long id);

    void deleteMeetingRoom(MeetingRoomDto meetingRoomDto);

    List<MeetingRoomDto> getRoomsAvailableForBooking(Long officeId, LocalDateTime dateTime, Short duration);

}
