package ua.nure.bookmeetup.dto.mapper;

import ua.nure.bookmeetup.dto.MeetingRoomDto;
import ua.nure.bookmeetup.entity.MeetingRoom;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeetingRoomMapper {

    public static MeetingRoomDto toMeetingRoomDto(MeetingRoom meetingRoom) {
        return new MeetingRoomDto()
                .setId(meetingRoom.getId())
                .setNumber(meetingRoom.getNumber())
                .setFloor(meetingRoom.getFloor())
                .setInfo(meetingRoom.getInfo());
    }

    public static List<MeetingRoomDto> toMeetingRoomDto(List<MeetingRoom> meetingRooms) {
        return meetingRooms.stream()
                .map(MeetingRoomMapper::toMeetingRoomDto).collect(Collectors.toList());
    }

    public static MeetingRoom toMeetingRoom(MeetingRoomDto meetingRoomDto) {
        return new MeetingRoom()
                .setId(meetingRoomDto.getId() == null ? 0 : meetingRoomDto.getId())
                .setNumber(meetingRoomDto.getNumber())
                .setFloor(meetingRoomDto.getFloor())
                .setInfo(meetingRoomDto.getInfo());
    }

}
