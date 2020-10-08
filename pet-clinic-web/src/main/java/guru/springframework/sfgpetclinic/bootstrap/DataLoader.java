package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetServices vetServices;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetServices vetServices, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetServices = vetServices;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        //Creating Dog Pet type
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        System.out.println("Created Dog Pet Type");

        //Creating Cat Pet Type
        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedcatPetType = petTypeService.save(cat);

        System.out.println("Created Cat Pet Type");

        //Creating Owner 1
        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Pickerel");
        owner1.setCity("Miami");
        owner1.setTelephone("123456789");

        System.out.println("Created Owner 1");

        //Giving Owner 1 a Pet
        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");
        owner1.getPets().add(mikesPet);

        System.out.println("Gave Owner 1 a Pet");

        //Saving
        ownerService.save(owner1);
        System.out.println("Owner 1 Saved");

        //Creating Owner 2
        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("123 Pickerel");
        owner2.setCity("Miami");
        owner2.setTelephone("123456789");

        System.out.println("Created Owner 1");

        //Giving Owner 2 a Pet
        Pet fionasPet = new Pet();
        fionasPet.setPetType(savedcatPetType);
        fionasPet.setOwner(owner2);
        fionasPet.setBirthDate(LocalDate.now());
        fionasPet.setName("Chessie");
        owner2.getPets().add(mikesPet);

        System.out.println("Gave Owner 2 a Pet");

        //Saving
        ownerService.save(owner2);
        System.out.println("Owner 2 Saved");

        //Creating Vet 1
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        System.out.println("Created Vet 1");

        //TODO Give Vet 1 Specialties

        //Saving Vet 1
        vetServices.save(vet1);

        System.out.println("Vet 1 Saved");

        //Creating Vet 2
        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        //TODO Give Vet 2 Specialties

        //Saving Vet 2
        vetServices.save(vet2);

        System.out.println("Vet 2 Saved");
    }
}
