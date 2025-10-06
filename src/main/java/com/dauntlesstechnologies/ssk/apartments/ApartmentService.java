package com.dauntlesstechnologies.ssk.apartments;


import com.dauntlesstechnologies.ssk.tenants.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                apartment.getRentOutstanding(),
                apartment.getOccupied(),
                apartment.getLastOccupied()
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

    //NOTE FOR INTERVIEW - USED SINGLE METHOD HERE FOR DATA INTEGRITY!!! NO MISMATCH OF DATA
    public List<Integer> occupiedOrVacantCount(){
        //Will do a run down of how many apts are occupied

        int occupiedCount = 0;
        int totalCount = 0;

        List<Apartment> apartments = apartmentRepository.findAll();

        for(Apartment apartment : apartments){
            if(tenantRepository.existsByApartmentId(apartment.getId())){
                apartment.setOccupied(true);
                //if tenant exists, keep updating last day, if not, dont modify the date
                apartment.setLastOccupied(new Date());
                apartmentRepository.save(apartment);
                occupiedCount++;

            }else{
                apartment.setOccupied(false);
                apartmentRepository.save(apartment);

            }
            totalCount++;
        }

        List<Integer> countList = new ArrayList<>();
        countList.add(occupiedCount);
        countList.add(totalCount-occupiedCount);
        return countList;

    }

    public List<ApartmentDto> getAllVacantApartments(){
        List<Apartment> apartments = apartmentRepository.findByOccupiedIsFalse();

        List<ApartmentDto> apartmentDtos = new ArrayList<>();

        for(Apartment apartment: apartments){
            apartmentDtos.add(convertEntitytoDTO(apartment));
        }

        return apartmentDtos;
    }


}
