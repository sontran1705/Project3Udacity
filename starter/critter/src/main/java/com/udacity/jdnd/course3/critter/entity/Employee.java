package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.common.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee extends User {

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "EMPLOYEE_SKILL",
            joinColumns=@JoinColumn(name="employee_id")
    )
    private Set<EmployeeSkill> skills;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "DAYS_AVAILABLE",
            joinColumns=@JoinColumn(name="employee_id")
    )
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "listEmployee")
    private List<Schedule> listSchedule;

}
