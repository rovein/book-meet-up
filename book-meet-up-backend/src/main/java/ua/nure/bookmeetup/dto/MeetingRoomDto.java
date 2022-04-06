package ua.nure.bookmeetup.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MeetingRoomDto {

    private Long id;

    private Integer number;

    private Byte floor;

    private String info;

}
