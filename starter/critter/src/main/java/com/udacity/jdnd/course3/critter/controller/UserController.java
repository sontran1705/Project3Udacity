package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.serivce.CustomerService;
import com.udacity.jdnd.course3.critter.serivce.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.save(customerDTO);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return customerService.getCustomerByPetId(petId);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.save(employeeDTO);
    }

    @GetMapping("/getAllEmpl")
    public List<EmployeeDTO> getPets(){
        return employeeService.getAllEmployee();
    }
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailabilityDayOfEmployee(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.findEmployeesForService(employeeDTO);
    }

}
