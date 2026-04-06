package com.dauntlesstechnologies.ssk.apartments;


import com.dauntlesstechnologies.ssk.configuration.ConfigurationRepository;
import com.dauntlesstechnologies.ssk.lease.Lease;
import com.dauntlesstechnologies.ssk.lease.LeaseRepository;
import com.dauntlesstechnologies.ssk.tenants.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final LeaseRepository leaseRepository;

    public ApartmentService(ApartmentRepository apartmentRepository, TenantRepository tenantRepository, ConfigurationRepository configurationRepository, LeaseRepository leaseRepository) {
        this.apartmentRepository = apartmentRepository;
        this.leaseRepository = leaseRepository;
    }

    public Map<String, Long> getApartmentStatistics() {
        long totalApartments = apartmentRepository.count();
        long occupiedApartments = leaseRepository.countOccupiedApartments();
        long vacantApartments = totalApartments - occupiedApartments;

        Map<String, Long> stats = new HashMap<>();
        stats.put("occupied", occupiedApartments);
        stats.put("vacant", vacantApartments);
        stats.put("total", totalApartments);

        return stats;
    }

    @Transactional
    public ApartmentDto createApartment(UpdateApartmentDto updateApartmentDto){
        Apartment apartment = new Apartment();
        apartment.setFlatNumber(updateApartmentDto.flatNumber());
        apartmentRepository.save(apartment);
        return apartmentToDTO(apartment);
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

    @Transactional
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

    public void deleteApartmentById(Long id){
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);

        if(apartmentOptional.isPresent()){
            Apartment apartment = apartmentOptional.get();
            //Idea is to see if there is an active lease, if not, we can safely delete the apartment without dependency issues
            Optional<Lease> leaseOptional = leaseRepository.findByApartmentIdAndIsActiveTrue(apartment.getId());
            if(!leaseOptional.isPresent()){
                apartmentRepository.delete(apartment);
            }else{
                throw new RuntimeException("CANNOT DELETE THE APARTMENT, THERE IS AN ACTIVE LEASE ASSOCIATED, PLEASE TERMINATE THE LEASE FIRST");
            }
        }

    }

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

}
