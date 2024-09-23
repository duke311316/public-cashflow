// package com.cajunenergy.green_hydrogen;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;


// @RestController
// @RequestMapping("/solar")
// public class SolarController {
//     @Autowired
//     private SolarService solarService;

    
//     @PostMapping("/calculate_solar_hydro_plant_cost")
//     public ResponseEntity<String> calculateSolarHydroPlantCost(@RequestBody SolarPlantCostRequest solarPanelCostrequest) {
//         try {
//             Integer systemWattage = solarService.calculateSolarWattage(solarPanelCostrequest.getGoalKilogramsPerYear(), solarPanelCostrequest.getSolarEfficiency(), solarPanelCostrequest.getTypeOfElectrolyzer());
//             Double solarPanelCost = solarService.calculateSolarPanelTotalPrice(solarPanelCostrequest.getCostPerWattSolar(), systemWattage);
//             Double taxAndTariff = solarService.addTaxAndTariff(solarPanelCostrequest.getCountryOfOrigin());
//             Double panelCostOfPanelsIncludingTaxAndTariff = (1 + taxAndTariff) * solarPanelCost;
//             Integer numberOfPanels = solarService.calculateNumberOfPanels(solarPanelCostrequest.getWattsPerPanel(), systemWattage);
//             Integer numberOfConnections = numberOfPanels;
//             Integer panelConnectionCost = numberOfConnections * solarPanelCostrequest.getCostPerPanelConnection().intValue();
//             Integer panelConnectionCostIncludingTaxAndTariff = (int)((1 + taxAndTariff) * panelConnectionCost);
//             Integer acreage = solarService.calculateNeededSpaceForSolar(solarPanelCostrequest.getWidthOfPanel().intValue(), solarPanelCostrequest.getLengthOfPanel().intValue(), numberOfPanels, solarPanelCostrequest.getUnitOfMeasure(), solarPanelCostrequest.getSynerUse());
//             Integer totalAcreageCost = acreage * solarPanelCostrequest.getPricePerAcre();

//             // Following is  a String of the response that will be sent back to the client.
//             String response = "The different components of the solar hydrogen plant cost are as follows: \n" +
//             "Cost of Solar Panels: $" + panelCostOfPanelsIncludingTaxAndTariff + "\n" +
//             "Cost of Panel Connections: $" + panelConnectionCostIncludingTaxAndTariff + "\n" +
//             "Cost of Acreage: $" + totalAcreageCost + "\n" + 
//             "Total Initial Cost: $" + (panelCostOfPanelsIncludingTaxAndTariff + panelConnectionCostIncludingTaxAndTariff + totalAcreageCost);

            
//             return ResponseEntity.ok(response);//change the argument with proper value later.
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//         }

//     }
//     // @PostMapping("/calculate_electrolyzer_costs")
//     // public Double calculateElectrolyzerCosts(@RequestBody ElectrolyzerCostRequest request ){
//     //     return null; 
//     // }
    
    

    

// }
