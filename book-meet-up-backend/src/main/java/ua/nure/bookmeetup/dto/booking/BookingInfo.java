package ua.nure.bookmeetup.dto.booking;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BookingInfo {

    Long getId();

    LocalDate getDate();

    LocalTime getTime();

    Short getDuration();

    String getStatus();

    Integer getNumber();

    Byte getFloor();

    String getInfo();

    String getCity();

    String getStreet();

    String getHouse();

    String getName();

    Long getMeetingRoomId();

}
