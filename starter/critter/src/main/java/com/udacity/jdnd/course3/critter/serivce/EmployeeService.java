package com.udacity.jdnd.course3.critter.serivce;

import com.udacity.jdnd.course3.critter.common.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    public EmployeeDTO save(EmployeeDTO employeeDTO){
        Employee savingEmployee = new Employee();
        savingEmployee.setName(employeeDTO.getName());
        savingEmployee.setSkills(employeeDTO.getSkills());
        savingEmployee.setDaysAvailable(employeeDTO.getDaysAvailable());
        savingEmployee.setListSchedule(null);

        if(employeeRepo.save(savingEmployee) != null){
            employeeDTO.setId(savingEmployee.getId());
            return employeeDTO;
        }
        throw new UnsupportedOperationException("Cannot save customer");
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<Employee> lstEmpl = employeeRepo.findAll();
        List<EmployeeDTO> lstEmplDto = new ArrayList<>();
        if(lstEmpl != null || lstEmpl.size() != 0){
            lstEmpl.forEach(empl -> {
                lstEmplDto.add(convertEmployeeEntityToDto(empl));
            });
        }
        return lstEmplDto;
    }

    public EmployeeDTO getEmployeeById(long empId){
        Employee employeeFound = employeeRepo.findById(empId).orElse(null);
        if(employeeFound != null){
            return convertEmployeeEntityToDto((employeeFound));
        }
        throw new UnsupportedOperationException("Cannot find employee");
    }

    public void setAvailabilityDayOfEmployee(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employeeFound = employeeRepo.findById(employeeId).orElse(null);
        if(employeeFound != null){
            employeeFound.setDaysAvailable(daysAvailable);
            employeeRepo.save(employeeFound);
        }
        else throw new UnsupportedOperationException("Cannot find employee");
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO){
        DayOfWeek queryingDay = employeeRequestDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> queryingSkills = employeeRequestDTO.getSkills();

        Set<String> skillsString = new HashSet<>();
        queryingSkills.forEach(employeeSkill -> {
            skillsString.add(employeeSkill.toString());
        });

        Set<Employee> listEmployeeFound = employeeRepo.findEmployeesForService(queryingDay, skillsString);
        listEmployeeFound.forEach(employee -> {
            System.out.println(employee.toString());
        });
        List<EmployeeDTO> listEmployeeDtoFound = new ArrayList<>();
        if(listEmployeeFound != null && listEmployeeFound.size() != 0){
            listEmployeeFound.forEach(employee -> {
                if(employee.getSkills().containsAll(queryingSkills)){
                    listEmployeeDtoFound.add(convertEmployeeEntityToDto(employee));
                }
            });
            return listEmployeeDtoFound;
        }
        throw new UnsupportedOperationException("Cannot find any employee with this request");

    }

    private EmployeeDTO convertEmployeeEntityToDto(Employee employee){
        EmployeeDTO employeeDto = new EmployeeDTO();
        employeeDto.setName(employee.getName());
        employeeDto.setId(employee.getId());
        employeeDto.setSkills(employee.getSkills());
        employeeDto.setDaysAvailable(employee.getDaysAvailable());

        return employeeDto;
    }

}
