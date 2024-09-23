package com.cajunenergy.green_hydrogen;

import org.springframework.stereotype.Service;
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
    // private Double avgHoursOfDaylight = 8.5;
    // private Double avgPanelEfficiencyDaylight = .55; //Xendee 
    final private Integer daysPerYear = 365;
    final private Integer hoursPerDay = 8;// will keep at 8 until battery logic is better implemented that way everything will be based on 8 hours of sunlight.

    private Integer kilowattHoursPerKilogramOfPemStack = 51;//(energy.gov)
    private Integer kilowattHoursPerKilogramOfAlkalineStack = 51;//(energy.gov)
    private Integer kilowattHoursPerKilogramOfSoecStack = 34;//(energy.gov)
    // private Double avgPanelEfficiencyPerDay = .219; //21.9% efficiency average (made-in-china.com)
    final private Double milisInInch = 25.4;
    final private Integer inchInFoot = 12;
    final private Integer sqftInAcre = 43560;
    private Double hydrogenDemanGrowthProjection = 1.05; // 5 percent year over year
    final private Double uptime = .95;

    /**
     * calculateSolarWattage
     * @param goalKilogramsPerYear
     * @param avgPanelEfficiencyPerDay
     * @param typeOfElectrolyzer
     * @return
     */
    public Integer calculateSolarWattage (Integer goalKilogramsPerYear, Double avgPanelEfficiencyPerDay, String typeOfElectrolyzer){
        Integer yearlyKilowattHoursNeeded = 0;
        switch(typeOfElectrolyzer){
            case "PEM":
                yearlyKilowattHoursNeeded = (goalKilogramsPerYear * kilowattHoursPerKilogramOfPemStack);
                break;
            case "Alkaline":
                yearlyKilowattHoursNeeded = (goalKilogramsPerYear * kilowattHoursPerKilogramOfAlkalineStack);
                break;
            case "SOEC":
                yearlyKilowattHoursNeeded = (goalKilogramsPerYear * kilowattHoursPerKilogramOfSoecStack);
                break;
            default:
                break;
        }
        Integer dailyKilowattHoursNeeded = yearlyKilowattHoursNeeded/daysPerYear;
        Integer dailyKilowattHoursNeededAdjustedForEfficiency = (int)(dailyKilowattHoursNeeded/(avgPanelEfficiencyPerDay));
        Integer systemWattage =  (dailyKilowattHoursNeededAdjustedForEfficiency * 1000)/24;
        return systemWattage;
    }

    /**
     * calculateNumberOfPanels
     * @param wattsPerPanel
     * @param systemDailyWattageNeeds
     * @return Integer number of panels needed
     */
    public Integer calculateNumberOfPanels(Integer wattsPerPanel, Integer systemDailyWattageNeeds){
        return systemDailyWattageNeeds/wattsPerPanel;
    }

    /**
     * calculateSolarPanelTotalPrice
     * @param pricePerWatt
     * @param dailywattageNeeds
     * @return Double total price of solar panels
     */
    public Double calculateSolarPanelTotalPrice(Double pricePerWatt, Integer dailywattageNeeds){
        return (dailywattageNeeds * pricePerWatt);
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
        Integer acreage = 0;
        if(unitOfMeasure.equals("mm")){
         acreage = (int)Math.ceil((panelWidth/(milisInInch * inchInFoot)) * (panelLength/(milisInInch * inchInFoot)) * numberOfPanels)/sqftInAcre;
        }
        else if (unitOfMeasure.equals("inch")){
         acreage = (int)Math.ceil((panelWidth/(inchInFoot)) * (panelLength/(inchInFoot)) * numberOfPanels)/sqftInAcre;
        };
        // these should be adjusted once someone from industry can provide more accurate numbers or agricultural data.
        switch(synerUse){
            case "crawfish":
                acreage = (int)(acreage * 1.2);//adds 15% onto land value for passageways (walk and drive) and maintenance routes and minimum needed space for crawfish farming. 
                break;
            case "crawfish and rice":
                acreage = (int)(acreage * 1.75);//adds 75% onto land value for passageways (walk and drive) and maintenance routes and plenty of space between panel rows for sunlight for rice farming.
                break;
            case "cattle":
                acreage = (int)(acreage * 1.4);//adds 40% onto land value for passageways (walk and drive) and maintenance routes and space for vegitation for cattle grazing.
                break;
            default:
                break;
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
        switch(typeOfElectrolyzer){
            case "PEM":
                daysOfRunTime = pemHoursToEndOfLife / hoursPerDay;
                break;
            case "Alkaline":
                daysOfRunTime = alkalineHoursToEndOfLife / hoursPerDay;
                break;
            case "SOEC":
                daysOfRunTime = soecHoursOfToEndOfLife / hoursPerDay;
                break;
            default:
                break;
        }
        return daysOfRunTime/daysPerYear;
    }

    // public Integer calculateBatteryCost(Integer systemWattHoursNeededPerHour, Integer batteryMaxPowerInMw, Integer batteryMaxMwHours, Integer costPerBattery, Integer solarEnergyGenerationInKillowatts){
    //     Integer batteryCost = 0;
    //     Integer runtime = 16; //hours a day batteires will be used
    //     Integer batteryMaxOutputInKw = batteryMaxPowerInMw * 1000;
    //     return 0;
    // }





}
