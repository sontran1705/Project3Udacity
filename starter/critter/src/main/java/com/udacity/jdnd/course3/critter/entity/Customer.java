package com.udacity.jdnd.course3.critter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer extends User {

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="notes")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Pet> petList;
}
