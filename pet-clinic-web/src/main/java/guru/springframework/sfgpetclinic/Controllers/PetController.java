package guru.springframework.sfgpetclinic.Controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    private final static String VIEWS_CREATE_OR_UPDATE = "pets/createOrUpdatePetForm";
    private final static String VIEWS_REDIRECT_TO_OWNER = "redirect:/owners/";

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populateTypes(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId){
        return ownerService.findById(ownerId);
    }

    @InitBinder
    public void initOwnerBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public ModelAndView initCreationForm(Owner owner){
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        return new ModelAndView(VIEWS_CREATE_OR_UPDATE).addObject(pet);
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Validated Pet pet, BindingResult result, Model model) {
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.getPets().add(pet);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEWS_CREATE_OR_UPDATE;
        } else {
            petService.save(pet);
            return VIEWS_REDIRECT_TO_OWNER + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public ModelAndView initUpdateForm(@PathVariable Long petId) {
        return new ModelAndView(VIEWS_CREATE_OR_UPDATE).addObject(petService.findById(petId));
    }

    @PostMapping("/pets/{petId}/edit")
    public ModelAndView processUpdateForm(@Validated Pet pet, BindingResult result, Owner owner) {
        if (result.hasErrors()) {
            pet.setOwner(owner);
            return new ModelAndView(VIEWS_CREATE_OR_UPDATE).addObject(pet);
        } else {
            owner.getPets().add(pet);
            petService.save(pet);
            return new ModelAndView(VIEWS_REDIRECT_TO_OWNER + owner.getId());
        }
    }

}
