package com.cajunenergy.green_hydrogen;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.ArrayList;

/** SolarService
 * This class contains methods to calculate the cost of solar panels and electrolyzers.
 * The methods calculate the wattage needed for solar panels, the number of panels needed, the total price of solar panels, the space needed for solar panels, the cost of land, and the cost of electrolyzers.
 */
@Service
public class SolarService {

    // Degradation rates of solar panels and electrolyzers 
    // final private Double monoPanelDegradeRate = .005; //.5 % per year
    final private Double soecDegradeRate = .5; //.5% per 1000 hours (energy.gov);
    final private Double pemDegradeRate = .25; //.25% per 1000 hours (energy.gov);
    final private Double alkalineDegradeRate = .17; //.17% per 1000 hours (energy.gov); 
    final private Integer pemHoursToEndOfLife = 40000;//energy.gov
    final private Integer soecHoursOfToEndOfLife = 20000;//energy.gov
    //www.energy.gov/eere/fuelcells/technical-targets-liquid-alkaline-electrolysis
    final private Integer alkalineHoursToEndOfLife = 60000;// 10% drop in voltage when considering same current density 
    //Price $/kilowatt electrolyzers
    private Integer costAlkaElecPerKw = 250;
    private Integer costPemElecPerKw = 450;
    private Integer costSoecElecPerKw = 300;
    // private Double alkalineEfficiency = .61;
    // private Double pemEfficiency = .65;
    // private Double soecEfficiency = .99;

    // private Double avgHoursOfDaylight = 8.5;
    // private Double avgPanelEfficiencyDaylight = .55; //Xendee 
    final private Integer daysPerYear = 365;
    final private Integer hoursPerDay = 24;// will keep at 8 until battery logic is better implemented that way everything will be based on 8 hours of sunlight.

    private Integer kilowattHoursPerKilogramOfPemStack = 51;//(energy.gov)
    private Integer kilowattHoursPerKilogramOfAlkalineStack = 51;//(energy.gov)
    private Integer kilowattHoursPerKilogramOfSoecStack = 34;//(energy.gov)
    // private Double avgPanelEfficiencyPerDay = .219; //21.9% efficiency average (made-in-china.com)
    
    // private Double hydrogenDemanGrowthProjection = 1.05; // 5 percent year over year
    final private Double uptime = .95;

    /**
     * calculateSolarWattage
     * @param goalKilogramsPerYear
     * @param avgPanelEfficiencyPerDay
     * @param typeOfElectrolyzer
     * @return
     */
    public Long calculateSolarWattage (Integer goalKilogramsPerYear, Double avgPanelEfficiencyPerDay, String typeOfElectrolyzer){
        Integer yearlyKilowattHoursNeeded;
        switch(typeOfElectrolyzer){
            case "PEM":
                yearlyKilowattHoursNeeded = (int)((goalKilogramsPerYear * kilowattHoursPerKilogramOfPemStack));
                break;
            case "Alkaline":
                yearlyKilowattHoursNeeded = (int)((goalKilogramsPerYear * kilowattHoursPerKilogramOfAlkalineStack));
                break;
            case "SOEC":
                yearlyKilowattHoursNeeded = (int)((goalKilogramsPerYear * kilowattHoursPerKilogramOfSoecStack));
                break;
            default:
                yearlyKilowattHoursNeeded = 0;
                break;
        }
        // if (typeOfElectrolyzer.equals("PEM")) {
        //     yearlyKilowattHoursNeeded = (int)(goalKilogramsPerYear * kilowattHoursPerKilogramOfPemStack);
        // } else if (typeOfElectrolyzer.equals("Alkaline")) {
        //     yearlyKilowattHoursNeeded = (int)(goalKilogramsPerYear * kilowattHoursPerKilogramOfAlkalineStack);
        // } else if (typeOfElectrolyzer.equals("SOEC")) {
        //     yearlyKilowattHoursNeeded = (int)(goalKilogramsPerYear * kilowattHoursPerKilogramOfSoecStack);
        // }
        double dailyKilowattHoursNeeded = (double)yearlyKilowattHoursNeeded/daysPerYear;
        System.out.println("Daily Kilowatt Hours Needed: " + dailyKilowattHoursNeeded);
        Double dailyKilowattHoursNeededAdjustedForEfficiency = dailyKilowattHoursNeeded/(avgPanelEfficiencyPerDay);
        System.out.println("Daily Kilowatt Hours Needed: " + dailyKilowattHoursNeededAdjustedForEfficiency);
        Long systemWattage =  (long) ((dailyKilowattHoursNeededAdjustedForEfficiency * 1000)/24);
        return systemWattage;
    }

