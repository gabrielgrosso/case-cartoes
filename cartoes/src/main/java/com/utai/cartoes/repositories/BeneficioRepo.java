package com.utai.cartoes.repositories;

import com.utai.cartoes.entities.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeneficioRepo extends JpaRepository<Beneficio, UUID> {
}
