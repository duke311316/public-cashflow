package com.cajunenergy.green_hydrogen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solar")
public class SolarController {
    @Autowired
    private SolarService solarService;

    

}