    /**
     * calculateNumberOfPanels
     * @param wattsPerPanel
     * @param systemWattageNeeds
     * @return Integer number of panels needed
     */
    public Integer calculateNumberOfPanels(Integer wattsPerPanel, Long systemWattageNeeds){
        return (int) (systemWattageNeeds/wattsPerPanel);
    }

    /**
     * calculateSolarPanelTotalPrice
     * @param pricePerWatt
     * @param wattageNeeds
     * @return Double total price of solar panels
     */
    public Double calculateSolarPanelTotalPrice(Double pricePerWatt, Long wattageNeeds){
        return (wattageNeeds * pricePerWatt);
    }

    /**
     * calculateNeededSpaceForSolar
     * @param panelWidth 
     * @param panelLength
     * @param numberOfPanels
     * @param unitOfMeasure (mm, inch)
     * @param synerUse type of land use (crawfish, crawfish and rice, cattle)
     * @return Integer acreage needed for solar panels
     */
    public Integer calculateNeededSpaceForSolar (Integer panelWidth, Integer panelLength, Integer numberOfPanels, String unitOfMeasure, String synerUse){
        Double milisInInch = 25.4;
        Integer inchInFoot = 12;
        Integer sqftInAcre = 43560;
        Integer acreage = 0;
        if(unitOfMeasure.equals("mm")){
         acreage = (int)Math.ceil((panelWidth/(milisInInch * inchInFoot)) * (panelLength/(milisInInch * inchInFoot)) * numberOfPanels)/sqftInAcre;
        }
        else if (unitOfMeasure.equals("inch")){
         acreage = ((panelWidth/(inchInFoot)) * (panelLength/(inchInFoot)) * numberOfPanels)/sqftInAcre;
        };
        // these should be adjusted once someone from industry can provide more accurate numbers or agricultural data.
        // switch(synerUse){
        //     case "Crawfish":{
        //         acreage = (int)(acreage * 1.2);//adds 15% onto land value for passageways (walk and drive) and maintenance routes and minimum needed space for crawfish farming. 
        //         break;}
        //     case "Crawfish and Rice":
        //         {acreage = (int)(acreage * 1.75);//adds 75% onto land value for passageways (walk and drive) and maintenance routes and plenty of space between panel rows for sunlight for rice farming.
        //         break;}
        //     case "Cattle":
        //         {acreage = (int)(acreage * 1.4);//adds 40% onto land value for passageways (walk and drive) and maintenance routes and space for vegitation for cattle grazing.
        //         break;}
        //     default:
        //         {break;}
        // }
        if (synerUse.equals("Crawfish")){
            acreage = (int)Math.ceil(acreage * 1.2);
        }
        else if (synerUse.equals("Crawfish and Rice")){
            acreage = (int)Math.ceil(acreage * 2.0);
        }
        else if (synerUse.equals("Cattle")){
            acreage = (int)Math.ceil(acreage * 1.4);
        }
        return acreage;
    }

    /**
     * calculateLandCost
     * @param acreage
     * @param pricePerAcre
     * @return Integer cost of land
     */
    public Integer calculateLandCost(Double acreage, Double pricePerAcre){
        return (int)(acreage * pricePerAcre);
    }

    /**
     * addTaxAndTariff
     * @param countryOfOrigin
     * @return Double percentage of tax and tariff
     */
    public Double addTaxAndTariff(String countryOfOrigin){
        Double percentage = 0.0;
        switch (countryOfOrigin) {
            case "China":{
                percentage = .5;
                break;
            }
            case "USA": {
                percentage = .1;
            }
            default:
                break;
        }
        return percentage;
    }

