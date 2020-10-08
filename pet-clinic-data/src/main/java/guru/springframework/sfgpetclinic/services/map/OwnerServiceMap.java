package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetServices;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetServices petServices;

    public OwnerServiceMap(PetTypeService petTypeService, PetServices petServices) {
        this.petTypeService = petTypeService;
        this.petServices = petServices;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner);
    }

    @Override
    public Owner save(Owner owner) {
        Owner savedOwner = null;
        if (owner != null) {
            if (owner.getPets() != null) {
                owner.getPets().forEach(
                        pet -> {
                            if (pet.getPetType() != null) {
                                if (pet.getId() == null){
                                    pet.setPetType(petTypeService.save(pet.getPetType()));
                                }
                            }else throw new RuntimeException("Pet Type is Required");
                            if (pet.getId() == null){
                                Pet savedPet = petServices.save(pet);
                                pet.setId(savedPet.getId());
                            }
                        }
                );
            }
            return super.save(owner);
        } else return null;
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
