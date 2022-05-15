package ua.nure.bookmeetup.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ua.nure.bookmeetup.dto.booking.BookingInfo;
import ua.nure.bookmeetup.entity.booking.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    String WHERE = " WHERE ";
    String EMPLOYEE_IS = "e.employee_id = ?1";
    String ROOM_IS = "mr.meeting_room_id = ?1";
    String BOOKING_IS = "bk.booking_id = ?1";
    String AND = " AND ";
    String IS_MEETING_UPCOMING = "bk.status IN ('CREATED', 'IN_PROGRESS')";

    String SELECT_STORAGE_INFO = "SELECT bk.booking_id as id, bk.date, bk.time, bk.duration, bk.status, "
            + "bk.meeting_room_id as meetingRoomId, mr.number, mr.floor, mr.info, "
            + "ob.city, ob.street, ob.house, ob.name "
            + "FROM booking bk "
            + "JOIN employee e ON bk.employee_id = e.employee_id "
            + "JOIN meeting_room mr ON bk.meeting_room_id = mr.meeting_room_id "
            + "JOIN office_building ob ON mr.office_building_id = ob.office_building_id";

    @Query(value = SELECT_STORAGE_INFO + WHERE + EMPLOYEE_IS, nativeQuery = true)
    List<BookingInfo> getAllBookingsByEmployee(Long employeeId);

    @Query(value = SELECT_STORAGE_INFO + WHERE + EMPLOYEE_IS + AND + IS_MEETING_UPCOMING, nativeQuery = true)
    List<BookingInfo> getAllUpcomingBookingsByEmployee(Long employeeId);

    @Query(value = SELECT_STORAGE_INFO + WHERE + ROOM_IS, nativeQuery = true)
    List<BookingInfo> getAllBookingsByMeetingRoom(Long meetingRoomId);

    @Query(value = SELECT_STORAGE_INFO + WHERE + ROOM_IS + AND + IS_MEETING_UPCOMING, nativeQuery = true)
    List<BookingInfo> getAllUpcomingBookingsByMeetingRoom(Long meetingRoomId);

    @Query(value = SELECT_STORAGE_INFO + WHERE + BOOKING_IS, nativeQuery = true)
    BookingInfo getBookingInfoById(Long bookingId);

    @NonNull
    List<Booking> findAll();
}
