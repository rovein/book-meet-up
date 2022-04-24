package ua.nure.bookmeetup.util;

import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.entity.booking.Booking;
import ua.nure.bookmeetup.entity.user.Employee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailNotificationSender {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private EmailNotificationSender() {
    }

    public static void sendBookingCreatedEmailNotification(Employee employee, Booking booking,
                                                           MeetingRoom meetingRoom, OfficeBuilding officeBuilding) {
        String content = EmailUtil.retrieveContentFromHtmlTemplate("email-templates/booking-created.html");
        new Thread(() -> EmailUtil.message()
                .destination(employee.getEmail())
                .subject("Створено нове бронювання кімнати")
                .body(String.format(content,
                        employee.getFirstName() + " " + employee.getLastName(),
                        DATE_FORMAT.format(booking.getDate()),
                        TIME_FORMAT.format(booking.getTime()),
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

}
