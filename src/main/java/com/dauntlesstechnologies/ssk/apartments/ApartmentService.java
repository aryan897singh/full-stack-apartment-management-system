package com.dauntlesstechnologies.ssk.apartments;


import com.dauntlesstechnologies.ssk.tenants.TenantRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<ApartmentDto> getAllOutstandingRentAparmtents(){
        List<Apartment> apartments = apartmentRepository.findApartmentsWithOutstandingRent();
        List<ApartmentDto> apartmentDtos = new ArrayList<>();

        for(Apartment apartment : apartments){
            Long aptId = apartment.getId();
            if(tenantRepository.existsById(aptId)){
                apartmentDtos.add(convertEntitytoDTO(apartment));
            }
        }
        return apartmentDtos;
    }

    public BigDecimal getOutstandingRentAmount(){
        List<Apartment> apartments = apartmentRepository.findApartmentsWithOutstandingRent();

        BigDecimal outstandingRent = BigDecimal.ZERO;

        for(int i = 0; i < apartments.size(); i++){

            if(apartments.get(i).getOccupied()){
                BigDecimal outstandingMaintenance = apartments.get(i).getMaintenanceAmount().subtract( apartments.get(i).getPaidMaintenance());
                BigDecimal outstandingRentPay = apartments.get(i).getRentAmount().subtract( apartments.get(i).getPaidRent());

                BigDecimal thisOutstandingRent = outstandingMaintenance.add(outstandingRentPay);
                outstandingRent = outstandingRent.add(thisOutstandingRent);
            }

        }

        return outstandingRent;

    }

    public ApartmentDto convertEntitytoDTO(Apartment apartment){
        return new ApartmentDto(
                apartment.getId(),
                apartment.getFlatNumber(),
                apartment.getExpectedRent(),
                apartment.getRentAmount(),
                apartment.getMaintenanceAmount(),
                apartment.getPaidMaintenance(),
                apartment.getPaidRent(),
                apartment.getOccupied(),
                apartment.getLastOccupied(),
                apartment.getDepositCollected()
        );
    }

    public void updateApartmentById(Long id, UpdateApartmentDto updateApartmentDto){
        Apartment apartment;

        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);

        if(apartmentOptional.isPresent()){
             apartment = apartmentOptional.get();
            apartment.setFlatNumber(updateApartmentDto.flatNumber());
            apartment.setExpectedRent(updateApartmentDto.expectedRent());
            apartment.setRentAmount(updateApartmentDto.rentAmount());
            apartment.setMaintenanceAmount(updateApartmentDto.maintenanceAmount());
            apartment.setPaidMaintenance(updateApartmentDto.paidMaintenance());
            apartment.setPaidRent(updateApartmentDto.paidRent());
            apartmentRepository.save(apartment);
        }
        else {
            throw new RuntimeException("NO SUCH APT FOUND WITH GIVEN ID");
        }

    }

    public void createApartment(UpdateApartmentDto updateApartmentDto){
        Apartment apartment = new Apartment();

        apartment.setFlatNumber(updateApartmentDto.flatNumber());
        apartment.setExpectedRent(updateApartmentDto.expectedRent());
        apartment.setRentAmount(updateApartmentDto.rentAmount());
        apartment.setMaintenanceAmount(updateApartmentDto.maintenanceAmount());
        apartment.setPaidMaintenance(updateApartmentDto.paidMaintenance());
        apartment.setPaidRent(updateApartmentDto.paidRent());
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

    //THIS METHOD IS USED TO RESET THE PAYMENT AMOUNT EVERY MONTH FOR ADMIN TO ADD NEW MONTH PAYMENT
    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetMonthlyPayments(){
        System.out.println("BEGINNING MONTHLY PAYMENT RESET ...");

        List<Apartment> apartments = apartmentRepository.findAll();

        for(Apartment apartment : apartments){
            apartment.setPaidMaintenance(BigDecimal.ZERO);
            apartment.setPaidRent(BigDecimal.ZERO);

            apartmentRepository.save(apartment);
        }

        System.out.println("MONTHLY PAYMENT RESET COMPLETE :)");

    }


}
