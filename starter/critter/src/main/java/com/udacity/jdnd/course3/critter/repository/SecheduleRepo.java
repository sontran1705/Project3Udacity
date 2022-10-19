package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecheduleRepo extends JpaRepository<Schedule,Long> {

    @Query(value = "SELECT s FROM Schedule s INNER JOIN s.listPet l WHERE :petId IN (l.id)")
    List<Schedule> getSchedulesByPetId(@Param("petId") long petId);
    @Query(value = "SELECT s FROM Schedule s INNER JOIN s.listEmployee l WHERE :employeeId IN (l.id)")
    List<Schedule> getSchedulesByEmployeeId(@Param("employeeId") long employeeId);
    @Query(value = "SELECT s FROM Schedule s INNER JOIN s.listPet l WHERE :customerId IN (l.owner.id)")
    List<Schedule> getSchedulesByCustomerId(@Param("customerId") long customerId);
}