    /**
     * calculateElectrolyzerCost
     * @param typeOfElectrolyzer
     * @param goalKilogramInFirstYear
     * @param loanInterest
     * @param loanTermInYears
     * @param evenLoanPayments
     * @param unevenLoanPayments
     * @return Integer cost of electrolyzers 
     */
    public Integer calculateElectrolyzerCost(String typeOfElectrolyzer, Integer goalKilogramInFirstYear){
        Integer costPerKw = 0;
        Integer goalKilogramsPerHour = goalKilogramInFirstYear/(daysPerYear * hoursPerDay);
        Integer kilowattsPerHourNeeded = 0;
        // Double degradationRatePer1000Hours = 0.0;
        // Integer hoursOfRunTime = 0;
        switch(typeOfElectrolyzer){
            case "PEM":
                costPerKw = costPemElecPerKw;
                // degradationRatePer1000Hours = pemDegradeRate;
                kilowattsPerHourNeeded = goalKilogramsPerHour * kilowattHoursPerKilogramOfPemStack;
                // hoursOfRunTime = pemHoursToEndOfLife;
                break;
            case "Alkaline":
                costPerKw = costAlkaElecPerKw;
                // degradationRatePer1000Hours = alkalineDegradeRate;
                kilowattsPerHourNeeded = goalKilogramsPerHour * kilowattHoursPerKilogramOfAlkalineStack;
                // hoursOfRunTime = alkalineHoursToEndOfLife;
                break;
            case "SOEC":
                costPerKw = costSoecElecPerKw;
                // degradationRatePer1000Hours = soecDegradeRate;
                kilowattsPerHourNeeded = goalKilogramsPerHour * kilowattHoursPerKilogramOfSoecStack;
                // hoursOfRunTime = soecHoursOfToEndOfLife;
                break;
            default:
                break;
        }
        // if(typeOfElectrolyzer.equals("PEM")){
        //     costPerKw = costPemElecPerKw;
        //     kilowattsPerHourNeeded = (goalKilogramsPerHour * kilowattHoursPerKilogramOfPemStack);
        // }
        // else if(typeOfElectrolyzer.equals("Alkaline")){
        //     costPerKw = costAlkaElecPerKw;
        //     kilowattsPerHourNeeded = (goalKilogramsPerHour * kilowattHoursPerKilogramOfAlkalineStack);
        // }
        // else if(typeOfElectrolyzer.equals("SOEC")){
        //     costPerKw = costSoecElecPerKw;
        //     kilowattsPerHourNeeded = (goalKilogramsPerHour * kilowattHoursPerKilogramOfSoecStack);
        // }
        kilowattsPerHourNeeded = (int)Math.ceil(kilowattsPerHourNeeded/uptime);// makes up for the 5% downtime
        Integer NumberOfMegawattElectrolyzersNeeded = (int)Math.ceil(kilowattsPerHourNeeded/1000); // 1 megawatt = 1000 kilowatts
        Integer costOfElectrolyzers = (int)(costPerKw * 1000 * NumberOfMegawattElectrolyzersNeeded);
        return costOfElectrolyzers;
    }

    /**
     * calculateElectrolyzerLifespaninYears
     * @param typeOfElectrolyzer
     * @return Integer lifespan of electrolyzer in years
     */
    public Integer calculateElectrolyzerLifespaninYears( String typeOfElectrolyzer){
        Integer daysOfRunTime = 0;
        // switch(typeOfElectrolyzer){
        //     case "PEM":
        //         daysOfRunTime = pemHoursToEndOfLife / hoursPerDay;
        //         break;
        //     case "Alkaline":
        //         daysOfRunTime = alkalineHoursToEndOfLife / hoursPerDay;
        //         break;
        //     case "SOEC":
        //         daysOfRunTime = soecHoursOfToEndOfLife / hoursPerDay;
        //         break;
        //     default:
        //         break;
        // }
        if (typeOfElectrolyzer.equals("PEM")){
            daysOfRunTime = pemHoursToEndOfLife / hoursPerDay;
        }
        else if (typeOfElectrolyzer.equals("Alkaline")){
            daysOfRunTime = alkalineHoursToEndOfLife / hoursPerDay;
        }
        else if (typeOfElectrolyzer.equals("SOEC")){
            daysOfRunTime = soecHoursOfToEndOfLife / hoursPerDay;
        }


        return daysOfRunTime/daysPerYear;
    }

