package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.common.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pet")
public class Pet{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="pet_type")
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Column(name = "name")
    private String name;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer owner;

    @ManyToMany(mappedBy = "listPet")
    private List<Schedule> listSchedule;
}
