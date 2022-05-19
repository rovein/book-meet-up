package ua.nure.bookmeetup.util;

import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.entity.booking.Booking;
import ua.nure.bookmeetup.entity.user.Employee;

import java.time.format.DateTimeFormatter;

public class EmailNotificationSender {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private EmailNotificationSender() {
    }

    public static void sendBookingCreatedEmailNotification(Employee employee, Booking booking, MeetingRoom meetingRoom) {
        sendBookingNotification("email-templates/booking-created.html", "Створено нове бронювання кімнати",
                employee, booking, meetingRoom);
    }

    public static void sendBookingEmailInvitation(Employee employee, Booking booking) {
        sendBookingNotification("email-templates/meeting-invitation.html", "Запрошення на переговорну зустріч",
                employee, booking, booking.getMeetingRoom());
    }

    public static void sendBookingEmailCancelNotification(Employee employee, Booking booking) {
        sendBookingNotification("email-templates/booking-canceled.html", "Зустріч було скасовано",
                employee, booking, booking.getMeetingRoom());
    }

    public static void sendBookingEmailReminder(Booking booking) {
        sendBookingNotification("email-templates/meeting-reminder.html", "Нагадування про переговорну зустріч",
                booking.getEmployee(), booking, booking.getMeetingRoom());
    }

    private static void sendBookingNotification(String pathToTemplate, String subject, Employee employee,
                                                Booking booking, MeetingRoom meetingRoom) {
        String content = EmailUtil.retrieveContentFromHtmlTemplate(pathToTemplate);
        OfficeBuilding officeBuilding = meetingRoom.getOfficeBuilding();
        new Thread(() -> EmailUtil.message()
                .destination(employee.getEmail())
                .subject(subject)
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
                        officeBuilding.getHouse(),
                        employee.getId()
                ))
                .send()
        ).start();
    }

}
