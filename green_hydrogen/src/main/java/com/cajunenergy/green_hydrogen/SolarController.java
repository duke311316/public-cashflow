package com.cajunenergy.green_hydrogen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solar")
public class SolarController {
    @Autowired
    private SolarService solarService;

    @PostMapping("/calculate_cost_per_kilogram")
    public Double calculateCostPerKilo(
        @RequestParam Double costPerWattSolar, 
        @RequestParam Double solarEfficiency, 
        @RequestParam Integer costPerKilowattElectrolyzer, 
        @RequestParam String typeOfElectrolyzer,
        @RequestParam Integer goalKilogramsPerYear,
        @RequestParam String countryOfOrigin){

            Integer systemDailyWattage = solarService.calculateSolarWattage(goalKilogramsPerYear);
            Double solarPanelCost = solarService.calculateSolarPanelTotalPrice(costPerWattSolar, systemDailyWattage);
            Double taxAndTariff = solarService.addTaxAndTariff(countryOfOrigin);
            Double panelCostIncludingTaxAndtariff = (1 + taxAndTariff) * solarPanelCost;

            return panelCostIncludingTaxAndtariff;


    }
    

    

}
