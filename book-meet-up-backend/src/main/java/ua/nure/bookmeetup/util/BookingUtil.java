package ua.nure.bookmeetup.util;

import org.threeten.extra.Interval;
import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.booking.Booking;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Predicate;

public class BookingUtil {

    private BookingUtil() {
    }

    public static boolean isRoomAvailableForBooking(MeetingRoom meetingRoom, LocalDateTime requestedStartDateTime,
                                                                Short duration) {
        Predicate<Booking> requestedTimeSlotHasConflict = booking -> {
            var requestedEndTime = requestedStartDateTime.plusMinutes(duration);
            var bookingStartTime = booking.startDateAndTime();
            var bookingEndTime = booking.endDateAndTime();
            var requestedInterval = createInterval(requestedStartDateTime, requestedEndTime);
            var bookingInterval = createInterval(bookingStartTime, bookingEndTime);
            return requestedInterval.overlaps(bookingInterval);
        };
        return meetingRoom.getBookings().stream()
                .filter(booking -> booking.getDate().isEqual(requestedStartDateTime.toLocalDate()))
                .filter(booking -> !(booking.isCanceled() || booking.isFinished()))
                .noneMatch(requestedTimeSlotHasConflict);
    }

    private static Interval createInterval (LocalDateTime from, LocalDateTime to) {
        return Interval.of(
                Instant.from(ZonedDateTime.of(from, ZoneId.systemDefault())),
                Instant.from(ZonedDateTime.of(to, ZoneId.systemDefault()))
        );
    }

}
