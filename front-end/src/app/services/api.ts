// services/api.ts
"use client"
import axios from 'axios';




export interface SolarPlantCostRequest {
  costPerWattSolar: number;
  solarEfficiency: number;
  typeOfElectrolyzer: string;
  goalKilogramsPerYear: number;
  countryOfOrigin: string;
  wattsPerPanel: number;
  lengthOfPanel: number;
  widthOfPanel: number;
  unitOfMeasure: string;
  pricePerAcre: number;
  costPerPanelConnection: number;
  synerUse: string;
}




export const calculateSolarHydroPlantCost = async (data: SolarPlantCostRequest) => {
  try {
    const response = await axios({
      method: 'post',
      url: 'http://localhost:8080/solar/calculate_solar_hydro_plant_cost', // Complete URL
      data: { ...data}
        // costPerWattSolar: data.costPerWattSolar,
        // solarEfficiency: data.solarEfficiency,
        // typeOfElectrolyzer: data.typeOfElectrolyzer,
        // goalKilogramsPerYear: data.goalKilogramsPerYear,
        // countryOfOrigin: data.countryOfOrigin,
        // wattsPerPanel: data.wattsPerPanel,
        // lengthOfPanel: data.lengthOfPanel,
        // widthOfPanel: data.widthOfPanel,
        // unitOfMeasure: data.unitOfMeasure,
        // pricePerAcre: data.pricePerAcre,
        // costPerPanelConnection: data.costPerPanelConnection,
        // synerUse: data.synerUse
      // },
    });
    return response.data; // Return the response data
  } catch (error) {
    console.error(error);
    throw error; // Re-throw the error to handle it in the frontend
  }
};