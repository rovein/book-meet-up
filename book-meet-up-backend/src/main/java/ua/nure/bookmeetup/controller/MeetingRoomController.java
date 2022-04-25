package ua.nure.bookmeetup.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.nure.bookmeetup.dto.MeetingRoomDto;
import ua.nure.bookmeetup.service.MeetingRoomService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ua.nure.bookmeetup.validation.BindingResultValidator.errorBody;

@CrossOrigin
@RestController
@RequestMapping("/office-buildings")
@Api(tags = "5. Meeting Room")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @Autowired
    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping("/{id}/meeting-rooms")
    @ApiOperation(value = "Returns a list of all meeting rooms", nickname = "getAllMeetingRooms")
    public ResponseEntity<List<MeetingRoomDto>> getAllMeetingRooms(@PathVariable Long id) {
        return ResponseEntity.ok(meetingRoomService.findAllMeetingRooms(id));
    }

    @PostMapping("/{id}/meeting-rooms")
    @ApiOperation(value = "Adds new meeting room", nickname = "addMeetingRoom")
    public ResponseEntity<?> addMeetingRoom(@PathVariable Long id,
                                            @Valid @RequestBody MeetingRoomDto meetingRoomDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(meetingRoomService.addMeetingRoom(meetingRoomDto, id));
    }

    @PutMapping("/{id}/meeting-rooms")
    @ApiOperation(value = "Updates the meeting room", nickname = "updateMeetingRoom")
    public ResponseEntity<?> updateMeetingRoom(@PathVariable Long id,
                                               @Valid @RequestBody MeetingRoomDto meetingRoomDto,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(meetingRoomService.updateMeetingRoom(meetingRoomDto, id));
    }

    @DeleteMapping("/meeting-rooms/{id}")
    @ApiOperation(value = "Deletes meeting room by ID", nickname = "deleteMeetingRoom")
    public ResponseEntity<Void> deleteMeetingRoom(@PathVariable Long id) {
        meetingRoomService.deleteMeetingRoom(meetingRoomService.findMeetingRoomById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/meeting-rooms/{id}")
    @ApiOperation(value = "Finds meeting room by ID", nickname = "getMeetingRoomById")
    public ResponseEntity<?> getMeetingRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingRoomService.findMeetingRoomById(id));
    }

    @GetMapping("/{id}/meeting-rooms/available-for-booking")
    @ApiOperation(value = "Finds meeting rooms available for booking", nickname = "getMeetingRoomsAvailableForBooking")
    public ResponseEntity<?> getMeetingRoomsAvailableForBooking(@PathVariable Long id,
                                                                @RequestParam String dateTime,
                                                                @RequestParam Short duration) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime);
        return ResponseEntity.ok(meetingRoomService.getRoomsAvailableForBooking(id, parsedDateTime, duration));
    }

}
