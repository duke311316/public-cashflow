package com.cajunenergy.green_hydrogen;

import org.springframework.stereotype.Service;

@Service
public class SolarService {

    // Degradation rates of solar panels and electrolyzers 
    // final private Double monoPanelDegradeRate = .005; //.5 % per year
    final private Double soecDegradeRate = .5; //50% per year (energy.gov);
    final private Double pemDegradeRate = .25; //25% per year (energy.gov);
    final private Double alkalineDegradeRate = .17; //17% per year (energy.gov); 
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
    final private Integer hoursPerDay = 24;

    private Integer kilowattHoursPerKilogramOfPemStack = 51;//(energy.gov)
    private Integer kilowattHoursPerKilogramOfAlkalineStack = 51;//(energy.gov)
    private Integer kilowattHoursPerKilogramOfSoecStack = 34;//(energy.gov)
    // private Double avgPanelEfficiencyPerDay = .219; //21.9% efficiency average (made-in-china.com)
    final private Double milisInInch = 25.4;
    final private Integer inchInFoot = 12;
    final private Integer sqftInAcre = 43560;
    private Double hydrogenDemanGrowthProjection = 1.05; // 5 percent year over year
    final private Double uptime = .95;

    
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

    public Integer calculateNumberOfPanels(Integer wattsPerPanel, Integer systemDailyWattageNeeds){
        return systemDailyWattageNeeds/wattsPerPanel;
    }

    public Double calculateSolarPanelTotalPrice(Double pricePerWatt, Integer dailywattageNeeds){
        return (dailywattageNeeds * pricePerWatt);
    }

    public Integer calculateNeededSpaceForSolar (Integer panelWidth, Integer panelLength, Integer numberOfPanels, String unitOfMeasure, String synerUse){
        Integer acreage = 0;
        if(unitOfMeasure.equals("mm")){
         acreage = (int)Math.ceil((panelWidth/(milisInInch * inchInFoot)) * (panelLength/(milisInInch * inchInFoot)) * numberOfPanels)/sqftInAcre;
        }
        else if (unitOfMeasure.equals("inch")){
         acreage = (int)Math.ceil((panelWidth/(inchInFoot)) * (panelLength/(inchInFoot)) * numberOfPanels)/sqftInAcre;
        };

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

    public Integer calculateLandCost(Double acreage, Double pricePerAcre){
        return (int)(acreage * pricePerAcre);
    }

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

    public Integer calculateElectrolyzerCost(String typeOfElectrolyzer, Integer goalKilogramPerYear){
        Integer costPerKw = 0;
        Integer goalKilogramsPerHour = goalKilogramPerYear/(daysPerYear * hoursPerDay);
        Integer kilowattsPerHourNeeded = 0;
        Double degradationRate = 0.0;
        Integer hoursOfRunTime = 0;
        

        switch(typeOfElectrolyzer){
            case "PEM":
                costPerKw = costPemElecPerKw;
                degradationRate = pemDegradeRate;
                kilowattsPerHourNeeded = goalKilogramsPerHour * kilowattHoursPerKilogramOfPemStack;
                hoursOfRunTime = pemHoursToEndOfLife;
                break;
            case "Alkaline":
                costPerKw = costAlkaElecPerKw;
                degradationRate = alkalineDegradeRate;
                kilowattsPerHourNeeded = goalKilogramsPerHour * kilowattHoursPerKilogramOfAlkalineStack;
                hoursOfRunTime = alkalineHoursToEndOfLife;
                break;
            case "SOEC":
                costPerKw = costSoecElecPerKw;
                degradationRate = soecDegradeRate;
                kilowattsPerHourNeeded = goalKilogramsPerHour * kilowattHoursPerKilogramOfSoecStack;
                hoursOfRunTime = soecHoursOfToEndOfLife;
                break;
            default:
                break;
        }
        
        
        return null;
    }


}
