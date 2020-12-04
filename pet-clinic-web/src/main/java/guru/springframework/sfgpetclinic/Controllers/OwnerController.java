package guru.springframework.sfgpetclinic.Controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/findallcustom"})
    public String listOwners(Model model) {

        model.addAttribute("owners", ownerService.findAll());

        return "owners/index";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {

        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));

        return modelAndView;
    }

    @GetMapping("/find")
    public ModelAndView findOwners(){
        ModelAndView modelAndView = new ModelAndView("owners/findOwners");
        modelAndView.addObject(Owner.builder().build());
        return modelAndView;
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model){
        if (owner.getLastName() == null){
            owner.setLastName("");
        }

        List<Owner> ownerList = ownerService.findAllByLastNameLike(owner.getLastName());

        if (ownerList.isEmpty()) {
            result.rejectValue("lastName", "not found", "not found");
            return "owners/findOwners";
        }
        else if (ownerList.size() == 1){
            owner = ownerList.get(0);
            return "redirect:/owners/" + owner.getId();
        }
        else {
            model.addAttribute("selections" , ownerList);
            return "owners/ownersList";
        }

    }
}
