package ua.nure.bookmeetup.dto.booking;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ua.nure.bookmeetup.entity.booking.Booking;
import ua.nure.bookmeetup.entity.booking.BookingStatus;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class BookingInfoDto {

    public BookingInfoDto(BookingInfo bookingInfo) {
        this.setId(bookingInfo.getId())
                .setDate(bookingInfo.getDate())
                .setTime(bookingInfo.getTime())
                .setDuration(bookingInfo.getDuration())
                .setStatus(BookingStatus.valueOf(bookingInfo.getStatus()))
                .setNumber(bookingInfo.getNumber())
                .setFloor(bookingInfo.getFloor())
                .setInfo(bookingInfo.getInfo())
                .setCity(bookingInfo.getCity())
                .setStreet(bookingInfo.getStreet())
                .setHouse(bookingInfo.getHouse())
                .setName(bookingInfo.getName())
                .setMeetingRoomId(bookingInfo.getMeetingRoomId());
    }

    private Long id;

    private LocalDate date;

    private LocalTime time;

    private Short duration;

    private BookingStatus status;

    private Integer number;

    private Byte floor;

    private String info;

    private String city;

    private String street;

    private String house;

    private String name;

    private Long meetingRoomId;

}
