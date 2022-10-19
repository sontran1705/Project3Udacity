package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.common.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="date")
    private LocalDate date;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "USED_ACTIVITIES",
            joinColumns = @JoinColumn(name="schedule_id")
    )
    private Set<EmployeeSkill> activities;

    @ManyToMany
    @JoinTable(name = "EMPLOYEE_SCHEDULE",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> listEmployee;

    @ManyToMany
    @JoinTable(name = "PET_SCHEDULE",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> listPet;

}
