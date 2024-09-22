package com.cajunenergy.green_hydrogen;


class SolarPlantCostRequest{
    private Double costPerWattSolar;
    private Double solarEfficiency;
    private String typeOfElectrolyzer;
    private Integer goalKilogramsPerYear;
    private String countryOfOrigin;
    private Integer wattsPerPanel;
    private Double lengthOfPanel;
    private Double widthOfPanel;
    private String unitOfMeasure;
    private Integer pricePerAcre;
    private Integer costPerKilowattElectrolyzer;
    private Double costPerPanelConnection;
    private String synerUse;

    // No-arg constructor
    public SolarPlantCostRequest() {
    }

    // Argument constructor
    public SolarPlantCostRequest(
        Double costPerWattSolar, 
        Integer costPerKilowattElectrolyzer,
        Double lengthOfPanel,
        Double widthOfPanel,
        String unitOfMeasure,
        Integer pricePerAcre,
        Integer wattsPerPanel, 
        Double solarEfficiency, 
        String typeOfElectrolyzer, 
        Integer goalKilogramsPerYear, 
        String countryOfOrigin,
        Double costPerPanelConnection,
        String synerUse) {
        this.costPerWattSolar = costPerWattSolar;
        this.solarEfficiency = solarEfficiency;
        this.costPerKilowattElectrolyzer = costPerKilowattElectrolyzer;
        this.typeOfElectrolyzer = typeOfElectrolyzer;
        this.goalKilogramsPerYear = goalKilogramsPerYear;
        this.countryOfOrigin = countryOfOrigin;
        this.wattsPerPanel = wattsPerPanel;
        this.lengthOfPanel = lengthOfPanel;
        this.widthOfPanel = widthOfPanel;
        this.unitOfMeasure = unitOfMeasure;
        this.pricePerAcre = pricePerAcre;
        this.costPerPanelConnection = costPerPanelConnection;
        this.synerUse = synerUse;
    }


    // Getters
    public Double getCostPerWattSolar() {
        return costPerWattSolar;
    }

    public Double getSolarEfficiency() {
        return solarEfficiency;
    }

    public Integer getCostPerKilowattElectrolyzer() {
        return costPerKilowattElectrolyzer;
    }

    public String getTypeOfElectrolyzer() {
        return typeOfElectrolyzer;
    }

    public Integer getGoalKilogramsPerYear() {
        return goalKilogramsPerYear;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    // Setters
    public void setCostPerWattSolar(Double costPerWattSolar) {
        this.costPerWattSolar = costPerWattSolar;
    }

    public void setSolarEfficiency(Double solarEfficiency) {
        this.solarEfficiency = solarEfficiency;
    }

    public void setCostPerKilowattElectrolyzer(Integer costPerKilowattElectrolyzer) {
        this.costPerKilowattElectrolyzer = costPerKilowattElectrolyzer;
    }

    public void setTypeOfElectrolyzer(String typeOfElectrolyzer) {
        this.typeOfElectrolyzer = typeOfElectrolyzer;
    }

    public void setGoalKilogramsPerYear(Integer goalKilogramsPerYear) {
        this.goalKilogramsPerYear = goalKilogramsPerYear;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Integer getWattsPerPanel() {
        return wattsPerPanel;
    }

    public void setWattsPerPanel(Integer wattsPerPanel) {
        this.wattsPerPanel = wattsPerPanel;
    }

    public Double getLengthOfPanel() {
        return lengthOfPanel;
    }

    public void setLengthOfPanel(Double lengthOfPanel) {
        this.lengthOfPanel = lengthOfPanel;
    }

    public Double getWidthOfPanel() {
        return widthOfPanel;
    }

    public void setWidthOfPanel(Double widthOfPanel) {
        this.widthOfPanel = widthOfPanel;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Integer getPricePerAcre() {
        return pricePerAcre;
    }

    public void setPricePerAcre(Integer pricePerAcre) {
        this.pricePerAcre = pricePerAcre;
    }

    public Double getCostPerPanelConnection() {
        return costPerPanelConnection;
    }

    public void setCostPerPanelConnection(Double costPerPanelConnection) {
        this.costPerPanelConnection = costPerPanelConnection;
    }

    public String getSynerUse() {
        return synerUse;
    }

    public void setSynerUse(String synerUse) {
        this.synerUse = synerUse;
    }

    @Override
    public String toString() {
        return "SolarPanelCostRequest [costPerWattSolar=" + costPerWattSolar + ", countryOfOrigin=" + countryOfOrigin
                + ", goalKilogramsPerYear=" + goalKilogramsPerYear + ", solarEfficiency=" + solarEfficiency
                + ", typeOfElectrolyzer=" + typeOfElectrolyzer + ", wattsPerPanel=" + wattsPerPanel +  ", lengthOfPanel=" + lengthOfPanel + ", widthOfPanel=" + widthOfPanel + ", unitOfMeasure=" + unitOfMeasure + ", pricePerAcre=" + pricePerAcre + ", costPerKilowattElectrolyzer=" + costPerKilowattElectrolyzer + ", costPerPanelConnection=" + costPerPanelConnection + ", synerUse=" + synerUse + "]";
    }

}

