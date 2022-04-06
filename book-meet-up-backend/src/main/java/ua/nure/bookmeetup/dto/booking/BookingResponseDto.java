package ua.nure.bookmeetup.dto.booking;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ua.nure.bookmeetup.entity.booking.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class BookingResponseDto {

    private Long id;

    private LocalDate date;

    private LocalTime time;

    private Short duration;

    private Long employeeId;

    private Long meetingRoomId;

    private BookingStatus status;

}
