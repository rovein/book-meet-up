package ua.nure.bookmeetup.dto.mapper;

import ua.nure.bookmeetup.dto.booking.BookingInfo;
import ua.nure.bookmeetup.dto.booking.BookingInfoDto;
import ua.nure.bookmeetup.dto.booking.BookingRequestDto;
import ua.nure.bookmeetup.dto.booking.BookingResponseDto;
import ua.nure.bookmeetup.entity.booking.Booking;

import java.util.List;
import java.util.stream.Collectors;

import static ua.nure.bookmeetup.entity.booking.BookingStatus.CREATED;

public class BookingMapper {

    public static BookingResponseDto toBookingResponseDto(Booking booking) {
        return new BookingResponseDto()
                .setId(booking.getId())
                .setDate(booking.getDate())
                .setTime(booking.getTime())
                .setDuration(booking.getDuration())
                .setStatus(booking.getStatus())
                .setMeetingRoomId(booking.getMeetingRoom().getId())
                .setEmployeeId(booking.getEmployee().getId());
    }

    public static List<BookingInfoDto> toBookingInfoDto(List<BookingInfo> storages) {
        return storages.stream()
                .map(BookingInfoDto::new)
                .collect(Collectors.toList());
    }

    public static Booking toBooking(BookingRequestDto bookingRequestDto) {
        Booking booking = new Booking();
        booking.setDate(bookingRequestDto.getDate());
        booking.setTime(bookingRequestDto.getTime());
        booking.setDuration(bookingRequestDto.getDuration());
        booking.setStatus(CREATED);
        return booking;
    }

}
