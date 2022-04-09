package ua.nure.bookmeetup.service;

import ua.nure.bookmeetup.dto.booking.BookingInfo;
import ua.nure.bookmeetup.dto.booking.BookingInfoDto;
import ua.nure.bookmeetup.dto.booking.BookingRequestDto;
import ua.nure.bookmeetup.dto.booking.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto createSingle(BookingRequestDto bookingRequestDto);

    BookingResponseDto update(BookingRequestDto bookingRequestDto);

    void delete(Long id);

    BookingResponseDto findById(Long id);

    List<BookingInfoDto> getAllBookingsByEmployee(Long employeeId);

    List<BookingInfoDto> getAllBookingsByMeetingRoom(Long meetingRoomId);

    BookingInfoDto getBookingInfoById(Long bookingId);

    void checkAndUpdateBookingStatuses();

    void cancelBooking(Long bookingId);

}
