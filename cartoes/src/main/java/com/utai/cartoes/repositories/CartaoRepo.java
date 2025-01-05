package com.utai.cartoes.repositories;

import com.utai.cartoes.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CartaoRepo extends JpaRepository<Cartao, UUID>{}
