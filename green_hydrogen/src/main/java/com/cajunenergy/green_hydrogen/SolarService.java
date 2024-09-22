package com.cajunenergy.green_hydrogen;

import org.springframework.stereotype.Service;

@Service
public class SolarService {

    final private Double panelDegradeRate = .005; //.5 % per year
    final private Double pemDegradeRate = .25; //25% per year (nrel);
    private Integer pemHoursOfRuntime = 40000;//(nrel)
    private Double avgHoursOfDaylight = 8.5;
    private Double avgPanelEfficiencyDaylight = .55; //Xendee 
    final private Integer daysPerYear = 365;
    private Integer kilowattHoursPerKilogramOfStack = 51;//(nrel)
    private Double avgPanelEfficiencyPerDay = .219; //21.9% efficiency average (made-in-china.com)
    private Integer pricePemPerKw = 450;
    private Integer systemDailyWattage;
    private Double tax_tariff = 1.0;// set at 0 percentage
    final private Double milisInInch = 25.4;
    final private Integer inchInFoot = 12;
    final private Integer sqftInAcre = 43560;
    private Double hydrogenDemanGrowthProjection = 1.05; // 5 percent year over year

    public Integer calculateSolarWattage (Integer goalKilogramsPerYear){
        Integer yearlyKilowattHoursNeeded = (int)((goalKilogramsPerYear * kilowattHoursPerKilogramOfStack));
        Integer dailyKilowattHoursNeeded = yearlyKilowattHoursNeeded/daysPerYear;
        Integer kilowattsNeeded = (int)(dailyKilowattHoursNeeded/(avgPanelEfficiencyDaylight * avgHoursOfDaylight));
        systemDailyWattage =  kilowattsNeeded * 1000;
        return systemDailyWattage;
    }

    public Integer calculateNumberOfPanels(Integer wattsPerPanel){
        return systemDailyWattage/wattsPerPanel;
    }
    public Double calculateSolarPanelTotalPrice(Double pricePerWatt, Integer dailywattageNeeds){
        return (dailywattageNeeds * pricePerWatt);
    }

    public Integer calculateNeededSpaceForSolar (Integer panelWidth, Integer panelLength, Integer numberOfPanels, String unitOfMeasure){
        Integer squareAcreage = 0;
        if(unitOfMeasure.equals("mm")){
         squareAcreage = (int)((panelWidth/(milisInInch * inchInFoot)) * (panelLength/(milisInInch * inchInFoot)) * numberOfPanels)/sqftInAcre;
        }
        else if (unitOfMeasure.equals("inch")){
         squareAcreage = (int)((panelWidth/(inchInFoot)) * (panelLength/(inchInFoot)) * numberOfPanels)/sqftInAcre;
        };
        squareAcreage  = (int)(squareAcreage * 1.2);//adds 20% onto land value for passageways (walk and drive) and maintenance routes
        return squareAcreage;
    }
    public Integer calculateLandCost(Integer acreage, Integer pricePerAcre){
        return acreage * pricePerAcre;
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





}
