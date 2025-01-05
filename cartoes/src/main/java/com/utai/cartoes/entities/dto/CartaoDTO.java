package com.utai.cartoes.entities.dto;

import com.utai.cartoes.enums.Bandeira;
import com.utai.cartoes.enums.NivelCartao;

import java.util.List;
import java.util.UUID;

public class CartaoDTO {

    private UUID id;
    private String nome;
    private Bandeira bandeira;
    private NivelCartao nivelCartao;
    private List<UUID> beneficios;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public NivelCartao getNivelCartao() {
        return nivelCartao;
    }

    public void setNivelCartao(NivelCartao nivelCartao) {
        this.nivelCartao = nivelCartao;
    }

    public List<UUID> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<UUID> beneficios) {
        this.beneficios = beneficios;
    }
}

