package com.dauntlesstechnologies.ssk.apartments;


import com.dauntlesstechnologies.ssk.configuration.ConfigurationRepository;
import com.dauntlesstechnologies.ssk.lease.Lease;
import com.dauntlesstechnologies.ssk.lease.LeaseRepository;
import com.dauntlesstechnologies.ssk.tenants.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final TenantRepository tenantRepository;
    private final ConfigurationRepository configurationRepository;
    private final LeaseRepository leaseRepository;

    public ApartmentService(ApartmentRepository apartmentRepository, TenantRepository tenantRepository, ConfigurationRepository configurationRepository, LeaseRepository leaseRepository) {
        this.apartmentRepository = apartmentRepository;
        this.tenantRepository = tenantRepository;
        this.configurationRepository = configurationRepository;
        this.leaseRepository = leaseRepository;
    }

    public void createApartment(UpdateApartmentDto updateApartmentDto){
        Apartment apartment = new Apartment();
        apartment.setFlatNumber(updateApartmentDto.flatNumber());
        apartmentRepository.save(apartment);
    }

    public ApartmentDto findApartmentById(Long id){
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);

        if(apartmentOptional.isPresent()){
             return apartmentToDTO(apartmentOptional.get());
        }
        else {
            throw new RuntimeException("NO SUCH APARTMENT FOUND WITH THIS ID");
        }

    }

//    public List<ApartmentDto> getAllOutstandingRentAparmtents(){
//
//    }

    public ApartmentDto apartmentToDTO(Apartment apartment){
        boolean occupied = false;
        Date lastOccupied = new Date();

        Optional<Lease> leaseOptional = leaseRepository.findByApartmentIdAndIsActiveTrue(apartment.getId());
        if(leaseOptional.isPresent()){
            occupied = true;
        }

        Optional<Date> lastOccupiedOptional = leaseRepository.findLastOccupiedDateByApartmentId(apartment.getId());

        if(lastOccupiedOptional.isPresent()){
            lastOccupied = lastOccupiedOptional.get();
        }else{
            lastOccupied = null;
        }

        return new ApartmentDto(
                apartment.getFlatNumber(),
                occupied,
                lastOccupied
        );
    }

    public void updateApartmentById(Long id, UpdateApartmentDto updateApartmentDto){
        Apartment apartment;

        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);

        if(apartmentOptional.isPresent()){
            apartment = apartmentOptional.get();
            apartment.setFlatNumber(updateApartmentDto.flatNumber());
            apartmentRepository.save(apartment);
        }
        else {
            throw new RuntimeException("NO SUCH APT FOUND WITH GIVEN ID");
        }

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
            apartmentDtos.add(apartmentToDTO(apartment));
        }

        return apartmentDtos;
    }


}
