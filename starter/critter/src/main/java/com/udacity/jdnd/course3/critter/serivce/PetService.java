package com.udacity.jdnd.course3.critter.serivce;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepo petRepo;

    public PetDTO save(PetDTO petDTO){
        Pet savingPet = new Pet();
        savingPet.setName(petDTO.getName());
        savingPet.setNotes(petDTO.getNotes());
        savingPet.setPetType(petDTO.getPetType());
        savingPet.setBirthDate(petDTO.getBirthDate());
        Customer customer = new Customer();
        customer.setId(petDTO.getOwnerId());

        savingPet.setOwner(customer);

        if(petRepo.save(savingPet) != null){
            return convertPetEntityToDto(savingPet);
        }
        throw new UnsupportedOperationException("Cannot save pet");
    }

    public PetDTO getPetById(long petId){
        Pet pet = petRepo.findById(petId).orElse(null);
        if(pet != null){
            return convertPetEntityToDto(pet);
        }
        throw new UnsupportedOperationException("Cannot find pet with this petId");
    }

    public List<PetDTO> getAllPet() {
        List<Pet> listAllPet = petRepo.findAll();
        List<PetDTO> listAllPetDto = new ArrayList<>();
        if(listAllPet != null || listAllPet.size() != 0){
            listAllPet.forEach(pet -> {
                listAllPetDto.add(convertPetEntityToDto(pet));
            });
        }
        return listAllPetDto;
    }

    public List<PetDTO> getPetsByOwner(long ownerId){
        List<Pet> listAllPetFound = petRepo.getPetsByOwnerId(ownerId);
        List<PetDTO> listAllPetDto = new ArrayList<>();
        if(listAllPetFound != null || listAllPetFound.size() != 0){
            listAllPetFound.forEach(pet -> {
                listAllPetDto.add( convertPetEntityToDto(pet));
            });
        }
        return listAllPetDto;
    }

    private PetDTO convertPetEntityToDto(Pet pet){
        PetDTO petDTO = null;
        if(pet != null){
            petDTO = new PetDTO();
            petDTO.setId(pet.getId());
            petDTO.setPetType(pet.getPetType());
            petDTO.setName(pet.getName());
            petDTO.setOwnerId(pet.getOwner().getId());
            petDTO.setBirthDate(pet.getBirthDate());
            petDTO.setNotes(pet.getNotes());
        }
        return petDTO;
    }
}
