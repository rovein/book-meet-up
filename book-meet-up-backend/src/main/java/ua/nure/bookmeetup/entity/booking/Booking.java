package ua.nure.bookmeetup.entity.booking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import ua.nure.bookmeetup.entity.MeetingRoom;
import ua.nure.bookmeetup.entity.user.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    @ApiModelProperty(example = "18:00:00")
    private LocalTime time;

    @Column(name = "duration")
    private Short duration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @ToString.Exclude
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meeting_room_id")
    @ToString.Exclude
    private MeetingRoom meetingRoom;

    public LocalDateTime startDateAndTime() {
        return LocalDateTime.of(date, time);
    }

    public LocalDateTime endDateAndTime() {
        return startDateAndTime().plusMinutes(duration);
    }

    public LocalTime endTime() {
        return time.plusMinutes(duration);
    }

    public boolean isNotStarted() {
        return BookingStatus.CREATED.equals(status);
    }

    public boolean isInProgress() {
        if (BookingStatus.IN_PROGRESS.equals(status)) {
            return true;
        }
        LocalDateTime startDateAndTime = startDateAndTime();
        boolean isStarted = startDateAndTime.isBefore(LocalDateTime.now());
        boolean isInProgress = startDateAndTime.isBefore(endDateAndTime());
        return isStarted && isInProgress;
    }

    public boolean isFinished() {
        if (BookingStatus.FINISHED.equals(status)) {
            return true;
        }
        return LocalDateTime.now().isAfter(endDateAndTime());
    }

    public boolean isCanceled() {
        return BookingStatus.CANCELED.equals(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Booking that = (Booking) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
