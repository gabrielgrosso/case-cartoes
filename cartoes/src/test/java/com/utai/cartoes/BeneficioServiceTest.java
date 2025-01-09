package com.utai.cartoes;

import com.utai.cartoes.entities.Beneficio;
import com.utai.cartoes.repositories.BeneficioRepo;
import com.utai.cartoes.service.BeneficioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BeneficioServiceTest {

    @InjectMocks
    private BeneficioService beneficioService;

    @Mock
    private BeneficioRepo beneficioRepo;

    private Beneficio beneficio;
    private UUID beneficioId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        beneficio = new Beneficio();
        beneficio.setDescricao("Benefício Teste");
    }

    @Test
    void testListaBeneficios() {
        List<Beneficio> beneficios = Arrays.asList(beneficio);
        when(beneficioRepo.findAll()).thenReturn(beneficios);

        List<Beneficio> result = beneficioService.listaBeneficios();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testCriaBeneficio() {
        when(beneficioRepo.save(any(Beneficio.class))).thenReturn(beneficio);

        beneficioService.criaBeneficio(beneficio);

        verify(beneficioRepo, times(1)).save(any(Beneficio.class));
    }

    @Test
    void testAtualizaBeneficio() {
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.of(beneficio));
        when(beneficioRepo.save(any(Beneficio.class))).thenReturn(beneficio);

        Beneficio beneficioAtualizado = new Beneficio();
        beneficioAtualizado.setDescricao("Novo Benefício");

        Beneficio result = beneficioService.atualizaBeneficio(beneficioId, beneficioAtualizado);

        assertEquals("Novo Benefício", result.getDescricao());
        verify(beneficioRepo, times(1)).save(any(Beneficio.class));
    }

    @Test
    void testAtualizaBeneficioQuandoBeneficioNaoEncontrado() {
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.empty());

        Beneficio beneficioAtualizado = new Beneficio();
        beneficioAtualizado.setDescricao("Novo Benefício");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            beneficioService.atualizaBeneficio(beneficioId, beneficioAtualizado);
        });

        assertEquals("404 NOT_FOUND \"Benefício com id " + beneficioId + " não encontrado.\"", exception.getMessage());
    }

    @Test
    void testBuscaPorId() {
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.of(beneficio));

        Optional<Beneficio> result = beneficioService.buscaPorId(beneficioId);

        assertTrue(result.isPresent());
    }

    @Test
    void testBuscaPorIdQuandoBeneficioNaoEncontrado() {
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            beneficioService.buscaPorId(beneficioId);
        });

        assertEquals("404 NOT_FOUND \"Benefício com id " + beneficioId + " não encontrado.\"", exception.getMessage());
    }

//    @Test
//    void testDeletaBeneficio() {
//        doNothing().when(beneficioRepo).deleteById(beneficioId);
//
//        beneficioService.deletaBeneficio(beneficioId);
//
//        verify(beneficioRepo, times(1)).deleteById(beneficioId);
//    }

    @Test
    void testDeletaBeneficioQuandoBeneficioNaoEncontrado() {
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            beneficioService.deletaBeneficio(beneficioId);
        });

        assertEquals("404 NOT_FOUND \"Benefício com id " + beneficioId + " não encontrado.\"", exception.getMessage());
    }
}
