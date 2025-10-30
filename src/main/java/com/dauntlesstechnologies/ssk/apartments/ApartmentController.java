package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/apartments")
@CrossOrigin
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService){
        this.apartmentService = apartmentService;
    }

    @GetMapping("/{id}")
    public ApartmentDto getApartment(@PathVariable("id") Long id){
        return apartmentService.findApartmentById(id);
    }

    @GetMapping("/getAllVacantApartments")
    public List<ApartmentDto> getAllVacantApartments(){
        return apartmentService.getAllVacantApartments();
    }

    //Note: we are using map so that it can be converted to JSON format and sent, ex. "count" : 5
    @GetMapping("/occupied")
    public Map<String, Integer> getOccupied(){
        return Collections.singletonMap("occupied", apartmentService.occupiedOrVacantCount().get(0));
    }

    @GetMapping("/vacant")
    public Map<String, Integer> getVacant(){
        return Collections.singletonMap("vacant", apartmentService.occupiedOrVacantCount().get(1));
    }

    @GetMapping("/getAllOutstandingRentApartments")
    public List<ApartmentDto> getAllOutstandingRentAparmtents(){
        return apartmentService.getAllOutstandingRentAparmtents();
    }

    @GetMapping("/getOutstandingRentAmount")
    public Map<String, BigDecimal> getOutstandingRentAmount(){
        return Collections.singletonMap("value", apartmentService.getOutstandingRentAmount());
    }

    @PutMapping("/update/{id}")
    public void updateApartment(@PathVariable("id") Long id, @RequestBody UpdateApartmentDto updateApartmentDto){
        apartmentService.updateApartmentById(id, updateApartmentDto );
    }

    @PostMapping("/create")
    public void createApartment(@RequestBody UpdateApartmentDto updateApartmentDto){
        apartmentService.createApartment(updateApartmentDto);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteApartment(@PathVariable("id") Long id){
        apartmentService.deleteApartment(id);
    }




    /*
    C - DONE
    R - DONE
    U - DONE
    D - DONE
     */

}
