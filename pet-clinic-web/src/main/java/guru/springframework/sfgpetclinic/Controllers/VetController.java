package guru.springframework.sfgpetclinic.Controllers;

import guru.springframework.sfgpetclinic.services.VetServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

    private final VetServices vetServices;

    public VetController(VetServices vetServices) {
        this.vetServices = vetServices;
    }

    @RequestMapping({"/vets", "/vets/index", "/vets/vets.index"})
    public String listVets(Model model) {
        model.addAttribute("vets",vetServices.findAll());
        return "vets/index";
    }

}
