package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (petTypeService.findAll().isEmpty()) {
            LoadData();
        }

    }

    private void LoadData() {
        //Creating Dog Pet type
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        System.out.println("Created Dog Pet Type");

        //Creating Cat Pet Type
        PetType cat = new PetType();
        cat.setName("Cat");
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

        System.out.println("Created Owner 2");

        //Giving Owner 2 a Pet
        Pet fionasPet = new Pet();
        fionasPet.setPetType(savedcatPetType);
        fionasPet.setOwner(owner2);
        fionasPet.setBirthDate(LocalDate.now());
        fionasPet.setName("Chessie");
        owner2.getPets().add(fionasPet);

        System.out.println("Gave Owner 2 a Pet");

        //Saving Owner 2
        ownerService.save(owner2);
        System.out.println("Owner 2 Saved");

        //Visiting Owner 2 Pet
        Visit chessieVisit = new Visit();
        chessieVisit.setPet(fionasPet);
        chessieVisit.setDate(LocalDate.now());
        chessieVisit.setDescription("He is All Fine");

        System.out.println("Visit for Owner 2 Pet Created");

        //Saving Owner 2 Pet Visit
        visitService.save(chessieVisit);
        System.out.println("Saved Owner 2 Pet Visit");

        //Creating Specialties
        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");

        Specialty surgery = new Specialty();
        radiology.setDescription("Surgery");

        Specialty dentistry = new Specialty();
        radiology.setDescription("Dentistry");

        System.out.println("Created Specialties");

        //Saving Specialties
        Specialty savedRadiology = specialtyService.save(radiology);
        Specialty savedSurgery = specialtyService.save(surgery);
        Specialty savedDentistry = specialtyService.save(dentistry);

        System.out.println("Saved Specialties");

        //Creating Vet 1
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        System.out.println("Created Vet 1");

        //Giving Vet 1 Specialties
        vet1.getSpecialties().add(savedRadiology);

        System.out.println("Gave Vet 1 Specialties");

        //Saving Vet 1
        vetService.save(vet1);

        System.out.println("Vet 1 Saved");

        //Creating Vet 2
        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        //Giving Vet 2 Specialties
        vet2.getSpecialties().add(savedSurgery);

        System.out.println("Gave Vet 2 Specialties");

        //Saving Vet 2
        vetService.save(vet2);

        System.out.println("Vet 2 Saved");

    }
}
