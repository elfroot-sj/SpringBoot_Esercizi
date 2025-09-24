package com.example.math_upgraded_es2;

@org.springframework.stereotype.Service

public class MathService {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
    public int multiply(int a, int b) { return a * b; }

    public String divide(float a, float b) {
        if (b == 0) {
            return "Can't divide by zero.";
        }
        float result = a / b;
        return "Division result: " + result;
    }
}

