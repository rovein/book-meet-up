package ua.nure.bookmeetup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.nure.bookmeetup.entity.MeetingRoom;

@Repository
public interface MeetingRoomRepository extends CrudRepository<MeetingRoom, Long> {

}
