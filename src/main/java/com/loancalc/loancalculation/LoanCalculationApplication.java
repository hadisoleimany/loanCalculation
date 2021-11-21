package com.loancalc.loancalculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;

@SpringBootApplication
@RestController("/")
public class LoanCalculationApplication {

    public static void main(String[] args)
    {

        SpringApplication.run(LoanCalculationApplication.class, args);
    }
    private static final int MONTH_OF_YEAR=12;
    @GetMapping("/calc")
    public String getCalculationLoan(@RequestParam(name = "principal") Double principal ,@RequestParam(name = "annualInterestRate")
            Float annualInterestRate,@RequestParam(name = "terms") int terms ,@RequestParam(name = "frq",defaultValue = "0") int frq){
        float monthlyInterestRate= annualInterestRate/MONTH_OF_YEAR;
        int numberOfPayments = terms* MONTH_OF_YEAR;
         double monthlyPayment=principal *
                 (
                         (monthlyInterestRate * (Math.pow(1 + monthlyInterestRate ,numberOfPayments)))/
                                 ((Math.pow(1+monthlyInterestRate,numberOfPayments))-1)
                         );
         StringBuilder builder=new StringBuilder();


             for (int i=0;i<frq;i++){

             builder.append(NumberFormat.getCurrencyInstance().format(((monthlyPayment * numberOfPayments)/frq))).append("\n");
         }
        builder.append("Total Payment :" +NumberFormat.getCurrencyInstance().format(monthlyPayment * numberOfPayments));
         return builder.toString();
    }
}
