package ua.nure.bookmeetup.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ua.nure.bookmeetup.dto.booking.BookingInfo;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.entity.booking.Booking;

import java.util.List;
import java.util.Set;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    String baseStorageInfoQuery = "SELECT bk.booking_id as id, bk.date, bk.time, bk.duration, bk.status, "
            + "bk.meeting_room_id as meetingRoomId, mr.number, mr.floor, mr.info, "
            + "ob.city, ob.street, ob.house, ob.name "
            + "FROM booking bk "
            + "JOIN employee e ON bk.employee_id = e.employee_id "
            + "JOIN meeting_room mr ON bk.meeting_room_id = mr.meeting_room_id "
            + "JOIN office_building ob ON mr.office_building_id = ob.office_building_id ";

    @Query(value = baseStorageInfoQuery + "WHERE e.employee_id = ?1", nativeQuery = true)
    List<BookingInfo> getAllBookingsByEmployee(Long employeeId);

    @Query(value = baseStorageInfoQuery + "WHERE mr.meeting_room_id = ?1", nativeQuery = true)
    List<BookingInfo> getAllBookingsByMeetingRoom(Long meetingRoomId);

    @Query(value = baseStorageInfoQuery + "WHERE bk.booking_id = ?1", nativeQuery = true)
    BookingInfo getBookingInfoById(Long bookingId);

    @NonNull
    List<Booking> findAll();
}
