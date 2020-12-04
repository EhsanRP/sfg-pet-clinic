package guru.springframework.sfgpetclinic.Controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;


    //VIEWS
    private static final String VIEWS_CREATE_OR_UPDATE = "owners/createOrUpdateOwnerForm";
    private static final String VIEWS_FIND_OWNER = "owners/findOwners";
    private static final String VIEWS_OWNERS_DETAILS = "owners/ownerDetails";
    private static final String VIEWS_OWNERS_LIST = "owners/ownersList";
    private static final String VIEWS_REDIRECT = "redirect:/owners/";

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {

        ModelAndView modelAndView = new ModelAndView(VIEWS_OWNERS_DETAILS);
        modelAndView.addObject(ownerService.findById(ownerId));

        return modelAndView;
    }

    @GetMapping("/find")
    public ModelAndView initFindOwners() {
        ModelAndView modelAndView = new ModelAndView(VIEWS_FIND_OWNER);
        modelAndView.addObject(Owner.builder().build());
        return modelAndView;
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> ownerList = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if (ownerList.isEmpty()) {
            result.rejectValue("lastName", "not found", "not found");
            return VIEWS_FIND_OWNER;
        } else if (ownerList.size() == 1) {
            owner = ownerList.get(0);
            return VIEWS_REDIRECT + owner.getId();
        } else {
            model.addAttribute("selections", ownerList);
            return VIEWS_OWNERS_LIST;
        }
    }

    @GetMapping("/new")
    public ModelAndView initCreationForm() {
        return new ModelAndView(VIEWS_CREATE_OR_UPDATE).addObject(Owner.builder().build());
    }

    @PostMapping("/new")
    public String processCreationForm(@Validated Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return VIEWS_CREATE_OR_UPDATE;
        }
        else {
            Owner savedOwner = ownerService.save(owner);
            return VIEWS_REDIRECT + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public ModelAndView initUpdateForm(@PathVariable Long ownerId){
        return new ModelAndView(VIEWS_CREATE_OR_UPDATE).addObject(ownerService.findById(ownerId));
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@PathVariable Long ownerId , @Validated Owner owner, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return VIEWS_CREATE_OR_UPDATE;
        }
        else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return VIEWS_REDIRECT + savedOwner.getId();
        }

    }

}
