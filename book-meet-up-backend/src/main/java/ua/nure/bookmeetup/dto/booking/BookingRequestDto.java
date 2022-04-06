package ua.nure.bookmeetup.dto.booking;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ua.nure.bookmeetup.entity.booking.BookingStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class BookingRequestDto {

    private Long id;

    private LocalDate date;

    private LocalTime time;

    private Short duration;

    private Long employeeId;

    private Long meetingRoomId;

}
