package com.dauntlesstechnologies.ssk.payment;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import com.dauntlesstechnologies.ssk.lease.Lease;
import com.dauntlesstechnologies.ssk.lease.LeaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApartmentRepository apartmentRepository;
    private final LeaseRepository leaseRepository;

    public PaymentService(PaymentRepository paymentRepository, ApartmentRepository apartmentRepository, LeaseRepository leaseRepository) {
        this.paymentRepository = paymentRepository;
        this.apartmentRepository = apartmentRepository;
        this.leaseRepository = leaseRepository;
    }

    public PaymentDto findPayment(Long id){
       Optional<Payment> paymentOptional = paymentRepository.findById(id);

       if(paymentOptional.isPresent()){
           Payment payment = paymentOptional.get();
           return entityToDto(payment);
       }
       else{
           throw new RuntimeException("Payment Not Found");
       }

    }

    public List<PaymentDto> findAllPayments(){
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();

            for(Payment payment : payments){
                paymentDtos.add(entityToDto(payment));
            }
            return paymentDtos;
        }

    @Transactional
    public void createPayment(UpdatePaymentDto updatePaymentDto){
        Payment payment = new Payment();
        Apartment apartment = new Apartment();

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updatePaymentDto.flatNumber());
        if(apartmentOptional.isPresent()){
            apartment = apartmentOptional.get();
        }else{
            throw new RuntimeException("APARTMENT WITH FLAT NUMBER " + apartment.getFlatNumber() + " NOT FOUND");
        }
        //Now we need to find the associated active lease with that apartment
        Optional<Lease> leaseOptional = leaseRepository.findByApartmentIdAndIsActiveTrue(apartment.getId());

        if(leaseOptional.isPresent()){payment.setLease(leaseOptional.get());} else throw new RuntimeException("LEASE NOT FOUND");

        payment.setPaymentType(updatePaymentDto.paymentType());
        payment.setPaymentAmount(updatePaymentDto.paymentAmount());
        payment.setPaymentMethod(updatePaymentDto.paymentMethod());
        payment.setComment(updatePaymentDto.comment());
        payment.setPaymentDate(updatePaymentDto.paymentDate());
        }

    public void updatePayment(Long id, UpdatePaymentDto updatePaymentDto){
        Payment payment;

        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()){
            payment = paymentOptional.get();

            Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updatePaymentDto.flatNumber());

            if (apartmentOptional.isPresent()){
                Apartment apartment = apartmentOptional.get();
                payment.setApartment(apartment);
            }
            else{
                throw new RuntimeException("No such apartment found with provided flat number");
            }

            payment.setRentAmount(updatePaymentDto.rentAmount());
            payment.setMaintenanceAmount(updatePaymentDto.maintenanceAmount());
            payment.setElectricityAmount(updatePaymentDto.electricityAmount());
            payment.setPaymentMethod(updatePaymentDto.paymentMethod());
            payment.setPaymentDate(updatePaymentDto.paymentDate());

            paymentRepository.save(payment);
        }
        else{
            throw new RuntimeException("Payment Not Found");
        }



    }

    public void deletePayment(Long id){

        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if(paymentOptional.isPresent()){
            paymentRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("No such payment record found :(");
        }


    }

    public PaymentDto entityToDto(Payment payment){
        Lease lease = payment.getLease();
        Optional<Apartment> apartmentOptional = apartmentRepository.findApartmentByLeaseId(lease.getId());

        return new PaymentDto(
                payment.getId(),
                apartmentOptional.get().getFlatNumber(),
                payment.getPaymentType(),
                payment.getPaymentAmount(),
                payment.getPaymentMethod(),
                payment.getComment(),
                payment.getPaymentDate()
        );

    }





}
