package ua.nure.bookmeetup.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.dto.booking.BookingInfo;
import ua.nure.bookmeetup.dto.booking.BookingInfoDto;
import ua.nure.bookmeetup.dto.booking.BookingRequestDto;
import ua.nure.bookmeetup.dto.booking.BookingResponseDto;
import ua.nure.bookmeetup.dto.mapper.BookingMapper;
import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.entity.booking.Booking;
import ua.nure.bookmeetup.entity.booking.BookingStatus;
import ua.nure.bookmeetup.entity.user.Employee;
import ua.nure.bookmeetup.exception.EntityNotFoundException;
import ua.nure.bookmeetup.repository.EmployeeRepository;
import ua.nure.bookmeetup.repository.MeetingRoomRepository;
import ua.nure.bookmeetup.repository.BookingRepository;
import ua.nure.bookmeetup.service.BookingService;
import ua.nure.bookmeetup.util.EmailUtil;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ua.nure.bookmeetup.entity.booking.BookingStatus.CREATED;
import static ua.nure.bookmeetup.entity.booking.BookingStatus.IN_PROGRESS;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_BOOKING_BY_ID;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_EMPLOYEE_BY_ID;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_MEETING_ROOM_BY_ID;

@Service
@Log4j2
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final MeetingRoomRepository meetingRoomRepository;

    private final EmployeeRepository employeeRepository;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              EmployeeRepository employeeRepository,
                              MeetingRoomRepository meetingRoomRepository) {
        this.bookingRepository = bookingRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public BookingResponseDto createSingle(BookingRequestDto bookingRequestDto) {
        Booking booking = new Booking();

        Employee employee = employeeRepository
                .findById(bookingRequestDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_EMPLOYEE_BY_ID));
        MeetingRoom meetingRoom = meetingRoomRepository
                .findById(bookingRequestDto.getMeetingRoomId())
                .orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_MEETING_ROOM_BY_ID));

        booking.setEmployee(employee);
        booking.setMeetingRoom(meetingRoom);
        booking.setDate(bookingRequestDto.getDate());
        booking.setTime(bookingRequestDto.getTime());
        booking.setDuration(bookingRequestDto.getDuration());
        booking.setStatus(CREATED);

        Booking createdBooking = bookingRepository.save(booking);
        sendEmailNotification(employee, createdBooking, meetingRoom, meetingRoom.getOfficeBuilding());

        return BookingMapper.toBookingResponseDto(createdBooking);
    }

    private void sendEmailNotification(Employee employee, Booking booking,
                                       MeetingRoom meetingRoom, OfficeBuilding officeBuilding) {
        String content = EmailUtil.retrieveContentFromHtmlTemplate("email-templates/booking-created.html");
        LocalDateTime creationDate = LocalDateTime.now();
        new Thread(() -> EmailUtil.message()
                .destination(employee.getEmail())
                .subject("Створено нове бронювання кімнати")
                .body(String.format(content,
                        employee.getFirstName() + " " + employee.getLastName(),
                        dateFormat.format(creationDate),
                        timeFormat.format(creationDate),
                        booking.getDuration(),
                        meetingRoom.getNumber(),
                        meetingRoom.getFloor(),
                        officeBuilding.getName(),
                        officeBuilding.getCity(),
                        officeBuilding.getStreet(),
                        officeBuilding.getHouse()
                ))
                .send()
        ).start();
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
    public List<BookingInfoDto> getAllBookingsByEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(Employee::getId)
                .map(bookingRepository::getAllBookingsByEmployee)
                .map(BookingMapper::toBookingInfoDto)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<BookingInfoDto> getAllBookingsByMeetingRoom(Long meetingRoomId) {
        return meetingRoomRepository.findById(meetingRoomId)
                .map(MeetingRoom::getId)
                .map(bookingRepository::getAllBookingsByMeetingRoom)
                .map(BookingMapper::toBookingInfoDto)
                .orElse(Collections.emptyList());
    }

    @Override
    public BookingInfo getBookingInfoById(Long bookingId) {
        return Optional.of(findById(bookingId))
                .map(booking -> bookingRepository.getBookingInfoById(bookingId))
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
        bookingRepository.findById(bookingId).ifPresent(booking -> booking.setStatus(BookingStatus.CANCELED));
    }

}
