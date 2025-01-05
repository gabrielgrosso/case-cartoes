package com.utai.cartoes.service;

import com.utai.cartoes.entities.*;
import com.utai.cartoes.entities.dto.CartaoDTO;
import com.utai.cartoes.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

            cartao.setNome(cartaoAtualizado.getNome());
            cartao.setBandeira(cartaoAtualizado.getBandeira());
            cartao.setNivelCartao(cartaoAtualizado.getNivelCartao());

            List<Beneficio> beneficios = new ArrayList<>();
            for (UUID beneficioId : cartaoAtualizado.getBeneficios()) {
                Beneficio beneficio = beneficioRepo.findById(beneficioId)
                        .orElseThrow(() -> new RuntimeException("Benefício não encontrado: " + beneficioId));
                beneficios.add(beneficio);
            }
            cartao.setBeneficios(beneficios);

            cartaoRepo.save(cartao);

            return Optional.of(cartao);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar dados do cartão com id " + id);
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
