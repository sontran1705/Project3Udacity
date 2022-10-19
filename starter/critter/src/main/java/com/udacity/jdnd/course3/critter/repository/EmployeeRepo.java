package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Set;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    @Query(value = "SELECT e.id, u.name, es.skills, da.days_available\n" +
            "FROM employee e INNER JOIN `user` u ON e.id = u.id\n" +
            "INNER JOIN employee_skill es ON e.id = es.employee_id\n" +
            "INNER JOIN days_available da ON e.id = da.employee_id\n" +
            "WHERE es.skills IN (:queryingSkills) AND da.days_available = COALESCE(:#{#queryingDay.toString()}, da.days_available)"
            , nativeQuery = true)
    Set<Employee> findEmployeesForService(@Param("queryingDay") DayOfWeek queryingDay,
                                          @Param("queryingSkills") Set<String> queryingSkills);
}
