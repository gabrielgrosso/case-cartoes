package com.utai.cartoes.service;

import com.utai.cartoes.entities.*;
import com.utai.cartoes.entities.dto.CartaoDTO;
import com.utai.cartoes.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartaoService {

    @Autowired
    private BeneficioRepo beneficioRepo;
    @Autowired
    private CartaoRepo cartaoRepo;

    public List<Cartao> listaCartoes() {
        return cartaoRepo.findAll();
    }

    public void criaCartao(CartaoDTO cartaoRequest) {
        Cartao cartao = cartaoDTOToCartaoTranslator(cartaoRequest);
        cartaoRepo.save(cartao);
    }

    public Optional<Cartao> atualizaCartao(UUID id, CartaoDTO cartaoAtualizado) {
        Optional<Cartao> cartaoExistente = cartaoRepo.findById(id);

        if (cartaoExistente.isPresent()) {
            Cartao cartao = cartaoExistente.get();

            // Atualiza apenas se os valores forem fornecidos no DTO
            if (cartaoAtualizado.getNome() != null && !cartaoAtualizado.getNome().isEmpty()) {
                cartao.setNome(cartaoAtualizado.getNome());
            }

            if (cartaoAtualizado.getBandeira() != null) {
                cartao.setBandeira(cartaoAtualizado.getBandeira());
            }

            if (cartaoAtualizado.getNivelCartao() != null) {
                cartao.setNivelCartao(cartaoAtualizado.getNivelCartao());
            }

            if (cartaoAtualizado.getBeneficios() != null && !cartaoAtualizado.getBeneficios().isEmpty()) {
                List<Beneficio> beneficios = new ArrayList<>();
                for (UUID beneficioId : cartaoAtualizado.getBeneficios()) {
                    Beneficio beneficio = beneficioRepo.findById(beneficioId)
                            .orElseThrow(() -> new RuntimeException("Benefício não encontrado: " + beneficioId));
                    beneficios.add(beneficio);
                }
                cartao.setBeneficios(beneficios);
            }

            cartaoRepo.save(cartao);

            return Optional.of(cartao);
        }

        return Optional.empty();
    }


    public Optional<Cartao> buscaPorId(UUID id) {
        return cartaoRepo.findById(id);
    }

    public void deletaCartao(UUID id) {
        cartaoRepo.deleteById(id);
    }

    public Cartao cartaoDTOToCartaoTranslator(CartaoDTO dto) {
        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setBandeira(dto.getBandeira());
        cartao.setNivelCartao(dto.getNivelCartao());

        List<Beneficio> beneficios = new ArrayList<>();
        for (UUID beneficioId : dto.getBeneficios()) {
            Beneficio beneficio = beneficioRepo.findById(beneficioId)
                    .orElseThrow(() -> new RuntimeException("Benefício não encontrado: " + beneficioId));
            beneficios.add(beneficio);
        }
        cartao.setBeneficios(beneficios);

        return cartao;
    }
}
