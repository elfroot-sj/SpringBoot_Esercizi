package com.example.math_upgraded_es2;

import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.stereotype.Service

public class MathService {
    public int add(@PathVariable int a, @PathVariable int b) { return a + b; }
    public int subtract(@PathVariable int a, @PathVariable int b) { return a - b; }
    public int multiply(@PathVariable int a, @PathVariable int b) { return a * b; }

    public String divide(@PathVariable float a, @PathVariable float b) {
        if (b == 0) {
            return "Can't divide by zero.";
        }
        float result = a / b;
        //return String.format("Risultato: %.2f", risultato);
        return "Division result: " + result;
    }
}


