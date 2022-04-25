package ua.nure.bookmeetup.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nure.bookmeetup.dto.MeetingRoomDto;
import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.entity.booking.Booking;
import ua.nure.bookmeetup.entity.booking.BookingStatus;
import ua.nure.bookmeetup.repository.OfficeBuildingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeetingRoomServiceImplTest {

    @Mock
    private OfficeBuildingRepository officeBuildingRepository;

    @InjectMocks
    private MeetingRoomServiceImpl meetingRoomService;

    private final Long officeId = 1L;
    private final Long meetingRoomId = 2L;
    private final LocalDateTime dateTime = LocalDateTime.of(
            LocalDate.of(2022, 2, 2),
            LocalTime.of(17, 30, 0)
    );
    private final Short duration = 60;

    @Test
    @Order(1)
    @DisplayName("Meeting room is available for new booking if it has no bookings yet")
    public void roomIsAvailableIfHasNoBookings() {
        mockMeetingRoom(Collections.emptyList());

        List<MeetingRoomDto> roomsAvailableForBooking =
                meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);

        verifyMeetingRoomIsAvailable(roomsAvailableForBooking);
    }

    @Test
    @Order(2)
    @DisplayName("Meeting room is available for new booking if it has only finished meetings")
    public void roomIsAvailableIfItHasFinishedBooking() {
        Booking validBooking = new Booking();
        validBooking.setDate(dateTime.toLocalDate());
        validBooking.setStatus(BookingStatus.FINISHED);
        mockMeetingRoom(List.of(validBooking));

        List<MeetingRoomDto> roomsAvailableForBooking =
                meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);

        verifyMeetingRoomIsAvailable(roomsAvailableForBooking);
    }

    @Test
    @Order(3)
    @DisplayName("Meeting room is available for new booking if it has only canceled meetings")
    public void roomIsAvailableIfItHasCanceledBooking() {
        Booking validBooking = new Booking();
        validBooking.setDate(dateTime.toLocalDate());
        validBooking.setStatus(BookingStatus.CANCELED);
        mockMeetingRoom(List.of(validBooking));

        List<MeetingRoomDto> roomsAvailableForBooking =
                meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);

        verifyMeetingRoomIsAvailable(roomsAvailableForBooking);
    }

    @Test
    @Order(4)
    @DisplayName("Meeting room is available for new booking if it has only bookings for another working day")
    public void roomIsAvailableIfItHasBookingsOnAnotherDay() {
        Booking validBooking = new Booking();
        validBooking.setDate(dateTime.toLocalDate().plusDays(1));
        mockMeetingRoom(List.of(validBooking));

        List<MeetingRoomDto> availableMeetingRooms =
                meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);

        verifyMeetingRoomIsAvailable(availableMeetingRooms);
    }

    @Test
    @Order(5)
    @DisplayName("Meeting room is not available if it has booking with the same date and time as requested")
    public void roomIsNotAvailableIfItHasTheSameBookingAsRequested() {
        Booking conflictBooking = conflictBooking();
        conflictBooking.setDate(dateTime.toLocalDate());
        conflictBooking.setTime(dateTime.toLocalTime());
        conflictBooking.setDuration(duration);
        mockMeetingRoom(List.of(conflictBooking));

        wrapWithMockedLocalDateTimeNow(() -> {
            List<MeetingRoomDto> availableMeetingRooms =
                    meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);
            verifyMeetingRoomIsNotAvailable(availableMeetingRooms);
        });
    }

    @Test
    @Order(6)
    @DisplayName("Meeting room is not available if it has booking which interval intersects with start time of requested")
    public void roomIsNotAvailableIfBookingIntersectsWithStartTime() {
        Booking conflictBooking = conflictBooking();
        conflictBooking.setDate(dateTime.toLocalDate());
        conflictBooking.setTime(dateTime.toLocalTime().minusMinutes(30));
        conflictBooking.setDuration(duration);
        mockMeetingRoom(List.of(conflictBooking));

        wrapWithMockedLocalDateTimeNow(() -> {
            List<MeetingRoomDto> availableMeetingRooms =
                    meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);
            verifyMeetingRoomIsNotAvailable(availableMeetingRooms);
        });
    }

    @Test
    @Order(7)
    @DisplayName("Meeting room is not available if it has booking which interval intersects with end time of requested")
    public void roomIsNotAvailableIfBookingIntersectsWithEndTime() {
        Booking conflictBooking = conflictBooking();
        conflictBooking.setDate(dateTime.toLocalDate());
        conflictBooking.setTime(dateTime.toLocalTime().plusMinutes(30));
        conflictBooking.setDuration(duration);
        mockMeetingRoom(List.of(conflictBooking));

        wrapWithMockedLocalDateTimeNow(() -> {
            List<MeetingRoomDto> availableMeetingRooms =
                    meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);
            verifyMeetingRoomIsNotAvailable(availableMeetingRooms);
        });
    }

    @Test
    @Order(8)
    @DisplayName("Meeting room is not available if it has both valid and conflict bookings")
    public void roomIsNotAvailableIfThereAreBothValidAndConflictMeetings() {
        Booking conflictBooking = conflictBooking();
        conflictBooking.setDate(dateTime.toLocalDate());
        conflictBooking.setTime(dateTime.toLocalTime().minusMinutes(30));
        conflictBooking.setDuration(duration);

        Booking validBooking = new Booking();
        validBooking.setDate(dateTime.toLocalDate());
        validBooking.setTime(dateTime.toLocalTime().minusMinutes(duration * 2));
        validBooking.setStatus(BookingStatus.IN_PROGRESS);

        mockMeetingRoom(List.of(conflictBooking, validBooking));

        wrapWithMockedLocalDateTimeNow(() -> {
            List<MeetingRoomDto> availableMeetingRooms =
                    meetingRoomService.getRoomsAvailableForBooking(officeId, dateTime, duration);
            verifyMeetingRoomIsNotAvailable(availableMeetingRooms);
        });
    }

    private Booking conflictBooking() {
        Booking conflictBooking = new Booking();
        conflictBooking.setStatus(BookingStatus.IN_PROGRESS);
        return conflictBooking;
    }

    private void mockMeetingRoom(List<Booking> bookings) {
        OfficeBuilding officeBuilding = new OfficeBuilding().setId(officeId);
        when(officeBuildingRepository.findById(officeId)).thenReturn(Optional.of(officeBuilding));

        MeetingRoom meetingRoom = new MeetingRoom().setId(meetingRoomId);
        List<MeetingRoom> meetingRooms = List.of(meetingRoom);
        officeBuilding.setMeetingRooms(meetingRooms);

        meetingRoom.setBookings(bookings);
    }

    private void wrapWithMockedLocalDateTimeNow(Runnable action) {
        try (MockedStatic<LocalDateTime> localDateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(dateTime);
            localDateTimeMockedStatic.when(() -> LocalDateTime.of(any(), any())).thenCallRealMethod();
            action.run();
        }
    }

    private void verifyMeetingRoomIsAvailable(List<MeetingRoomDto> roomsAvailableForBooking) {
        verify(officeBuildingRepository).findById(officeId);
        assertThat(roomsAvailableForBooking.size()).isEqualTo(1);
        assertThat(roomsAvailableForBooking.get(0).getId()).isEqualTo(meetingRoomId);
    }

    private void verifyMeetingRoomIsNotAvailable(List<MeetingRoomDto> roomsAvailableForBooking) {
        verify(officeBuildingRepository).findById(officeId);
        assertThat(roomsAvailableForBooking.size()).isEqualTo(0);
    }

}
