package guru.springframework.sfgpetclinic.Controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("/api")
public class APIController {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetService petService;

    public APIController(OwnerService ownerService, VetService vetService, PetService petService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petService = petService;
    }

    @RequestMapping("/vets")
    public @ResponseBody Set<Vet> getVetsJson(){
        return vetService.findAll();
    }

    @RequestMapping("/pets")
    public @ResponseBody Set<Pet> getPetsJson(){
        return petService.findAll();
    }

    @RequestMapping("/owners")
    public @ResponseBody Set<Owner> getOwnersJson(){
        return ownerService.findAll();
    }
}
