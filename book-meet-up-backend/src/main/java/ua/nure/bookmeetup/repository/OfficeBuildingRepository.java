package ua.nure.bookmeetup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.nure.bookmeetup.entity.OfficeBuilding;

@Repository
public interface OfficeBuildingRepository extends CrudRepository<OfficeBuilding, Long> {

}