    // public Integer calculateBatteryCost(Integer systemWattHoursNeededPerHour, Integer batteryMaxPowerInMw, Integer batteryMaxMwHours, Integer costPerBattery, Integer solarEnergyGenerationInKillowatts){
    //     Integer batteryCost = 0;
    //     Integer runtime = 16; //hours a day batteires will be used
    //     Integer batteryMaxOutputInKw = batteryMaxPowerInMw / 1000;
    //     return 0;
    // }

    public String calculateSolarHydroPlantCost(@RequestBody SolarPlantCostRequest solarPanelCostrequest) {
        try {
            Long systemPanelWattageNeeded = calculateSolarWattage(solarPanelCostrequest.getGoalKilogramsPerYear(), solarPanelCostrequest.getSolarEfficiency(), solarPanelCostrequest.getTypeOfElectrolyzer());
            System.out.println("solar panel efficiency: " + solarPanelCostrequest.getSolarEfficiency());
            System.out.println("System Panel Wattage Needed: " + systemPanelWattageNeeded);
            Double solarPanelCost = calculateSolarPanelTotalPrice(solarPanelCostrequest.getCostPerWattSolar(), systemPanelWattageNeeded);
            Double taxAndTariff = addTaxAndTariff(solarPanelCostrequest.getCountryOfOrigin());
            Double costOfPanelsIncludingTaxAndTariff = (1 + taxAndTariff) * solarPanelCost;
            Integer numberOfPanels = calculateNumberOfPanels(solarPanelCostrequest.getWattsPerPanel(), systemPanelWattageNeeded);
            
            Integer numberOfConnections = numberOfPanels;
            Integer panelConnectionCost = numberOfConnections * solarPanelCostrequest.getCostPerPanelConnection().intValue();
            Integer panelConnectionCostIncludingTaxAndTariff = (int)((1 + taxAndTariff) * panelConnectionCost);
            Integer acreage = calculateNeededSpaceForSolar(solarPanelCostrequest.getWidthOfPanel().intValue(), solarPanelCostrequest.getLengthOfPanel().intValue(), numberOfPanels, solarPanelCostrequest.getUnitOfMeasure(), solarPanelCostrequest.getSynerUse());
            System.out.println("Acreage: " + acreage);
            Integer totalAcreageCost = acreage * solarPanelCostrequest.getPricePerAcre();
            Integer electrolyzerCosts = calculateElectrolyzerCost(solarPanelCostrequest.getTypeOfElectrolyzer(), solarPanelCostrequest.getGoalKilogramsPerYear());
            Double costOfElectrolyzersIncludingTaxAndTariff = (1 + taxAndTariff) * electrolyzerCosts;
            Double totalCost = costOfPanelsIncludingTaxAndTariff + panelConnectionCostIncludingTaxAndTariff + totalAcreageCost + costOfElectrolyzersIncludingTaxAndTariff;
            // Integer systemBatteryWattageNeeded = (int)(systemPanelWattageNeeded * (1- solarPanelCostrequest.getSolarEfficiency()));
            // Integer batteryCost = calculateBatteryCost(will possibly have this function by end of semester);

            // Integer electrolyzerLifespanInYears = solarService.calculateElectrolyzerLifespaninYears(solarPanelCostrequest.getTypeOfElectrolyzer());
            String response = "The different components of the solar hydrogen plant cost are as follows: \n" +
            "System Panel Wattage Needed: " + systemPanelWattageNeeded + "\n" +
           
            "Number of Panels: " + numberOfPanels + "\n" +
            "Cost of Solar Panels: $" + costOfPanelsIncludingTaxAndTariff + "\n" +
            "Cost of Panel Connections: $" + panelConnectionCostIncludingTaxAndTariff + "\n" +
            "Sqft needed for Solar Panels: " +
            "Total Acreage Needed: " + acreage + " acres" + "\n" +

            "Cost of Acreage: $" + totalAcreageCost + "\n" +
            "Cost of Electrolyzers: $" + costOfElectrolyzersIncludingTaxAndTariff + "\n" +
            "Total Cost: $" + (int) (totalCost * 1);
            return response;
            // return ResponseEntity.ok(response);//change the argument with proper value later.
        } catch (Exception e) {
            return e.getMessage();
        }
    }



}
