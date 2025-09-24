package com.example.math_upgraded;
//qui chiamo solo le rotte

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@org.springframework.stereotype.Controller

@RestController //dichiara questa classe come controller per gestire le richieste HTTP
@RequestMapping("/math") //imposta una rotta "di base" da cui partire
public class Controller {

    private final MathService mathService;
    public Controller(MathService mathService) {
        this.mathService = mathService;
    } //costruttore

    // localhost:8080/math/add/2/3
    @GetMapping("/add/{a}/{b}")
    public int add(@PathVariable int a, @PathVariable int b) {
        return mathService.add(a, b);
    }

    // localhost:8080/math/subtract/10/3
    @GetMapping("/subtract/{a}/{b}")
    public int subtract(@PathVariable int a, @PathVariable int b) {
        return mathService.subtract(a, b);
    }

    // localhost:8080/math/multiply/2/3
    @GetMapping("/multiply/{a}/{b}")
    public int multiply(@PathVariable int a, @PathVariable int b) {
        return mathService.multiply(a, b);
    }

    // localhost:8080/math/divide/10/2
    @GetMapping("/divide/{a}/{b}")
    public String divide(@PathVariable float a, @PathVariable float b) {
        return mathService.divide(a, b);
    }

}


