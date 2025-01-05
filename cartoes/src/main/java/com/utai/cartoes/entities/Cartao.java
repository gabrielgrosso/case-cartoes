package com.utai.cartoes.entities;

import com.utai.cartoes.enums.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cartoes")
public class Cartao {
    @Id
    private UUID id = UUID.randomUUID();
    private String nome;
    private Bandeira bandeira;
    private NivelCartao nivelCartao;

    @ManyToMany
    private List<Beneficio> beneficios;

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

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<Beneficio> beneficios) {
        this.beneficios = beneficios;
    }
}
