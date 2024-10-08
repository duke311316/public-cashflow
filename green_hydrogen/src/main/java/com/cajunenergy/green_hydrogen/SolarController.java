package com.cajunenergy.green_hydrogen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solar")
@CrossOrigin("http://localhost:3000")
public class SolarController {
    @Autowired
    private SolarService solarService;

    @PostMapping("/calculate_solar_hydro_plant_cost")
    public ResponseEntity<String> calculateSolarHydroPlantCost(@RequestBody SolarPlantCostRequest solarPanelCostrequest) {
        try {

            // Integer systemPanelWattageNeeded = solarService.calculateSolarWattage(solarPanelCostrequest.getGoalKilogramsPerYear(), solarPanelCostrequest.getSolarEfficiency(), solarPanelCostrequest.getTypeOfElectrolyzer());
            // Double solarPanelCost = solarService.calculateSolarPanelTotalPrice(solarPanelCostrequest.getCostPerWattSolar(), systemPanelWattageNeeded);
            // Double taxAndTariff = solarService.addTaxAndTariff(solarPanelCostrequest.getCountryOfOrigin());
            // Double costOfPanelsIncludingTaxAndTariff = (1 + taxAndTariff) * solarPanelCost;
            // Integer numberOfPanels = solarService.calculateNumberOfPanels(solarPanelCostrequest.getWattsPerPanel(), systemPanelWattageNeeded);
            
            // Integer numberOfConnections = numberOfPanels;
            // Integer panelConnectionCost = numberOfConnections * solarPanelCostrequest.getCostPerPanelConnection().intValue();
            // Integer panelConnectionCostIncludingTaxAndTariff = (int)((1 + taxAndTariff) * panelConnectionCost);
            // Integer acreage = solarService.calculateNeededSpaceForSolar(solarPanelCostrequest.getWidthOfPanel().intValue(), solarPanelCostrequest.getLengthOfPanel().intValue(), numberOfPanels, solarPanelCostrequest.getUnitOfMeasure(), solarPanelCostrequest.getSynerUse());
            // System.out.println("Acreage: " + acreage);
            // Integer totalAcreageCost = acreage * solarPanelCostrequest.getPricePerAcre();
            // Integer electrolyzerCosts = solarService.calculateElectrolyzerCost(solarPanelCostrequest.getTypeOfElectrolyzer(), solarPanelCostrequest.getGoalKilogramsPerYear());
            // Double costOfElectrolyzersIncludingTaxAndTariff = (1 + taxAndTariff) * electrolyzerCosts;
            // Double totalCost = costOfPanelsIncludingTaxAndTariff + panelConnectionCostIncludingTaxAndTariff + totalAcreageCost + costOfElectrolyzersIncludingTaxAndTariff;
            // Integer systemBatteryWattageNeeded = (int)(systemPanelWattageNeeded * (1- solarPanelCostrequest.getSolarEfficiency()));
            // Integer batteryCost = solarService.calculateBatteryCost(will possibly have this function by end of semester);

            // Integer electrolyzerLifespanInYears = solarService.calculateElectrolyzerLifespaninYears(solarPanelCostrequest.getTypeOfElectrolyzer());
            String response = solarService.calculateSolarHydroPlantCost(solarPanelCostrequest);
            return ResponseEntity.ok(response);
            // return ResponseEntity.ok(response);//change the argument with proper value later.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
            // ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
    // @PostMapping("/calculate_electrolyzer_costs")
    // public Double calculateElectrolyzerCosts(@RequestBody ElectrolyzerCostRequest request ){
    //     return null; 
    // }
    
    

    

}
