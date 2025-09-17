package com.dauntlesstechnologies.ssk.apartments;


import com.dauntlesstechnologies.ssk.tenants.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final TenantRepository tenantRepository;

    public ApartmentService(ApartmentRepository apartmentRepository, TenantRepository tenantRepository){
        this.apartmentRepository = apartmentRepository;
        this.tenantRepository = tenantRepository;
    }

    public ApartmentDto findApartmentById(Long id){
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);

        if(apartmentOptional.isPresent()){
             return convertEntitytoDTO(apartmentOptional.get());
        }
        else {
            throw new RuntimeException("NO SUCH APARTMENT FOUND WITH THIS ID");
        }

    }

    public ApartmentDto convertEntitytoDTO(Apartment apartment){
        return new ApartmentDto(
                apartment.getId(),
                apartment.getFlatNumber(),
                apartment.getRentAmount(),
                apartment.getRentOutstanding()
        );
    }

    public void updateApartmentById(Long id, UpdateApartmentDto updateApartmentDto){
        Apartment apartment;

        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);

        if(apartmentOptional.isPresent()){
             apartment = apartmentOptional.get();
        }
        else {
            throw new RuntimeException("NO SUCH APT FOUND WITH GIVEN ID");
        }

        apartment.setFlatNumber(updateApartmentDto.flatNumber());
        apartment.setRentOutstanding(updateApartmentDto.rentOutstanding());
        apartment.setRentAmount(updateApartmentDto.rentAmount());

        apartmentRepository.save(apartment);

    }

    public void createApartment(UpdateApartmentDto updateApartmentDto){
        Apartment apartment = new Apartment();

        apartment.setFlatNumber(updateApartmentDto.flatNumber());
        apartment.setRentAmount(updateApartmentDto.rentAmount());
        apartment.setRentOutstanding(updateApartmentDto.rentOutstanding());

        apartmentRepository.save(apartment);
    }

    //Making sure no one is living before deleting
    public void deleteApartment(Long id){

        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);
        Apartment apartment = new Apartment();


        if(apartmentOptional.isPresent()){
            if(tenantRepository.existsByApartmentId(id)){
                throw new RuntimeException("CANNOT DELETE SINCE TENANT EXISTS, PLEASE REMOVE EXISTING TENANTS");
            }
            else {
                apartmentRepository.deleteById(id);
            }
        }
        else{
            throw new RuntimeException("NO SUCH APARTMENT FOUND WITH GIVEN ID TO DELETE");
        }

    }


}
