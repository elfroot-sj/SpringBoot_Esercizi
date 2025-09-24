package com.example.calcolatrice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalcController {

    //localhost:8080/somma/2/3
    @RequestMapping (path = "/somma/{num1}/{num2}")
    public @ResponseBody
    float somma(@PathVariable float num1, @PathVariable float num2) {
        return num1 + num2;
    }

    //localhost:8080/differenza/5/3
    @RequestMapping (path = "/differenza/{num1}/{num2}")
    public @ResponseBody
    float differenza(@PathVariable float num1, @PathVariable float num2) {
        return num1 - num2;
    }

    //localhost:8080/moltiplicazione/3/6
    @RequestMapping (path = "/moltiplicazione/{num1}/{num2}")
    public @ResponseBody
    float moltiplicazione(@PathVariable float num1, @PathVariable float num2) {
        return num1 * num2;
    }

    //localhost:8080/divisione/6/2
    @RequestMapping (path = "/divisione/{num1}/{num2}")
    public @ResponseBody
    String divisione(@PathVariable float num1, @PathVariable float num2) {
        if (num2 == 0) {
            return "Impossibile dividere per zero. Scegli un altro denominatore.";
        }
        float risultato = num1 / num2;
        //return String.format("Risultato: %.2f", risultato);
        return "Risultato divisione: " + risultato;
    }
}
