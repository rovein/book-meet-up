package ua.nure.bookmeetup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ua.nure.bookmeetup.entity.OfficeBuilding;
import ua.nure.bookmeetup.entity.user.Employee;

import java.util.List;

@Repository
public interface OfficeBuildingRepository extends CrudRepository<OfficeBuilding, Long> {

    @NonNull
    List<OfficeBuilding> findAll();

}
