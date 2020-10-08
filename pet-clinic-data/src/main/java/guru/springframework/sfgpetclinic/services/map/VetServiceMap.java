package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Specialty;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyServices;
import guru.springframework.sfgpetclinic.services.VetServices;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetServices {

    private final SpecialtyServices specialtyServices;

    public VetServiceMap(SpecialtyServices specialtyServices) {
        this.specialtyServices = specialtyServices;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet vet) {
        super.delete(vet);
    }

    @Override
    public Vet save(Vet vet) {

        if (vet.getSpecialties().size() > 0) {
            vet.getSpecialties().forEach(
                    specialty -> {
                        if (specialty.getId() == null) {
                            Specialty savedSpecialty = specialtyServices.save(specialty);
                            specialty.setId(savedSpecialty.getId());
                        }
                    });
        }

        return super.save(vet);
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}