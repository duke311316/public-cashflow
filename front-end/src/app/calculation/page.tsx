// calculation page
"use client";

import { useState } from 'react';
import { calculateSolarHydroPlantCost, SolarPlantCostRequest } from '../services/api';
import FlurDesLee from './flurdeslee/flurdeslee';


export default function CalculateSolarCost() {
  const [formData, setFormData] = useState<SolarPlantCostRequest>({
    costPerWattSolar: 0,
    solarEfficiency: 0,
    typeOfElectrolyzer: 'PEM', // default value
    goalKilogramsPerYear: 0,
    countryOfOrigin: 'USA', // default value
    wattsPerPanel: 0,
    lengthOfPanel: 0,
    widthOfPanel: 0,
    unitOfMeasure: 'inch', // default value
    pricePerAcre: 0,
    costPerPanelConnection: 0,
    synerUse: 'crawfish', // default value
  });

  const [result, setResult] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await calculateSolarHydroPlantCost(formData);
      setResult(response); // Set the response data as a string.
      setError(null);
    } catch (error) {
    //   setError(error);
      setResult(null);
      console.error(error);
    }
  };

  return (
    <div >
      <div 
      className = "header-container"
      >
        <div className = "flurdeslee-container">
          <FlurDesLee/>
        </div>
        
        <h1>Calculate Solar Hydrogen Plant Cost</h1>
      </div>
      
      <div className = "main-container">
      <div className = "inside-container">
      <form onSubmit={handleSubmit}>
        <div>
          <label>Cost Per Watt Solar:</label>
          <input
            type="number"
            name="costPerWattSolar"
            value={formData.costPerWattSolar}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Solar Efficiency:</label>
          <input
            type="number"
            name="solarEfficiency"
            value={formData.solarEfficiency}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Type of Electrolyzer:</label>
          <select
            name="typeOfElectrolyzer"
            value={formData.typeOfElectrolyzer}
            onChange={handleChange}
          >
            <option value="PEM">PEM</option>
            <option value="Alkaline">Alkaline</option>
            <option value="SOEC">SOEC</option>
          </select>
        </div>
        <div>
          <label>Goal Kilograms Per Year:</label>
          <input
            type="number"
            name="goalKilogramsPerYear"
            value={formData.goalKilogramsPerYear}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Country of Origin:</label>
          <select
            name="countryOfOrigin"
            value={formData.countryOfOrigin}
            onChange={handleChange}
          >
            <option value="USA">USA</option>
            <option value="China">China</option>
          </select>
        </div>
        <div>
          <label>Watts Per Panel:</label>
          <input
            type="number"
            name="wattsPerPanel"
            value={formData.wattsPerPanel}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Length of Panel:</label>
          <input
            type="number"
            name="lengthOfPanel"
            value={formData.lengthOfPanel}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Width of Panel:</label>
          <input
            type="number"
            name="widthOfPanel"
            value={formData.widthOfPanel}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Unit of Measure:</label>
          <select
            name="unitOfMeasure"
            value={formData.unitOfMeasure}
            onChange={handleChange}
          >
            <option value="inch">Inch</option>
            <option value="mm">Millimeter</option>
          </select>
        </div>
        <div>
          <label>Price Per Acre:</label>
          <input
            type="number"
            name="pricePerAcre"
            value={formData.pricePerAcre}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Cost Per Panel Connection:</label>
          <input
            type="number"
            name="costPerPanelConnection"
            value={formData.costPerPanelConnection}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Syner Use:</label>
          <select
            name="synerUse"
            value={formData.synerUse}
            onChange={handleChange}
          >
            <option value="Crawfish">Crawfish</option>
            <option value="Crawfish and Rice">Crawfish and Rice</option>
            <option value="Cattle">Cattle</option>
          </select>
        </div>
        <button type="submit">Calculate</button>
      </form>
      </div>
      <div className = "inside-container">
        {result && (
        <div className="results">
          <h2>Results</h2>
          {result.split('\n').map((line, index) => (
            <div key={index} className="result-box">{line}</div>
          ))}
        </div>
      )}

      {error && (
        <div className="error">
          <h2>Error:</h2>
          <p>{error}</p>
        </div>
      )}
      </div>
      </div>

      <style jsx>{`
        .main-container {
          padding: 20px;
          background-color: rgb(180, 10, 10);
          color: white;
          display:flex;
          flex-direction: row;
          height: 80vh;
        }
        form div {
          margin-bottom: 10px;
        }
        .results {
          margin-top: .5rem;
          max-height: 80vh;
        }
        .result-box {
          border: 1px solid #ccc;
          padding: 10px;
          margin: .4rem;
        }
        .flurdeslee-container{
          width: 8rem;
          height: auto;
          }
        .header-container{
        display: flex;
        flex-direction: row;
        background-color: rgb(180, 10, 10);
        border-bottom: 1px solid #ccc;
        border-top: 5px solid #rgb(126,126,126);
        color: white;
        align-items: center;
        }
        .inside-container{
          width: 50%;
          align-items: center;
          justify-content: center;
          
        }
        .error {
          color: red;
        }
      `}</style>
    </div>
  );
}