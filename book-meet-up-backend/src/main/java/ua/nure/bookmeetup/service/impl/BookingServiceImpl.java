package ua.nure.bookmeetup.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.dto.booking.BookingInfo;
import ua.nure.bookmeetup.dto.booking.BookingInfoDto;
import ua.nure.bookmeetup.dto.booking.BookingRequestDto;
import ua.nure.bookmeetup.dto.booking.BookingResponseDto;
import ua.nure.bookmeetup.dto.mapper.BookingMapper;
import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.booking.Booking;
import ua.nure.bookmeetup.entity.booking.BookingStatus;
import ua.nure.bookmeetup.entity.user.Employee;
import ua.nure.bookmeetup.exception.BookingCreationException;
import ua.nure.bookmeetup.exception.EntityNotFoundException;
import ua.nure.bookmeetup.repository.EmployeeRepository;
import ua.nure.bookmeetup.repository.MeetingRoomRepository;
import ua.nure.bookmeetup.repository.BookingRepository;
import ua.nure.bookmeetup.service.BookingService;
import ua.nure.bookmeetup.util.BookingUtil;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static ua.nure.bookmeetup.entity.booking.BookingStatus.CREATED;
import static ua.nure.bookmeetup.entity.booking.BookingStatus.IN_PROGRESS;
import static ua.nure.bookmeetup.util.EmailNotificationSender.*;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_BOOKING_BY_ID;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_EMPLOYEE_BY_ID;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_MEETING_ROOM_BY_ID;

@Service
@Log4j2
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final MeetingRoomRepository meetingRoomRepository;

    private final EmployeeRepository employeeRepository;

    private static final Short FIFTEEN = 15;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              EmployeeRepository employeeRepository,
                              MeetingRoomRepository meetingRoomRepository) {
        this.bookingRepository = bookingRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @SneakyThrows
    public BookingResponseDto createSingle(BookingRequestDto bookingRequestDto) {
        Employee employee = employeeRepository
                .findById(bookingRequestDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_EMPLOYEE_BY_ID));
        MeetingRoom meetingRoom = meetingRoomRepository
                .findById(bookingRequestDto.getMeetingRoomId())
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_MEETING_ROOM_BY_ID));

        LocalDateTime requestedDateTime = LocalDateTime.of(bookingRequestDto.getDate(), bookingRequestDto.getTime());
        if (!BookingUtil.isRoomAvailableForBooking(meetingRoom, requestedDateTime, bookingRequestDto.getDuration())) {
            ObjectMapper objectMapper = new ObjectMapper();
            var error = objectMapper.writeValueAsString(Map.of("errorKey", "RoomIsAlreadyBusy"));
            throw new BookingCreationException(error);
        }

        Booking booking = BookingMapper.toBooking(bookingRequestDto);
        booking.setEmployee(employee);
        booking.setMeetingRoom(meetingRoom);

        Booking createdBooking = bookingRepository.save(booking);
        sendBookingCreatedEmailNotification(employee, createdBooking, meetingRoom);

        return BookingMapper.toBookingResponseDto(createdBooking);
    }

    @Override
    @Transactional
    public BookingResponseDto update(BookingRequestDto bookingRequestDto) {
        return bookingRepository.findById(bookingRequestDto.getId())
                .map(booking -> {
                    booking.setDate(bookingRequestDto.getDate());
                    booking.setTime(bookingRequestDto.getTime());
                    booking.setDuration(bookingRequestDto.getDuration());
                    return BookingMapper.toBookingResponseDto(booking);
                })
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_BOOKING_BY_ID));
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingResponseDto findById(Long id) {
        return bookingRepository.findById(id)
                .map(BookingMapper::toBookingResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_BOOKING_BY_ID));
    }

    @Override
    public List<BookingInfoDto> getAllBookingsByEmployee(Long employeeId, boolean isUpcoming) {
        return getAllBookingsBy(employeeRepository, employeeId, isUpcoming
                ? bookingRepository::getAllUpcomingBookingsByEmployee
                : bookingRepository::getAllBookingsByEmployee);
    }

    @Override
    public List<BookingInfoDto> getAllBookingsByMeetingRoom(Long meetingRoomId, boolean isUpcoming) {
        return getAllBookingsBy(meetingRoomRepository, meetingRoomId, isUpcoming
                ? bookingRepository::getAllUpcomingBookingsByMeetingRoom
                : bookingRepository::getAllBookingsByMeetingRoom);
    }

    public List<BookingInfoDto> getAllBookingsBy(CrudRepository<?, Long> repository, Long id,
                                                 Function<Long, List<BookingInfo>> bookingsFinder) {
        return repository.findById(id)
                .map(entity -> bookingsFinder.apply(id))
                .map(BookingMapper::toBookingInfoDto)
                .orElse(Collections.emptyList());
    }

    @Override
    public BookingInfoDto getBookingInfoById(Long bookingId) {
        return Optional.of(findById(bookingId))
                .map(booking -> bookingRepository.getBookingInfoById(bookingId))
                .map(BookingInfoDto::new)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_BOOKING_BY_ID));
    }

    @Override
    @Transactional
    public void checkAndUpdateBookingStatuses() {
        bookingRepository.findAll().stream()
                .filter(this::isCreatedOrInProgress)
                .forEach(this::changeStatus);
    }

    private boolean isCreatedOrInProgress(Booking booking) {
        BookingStatus bookingStatus = booking.getStatus();
        return CREATED.equals(bookingStatus) || IN_PROGRESS.equals(bookingStatus);
    }

    private void changeStatus(Booking booking) {
        BookingStatus status = booking.getStatus();
        if (CREATED.equals(status) && booking.isInProgress()) {
            log.info("Changing status from CREATED to IN_PROGRESS for booking {}", booking.getId());
            booking.setStatus(IN_PROGRESS);
        } else if (IN_PROGRESS.equals(status) && booking.isFinished()) {
            log.info("Changing status from IN_PROGRESS to FINISHED for booking {}", booking.getId());
            booking.setStatus(BookingStatus.FINISHED);
        }
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {
        bookingRepository.findById(bookingId)
                .ifPresentOrElse(booking -> booking.setStatus(BookingStatus.CANCELED),
                        () -> {
                            throw new EntityNotFoundException(ERROR_FIND_BOOKING_BY_ID);
                        });
    }

    @Override
    @Transactional
    public void sendEmailInvitation(Long bookingId, List<String> emails) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_BOOKING_BY_ID));
        sendEmailForBatchOfUsers(emails, employee -> sendBookingEmailInvitation(employee, booking));
    }

    @Override
    public void sendEmailCancelNotification(Long bookingId, List<String> emails) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_BOOKING_BY_ID));
        sendEmailForBatchOfUsers(emails, employee -> sendBookingEmailCancelNotification(employee, booking));
    }

    private void sendEmailForBatchOfUsers(List<String> emails, Consumer<Employee> emailSender) {
        List<Employee> invitedEmployees = employeeRepository.findAllWithEmails(emails.toArray(new String[0]));
        invitedEmployees.forEach(emailSender);
    }

    @Override
    @Transactional
    public void checkAndNotifyAboutUpcomingMeeting() {
        bookingRepository.findAll().stream()
                .filter(booking -> CREATED.equals(booking.getStatus()))
                .forEach(booking -> {
                    LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
                    LocalDateTime bookingStartTime = booking.startDateAndTime().withSecond(0).withNano(0);
                    if (now.plusMinutes(FIFTEEN).isEqual(bookingStartTime)) {
                        sendBookingEmailReminder(booking);
                    }
                });
    }

}
