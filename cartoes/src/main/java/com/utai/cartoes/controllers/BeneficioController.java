package com.utai.cartoes.controllers;

import com.utai.cartoes.entities.Beneficio;
import com.utai.cartoes.service.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioService beneficioService;

    @GetMapping
    public List<Beneficio> listaBeneficios(){
        return beneficioService.listaBeneficios();
    }

    @PostMapping
    public void criaBeneficio(@RequestBody Beneficio beneficio){
        beneficioService.criaBeneficio(beneficio);
    }

    @PutMapping("{id}")
    public Beneficio atualizaBeneficio(@PathVariable UUID id, @RequestBody Beneficio beneficio){
        return beneficioService.atualizaBeneficio(id, beneficio);
    }

    @DeleteMapping("{id}")
    public void deletaBeneficio(@PathVariable UUID id){
        beneficioService.deletaBeneficio(id);
    }


}
