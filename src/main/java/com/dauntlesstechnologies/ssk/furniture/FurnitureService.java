package com.dauntlesstechnologies.ssk.furniture;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FurnitureService {

    private FurnitureRepository furnitureRepository;
    private ApartmentRepository apartmentRepository;

    public FurnitureService(FurnitureRepository furnitureRepository, ApartmentRepository apartmentRepository) {
        this.furnitureRepository = furnitureRepository;
        this.apartmentRepository = apartmentRepository;

    }

    public FurnitureDto getFurniture(Long id){
        Optional<Furniture> furnitureOptional = furnitureRepository.findById(id);

        if(furnitureOptional.isPresent()){
            return entityToDto(furnitureOptional.get());
        }else{
            throw new RuntimeException("No such Furniture Type Found");
        }

    }

    public List<FurnitureDto> getAllFurniture(){
        List<Furniture> furnitures = furnitureRepository.findAll();

        List<FurnitureDto> furnitureDtos = new ArrayList<>();

        for(Furniture furniture : furnitures){
            furnitureDtos.add(entityToDto(furniture));
        }

        return furnitureDtos;

    }

    public void createFurniture(UpdateFurnitureDto updateFurnitureDto){

        Furniture furniture = new Furniture();

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updateFurnitureDto.flatNumber());

        if(apartmentOptional.isPresent()){
            furniture.setApartment(apartmentOptional.get());
        }else{
            throw new RuntimeException("No such apartment found with provided flat number");
        }

        furniture.setFurnitureType(updateFurnitureDto.furnitureType());
        furniture.setQuantity(updateFurnitureDto.quantity());

        furnitureRepository.save(furniture);
    }

    public void updateFurniture(Long id, UpdateFurnitureDto updateFurnitureDto){
        Optional<Furniture> furnitureOptional = furnitureRepository.findById(id);

        if(furnitureOptional.isPresent()){
            Furniture furniture = furnitureOptional.get();

            Optional<Apartment> apartment = apartmentRepository.findByFlatNumber(updateFurnitureDto.flatNumber());
            if(apartment.isPresent()){
                furniture.setApartment(apartment.get());
            }else{
                throw new RuntimeException("No such apartment found with provided flat number");
            }


            furniture.setFurnitureType(updateFurnitureDto.furnitureType());
            furniture.setQuantity(updateFurnitureDto.quantity());

            furnitureRepository.save(furniture);

        }else{
            throw new RuntimeException("No such Furniture Record Found");
        }
    }

    public void deleteFurniture(Long id){
        Optional<Furniture> furnitureOptional = furnitureRepository.findById(id);
        if(furnitureOptional.isPresent()){
            furnitureRepository.delete(furnitureOptional.get());
        }else{
            throw new RuntimeException("No such Furniture record found");
        }
    }

    public FurnitureDto entityToDto(Furniture furniture){
        return new FurnitureDto(
                furniture.getId(),
                furniture.getApartment().getFlatNumber(),
                furniture.getFurnitureType(),
                furniture.getQuantity()
        );
    }


}
