package com.utai.cartoes.controllers;

import com.utai.cartoes.entities.Cartao;
import com.utai.cartoes.entities.dto.CartaoDTO;
import com.utai.cartoes.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @GetMapping
    public List<Cartao> listaCartoes(){
        return cartaoService.listaCartoes();
    }

    @PostMapping
    public void criaCartao(@RequestBody CartaoDTO dto){
        cartaoService.criaCartao(dto);
    }

    @PutMapping("/{id}")
    public Optional<Cartao> atualizaCartao(@PathVariable UUID id, @RequestBody CartaoDTO cartaoAtualizado){
        return cartaoService.atualizaCartao(id, cartaoAtualizado);
    }

    @GetMapping("/{id}")
    public Optional<Cartao> buscaPorId(@PathVariable UUID id){
        return cartaoService.buscaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletaCartao(@PathVariable UUID id){
        cartaoService.deletaCartao(id);
    }




}
