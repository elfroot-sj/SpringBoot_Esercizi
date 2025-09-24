package com.example.math_upgraded_es2;

import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller

@RestController //dichiara questa classe come controller per gestire le richieste HTTP
public class Controller {

    private final MathService mathService;
    public Controller(MathService mathService) {
        this.mathService = mathService;
    }

    // localhost:8080/add/2/3
    @RequestMapping("/add/{a}/{b}")
    public int add(@PathVariable int a, @PathVariable int b) {
        return mathService.add(a, b);
    }

    // localhost:8080/subtract?a=10&b=3
    @GetMapping("/subtract")
    public int subtract(@RequestParam int a, @RequestParam int b) {
        return mathService.subtract(a, b);
    }

    // localhost:8080/multiply
    @PostMapping("/multiply")
    public int multiply(@RequestBody JsonOperands operands) {
        return mathService.multiply(operands.getA(), operands.getB());
    }

    // localhost:8080/divide/10/2
    @RequestMapping("/divide/{a}/{b}")
    public String divide(@PathVariable float a, @PathVariable float b) {
        return mathService.divide(a, b);
    }

}
