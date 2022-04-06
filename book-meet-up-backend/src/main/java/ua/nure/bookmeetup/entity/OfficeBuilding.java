package ua.nure.bookmeetup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "office_building")
@Getter
@Setter
@Accessors(chain = true)
public class OfficeBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "office_building_id")
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "officeBuilding", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<MeetingRoom> meetingRooms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OfficeBuilding officeBuilding = (OfficeBuilding) o;
        return id != null && Objects.equals(id, officeBuilding.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
