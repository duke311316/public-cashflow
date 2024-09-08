package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** CashFlow
 * Class that handles several money-based calculations to provide
 * reasoning behind the banks numbers**/
public class CashFlow {
    /** amount of interest on loan or investment **/
    private Double interest;
    /** initialNetAmount
     * net amount of monies initially invested or loaned at day 0**/
    private Double initialNetAmount;
    /** period
     *  the time elapse in which the interest is calculated **/
    private Integer compoundPeriod;
    /** nunOfPayments
     * how many periods within the loan **/
    private Integer numOfPeriods;
    /** recurringPayment
     * if equalPayments is true -> the amount of money per payment **/
    private Double recurringPayment;
    /** equalPayments **/
    private boolean equalPayments;
    /** unequalPayments
     * what is the agreed payments if not equal amounts **/
    private List<Double> unequalPayments = new ArrayList<>();
    /** equity
     * current vested. If a car, this would be the worth the dealership is willing
     * to offer of the vehicle minus what you owe. Same for a house.**/
    private Double equity;
    /** currentLoanPayoff
     * if you have a car or house, what will it cost to pay the loan off on the day
     * in which you are to make a new purchase or transfer.**/
    private Double currentLoanPayoff;
    /** tradeAppraisal
     * value of the property in your position**/
    private Double tradeAppraisal;
    private Double insurance;
    private Double msrp;
    private Double downPayment;
    private Double tax;
    private Double title;
    private boolean car;
    private boolean house;
    private boolean investment;


    public CashFlow(){
        this.interest = 0.0;
        this.initialNetAmount = 0.0;
        this.compoundPeriod = 0;
        this.numOfPeriods = 0;
        this.recurringPayment = 0.0;
        this.equalPayments = true;
        this.unequalPayments = new ArrayList<>();
        this.equity = 0.0;
        this.currentLoanPayoff = 0.0;
        this.tradeAppraisal = 0.0;
        this.insurance = 0.0;
        this.msrp = 0.0;
        this.downPayment = 0.0;
        this.tax = 0.0;
        this.title = 0.0;
        this.car = false;
        this.house = false;
        this.investment = false;
    }

    public Double getInterest () {
        return interest;
    }
    public void setInterest (Double interest) {
        this.interest = interest;
    }
    public Double getInitialNetAmount () {
        return initialNetAmount;
    }
    private void setInitialNetAmount (Double initialAmount) {
        this.initialNetAmount = initialAmount;
    }
    public boolean isEqualPayments() {
        return equalPayments;
    }
    public void setEqualPayments(boolean equalPayments) {
        this.equalPayments = equalPayments;
    }
    public Double getRecurringPayment() {
        return recurringPayment;
    }
    private void setRecurringPayment(Double recurringPayment) {
        this.recurringPayment = recurringPayment;
    }
    public Integer getNumOfPeriods() {
        return numOfPeriods;
    }
    public void setNumOfPeriods(Integer numOfPeriods) {
        this.numOfPeriods = numOfPeriods;
    }
    public Integer getCompoundPeriod() {
        return compoundPeriod;
    }
    public void setCompoundPeriod(Integer period) {
        this.compoundPeriod = period;
    }
    public List<Double> getUnequalPayments() {
        return unequalPayments;
    }
    public void setUnequalPayments(List<Double> unequalPayments) {
        this.unequalPayments = unequalPayments;
    }
    public Double getEquity() {
        return equity;
    }
    public Double getCurrentLoanPayoff() {
        return currentLoanPayoff;
    }
    public void setCurrentLoanPayoff(Double currentLoanPayoff) {
        this.currentLoanPayoff = currentLoanPayoff;
    }
    public Double getTradeAppraisal() {
        return tradeAppraisal;
    }
    public void setTradeAppraisal(Double tradeAppraisal) {
        this.tradeAppraisal = tradeAppraisal;
    }
    public Double getInsurance() {
        return insurance;
    }
    void setInsurance(Double insurance) {
        this.insurance = insurance;
    }

    public boolean isInvestment() {
        return investment;
    }

    public void setInvestment(boolean investment) {
        this.investment = investment;
    }

    Double getMsrp() {
        return msrp;
    }
    void setMsrp(Double msrp) {
        this.msrp = msrp;
    }
    Double getDownPayment() {
        return downPayment;
    }
    void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }
    public Double getTax() {
        return tax;
    }
    public void setTax(Double tax) {
        this.tax = tax;
    }
    public Double getTitle() {
        return title;
    }
    void setTitle(Double title) {
        this.title = title;
    }
    public boolean isHouse() {
        return house;
    }
    public void setHouse(boolean house) {
        this.house = house;
    }

    public boolean isCar() {
        return car;
    }

    public void setCar(boolean car) {
        this.car = car;
    }
    //    public Double calculateNote(Double interest, Double initialAmount, int period, int numOfPeriods, boolean equalPayments, List<Double> unequalPayments ) {
//    }

    private void calculateEquity(){
        equity = tradeAppraisal - currentLoanPayoff;
    }
    public void calculateLoanPresentValue(){
        if(car){
            initialNetAmount= ((msrp - downPayment - equity) * (1 + tax + title));
        }
        if(house){
            initialNetAmount = ((msrp - downPayment - equity) * (1 + tax));
        }
    }
    public void evaluateLoan () throws Exception {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                //buying a car or home
                setCar(true);
                setTitle(0.01);
                System.out.println("What is the value of your current Vehicle?");
                setTradeAppraisal(scanner.nextDouble());
                System.out.println("What is the value of your vehicle payoff?");
                setCurrentLoanPayoff(scanner.nextDouble());
                calculateEquity();
                break;
            }
            case 2: {
                setHouse(true);
                break;
            }
            case 3: {
                setInvestment(true);
                break;
            }
            default: {
                throw new Exception("The choice is invalid");
            }
        }
        System.out.println("What is the MSRP?");
        setMsrp(scanner.nextDouble());
        System.out.println("What is the Down Payment?");
        setDownPayment(scanner.nextDouble());
        System.out.println("What is the annual interest rate?");
        setInterest(scanner.nextDouble());
        System.out.println("How many years will you be financing?");
        setNumOfPeriods(scanner.nextInt());

        calculateLoanPresentValue();
        System.out.println(initialNetAmount);
    }
    public static void printMenu(){
        System.out.println("Welcome!\n" +
                "What would you like to do today?\n" +
                "1. Buy a car?\n" +
                "2. Buy a home?\n" +
                "3. Investment in a fixed interest account?");
    }
    public Double calculateLoanPaymentValue(){
        double compoundFactor = 1.0 + interest/12;
        System.out.println(compoundFactor);
        numOfPeriods = numOfPeriods * 12;
        recurringPayment = initialNetAmount * ((interest/12) * Math.pow(compoundFactor,(double)numOfPeriods))/(Math.pow(compoundFactor,(double)numOfPeriods)-1.0);
        return (double) Math.round(recurringPayment);
    }



}
