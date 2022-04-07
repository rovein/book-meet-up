package ua.nure.bookmeetup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import ua.nure.bookmeetup.entity.booking.Booking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "meeting_room")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "meeting_room_id")
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "floor")
    private Byte floor;

    @Column(name = "info")
    private String info;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_building_id")
    @ToString.Exclude
    protected OfficeBuilding officeBuilding;

    @JsonIgnore
    @OneToMany(mappedBy = "meetingRoom", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Booking> bookings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MeetingRoom meetingRoom = (MeetingRoom) o;
        return id != null && Objects.equals(id, meetingRoom.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
