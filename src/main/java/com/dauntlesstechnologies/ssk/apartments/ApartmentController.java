package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/apartments")
@CrossOrigin
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService){
        this.apartmentService = apartmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDto> getApartment(@PathVariable("id") Long id){
        return ResponseEntity.ok(apartmentService.findApartmentById(id));
    }

//    @GetMapping("/getAllVacantApartments")
//    public List<ApartmentDto> getAllVacantApartments(){
//        return apartmentService.getAllVacantApartments();
//    }

//    //Note: we are using map so that it can be converted to JSON format and sent, ex. "count" : 5
//    @GetMapping("/occupied")
//    public Map<String, Integer> getOccupied(){
//        return Collections.singletonMap("occupied", apartmentService.occupiedOrVacantCount().get(0));
//    }
//
//    @GetMapping("/vacant")
//    public Map<String, Integer> getVacant(){
//        return Collections.singletonMap("vacant", apartmentService.occupiedOrVacantCount().get(1));
//    }
//
//    @GetMapping("/getAllOutstandingRentApartments")
//    public List<ApartmentDto> getAllOutstandingRentAparmtents(){
//        return apartmentService.getAllOutstandingRentAparmtents();
//    }
//
//    @GetMapping("/getOutstandingRentAmount")
//    public Map<String, BigDecimal> getOutstandingRentAmount(){
//        return Collections.singletonMap("value", apartmentService.getOutstandingRentAmount());
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateApartment(@PathVariable("id") Long id, @RequestBody UpdateApartmentDto updateApartmentDto){
        apartmentService.updateApartmentById(id, updateApartmentDto );
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ApartmentDto> createApartment(@RequestBody UpdateApartmentDto updateApartmentDto){
       ApartmentDto apartmentDto = apartmentService.createApartment(updateApartmentDto);
       return new ResponseEntity<>(apartmentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable("id") Long id){
        apartmentService.deleteApartmentById(id);
        return ResponseEntity.noContent().build();
    }


}
