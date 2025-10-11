package com.dauntlesstechnologies.ssk.deposits;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final ApartmentRepository apartmentRepository;

    public DepositService(DepositRepository depositRepository,  ApartmentRepository apartmentRepository) {
        this.depositRepository = depositRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public List<DepositDto> findAllDeposits(){

        List<Deposit> deposits = depositRepository.findAll();
        List<DepositDto> depositDtos = new ArrayList<>();
        for(Deposit deposit: deposits){
            depositDtos.add(entityToDto(deposit));
        }
        return depositDtos;
    }

    public DepositDto findDepositByFlatNumber(String flatNumber){
        //WHEN I COME BACK TO CLEAN CODE: TRY TO USE A JOIN STATEMENT TO OBTAIN THE DEPOSIT
        //WITHOUT TWO DIFFERENT SQL QUERIES
        Long apartmentId;

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(flatNumber);

        if(apartmentOptional.isPresent()){
            Apartment apartment = apartmentOptional.get();
            apartmentId = apartment.getId();
        }else{
            throw new RuntimeException("No such Apartment Found");
        }

        Optional<Deposit> depositOptional = depositRepository.findByApartmentId(apartmentId);

        if(depositOptional.isPresent()){
            return entityToDto(depositOptional.get());
        }else{
            throw new RuntimeException("No such Deposit Found");
        }



    }

    //Every time this is counted, the data is updated in the apt table as well to keep it consistent
    public List<Integer> updateApartmentDepositsAndCountCollected(){

        int depositCollectedCount = 0;
        int totalCount = 0;
        List<Deposit> deposits = depositRepository.findAll();

        for(Deposit deposit: deposits){
            if(depositRepository.checkIfAptOccupied(deposit.getId())) {
                if (deposit.getPaid().compareTo(deposit.getNegotiated()) >= 0) {
                    depositCollectedCount++;

                    //access the apartment record, and set its deposit boolean to true
                    Apartment apartment = deposit.getApartment();
                    apartment.setDepositCollected(true);
                    apartmentRepository.save(apartment);
                }
                totalCount++;
            }
        }
        return List.of(depositCollectedCount,totalCount);

    }

    public DepositDto entityToDto(Deposit deposit){
        return new DepositDto(
                deposit.getId(),
                deposit.getApartment().getFlatNumber(),
                deposit.getExpected(),
                deposit.getNegotiated(),
                deposit.getPaid()
        );

    }

    public void updateDeposit(UpdateDepositDto updateDepositDto){
        Long apartmentId;

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updateDepositDto.flatNumber());

        if(apartmentOptional.isPresent()){
            Apartment apartment = apartmentOptional.get();
            apartmentId = apartment.getId();
        }else{
            throw new RuntimeException("No such Apartment Found");
        }

        Optional<Deposit> depositOptional = depositRepository.findByApartmentId(apartmentId);

        if(depositOptional.isPresent()){
            Deposit deposit = depositOptional.get();
            deposit.setNegotiated(updateDepositDto.negotiated());
            deposit.setPaid(updateDepositDto.paid());

            depositRepository.save(deposit);

        }else{
            throw new EntityNotFoundException("No such Deposit Found");
        }



    }







    }


