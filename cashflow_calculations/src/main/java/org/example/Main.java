package org.example;




public class Main {
    public static void main(String[] args) {
        
        CashFlow loan = new CashFlow();
        boolean continueLoop = true;
        while(continueLoop) {
            try {
                loan.evaluateLoan();
                continueLoop = false;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        Double payments = loan.calculateLoanPaymentValue();
        System.out.println(payments);
    }
}