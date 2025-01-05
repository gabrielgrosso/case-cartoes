package com.utai.cartoes.service;

import com.utai.cartoes.entities.Beneficio;
import com.utai.cartoes.repositories.BeneficioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BeneficioService {

    @Autowired
    private BeneficioRepo beneficioRepo;

    public List<Beneficio> listaBeneficios() {
        return beneficioRepo.findAll();
    }

    public void criaBeneficio(Beneficio beneficio) {
        beneficioRepo.save(beneficio);
    }

    public Beneficio atualizaBeneficio(UUID id, Beneficio beneficioAtualizado) {
        Optional<Beneficio> beneficioExistente = beneficioRepo.findById(id);

        if (beneficioExistente.isPresent()) {
            Beneficio beneficio = beneficioExistente.get();
            beneficio.setDescricao(beneficioAtualizado.getDescricao());
            return beneficioRepo.save(beneficio);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Benefício com id " + id + " não encontrado.");
    }

    public Optional<Beneficio> buscaPorId(UUID id) {
        return beneficioRepo.findById(id);
    }

    public void deletaBeneficio(UUID id) {
        beneficioRepo.deleteById(id);
    }

}
