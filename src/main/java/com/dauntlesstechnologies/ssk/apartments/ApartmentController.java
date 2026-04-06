package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getApartmentStatistics() {
        return ResponseEntity.ok(apartmentService.getApartmentStatistics());
    }

    @GetMapping("/getAllOccupied")
    public ResponseEntity<List<ApartmentDto>> getAllOccupiedApartments() {
        return ResponseEntity.ok(apartmentService.findAllOccupiedApartments());
    }


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
