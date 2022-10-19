package com.udacity.jdnd.course3.critter.serivce;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    PetRepo petRepo;
    @Autowired
    CustomerRepository customerRepository;

    public CustomerDTO save(CustomerDTO customerDTO){
        Customer savingCustomer = new Customer();
        savingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        savingCustomer.setNotes(customerDTO.getNotes());
        savingCustomer.setName(customerDTO.getName());
        savingCustomer.setPetList(null);

        if(customerRepository.save(savingCustomer) != null){
            return convertCustomerEntityToDto(savingCustomer);

        }
        throw new UnsupportedOperationException("Cannot save customer");
    }

    public CustomerDTO getCustomerByPetId(long petId){
        Customer petOwnerFound = customerRepository.findCustomerByPetId(petId);
        petOwnerFound.setPetList(petRepo.getPetsByOwnerId(petOwnerFound.getId()));
        if(petOwnerFound != null){
            return convertCustomerEntityToDto(petOwnerFound);
        }
        throw new UnsupportedOperationException("Cannot find customer");
    }

    public List<CustomerDTO> getAllCustomer(){
        List<Customer> listAllCustomer = customerRepository.findAll();

        listAllCustomer.forEach(customer -> {
            customer.setPetList(petRepo.getPetsByOwnerId(customer.getId()));
        });

        List<CustomerDTO> listAllCustomerDto = new ArrayList<>();
        listAllCustomer.forEach(customer -> {

            CustomerDTO customerDTO = convertCustomerEntityToDto(customer);
            listAllCustomerDto.add(customerDTO);
        });

        return listAllCustomerDto;
    }

    public Customer getCustomerById(long customerId){
        Customer customerFound = customerRepository.findById(customerId).get();
        if(customerFound == null){
            throw new UnsupportedOperationException("Cannot find customer with this customerId");
        }
        return customerFound;
    }
    //Common method to convert a customer entity to dto
    private CustomerDTO convertCustomerEntityToDto(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());

        if(customer.getPetList() != null){
            List<Long> petIds = new ArrayList<>();
            customer.getPetList().forEach(p -> {
                petIds.add(p.getId());
            });
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }
}
