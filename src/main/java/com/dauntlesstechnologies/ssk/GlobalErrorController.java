package com.dauntlesstechnologies.ssk;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GlobalErrorController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; //This tells ThymeLeaf to look for access-denied.html when this
        //API endpoint is called
    }
}
