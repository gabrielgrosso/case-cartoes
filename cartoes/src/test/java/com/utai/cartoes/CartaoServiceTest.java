package com.utai.cartoes;

import com.utai.cartoes.entities.Cartao;
import com.utai.cartoes.entities.Beneficio;
import com.utai.cartoes.entities.dto.CartaoDTO;
import com.utai.cartoes.enums.Bandeira;
import com.utai.cartoes.enums.NivelCartao;
import com.utai.cartoes.repositories.BeneficioRepo;
import com.utai.cartoes.repositories.CartaoRepo;
import com.utai.cartoes.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CartaoServiceTest {

    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepo cartaoRepo;

    @Mock
    private BeneficioRepo beneficioRepo;

    private CartaoDTO cartaoDTO;
    private Cartao cartao;
    private Beneficio beneficio;
    private UUID beneficioId;
    private UUID cartaoId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Benefício
        beneficioId = UUID.randomUUID();
        beneficio = new Beneficio();
        beneficio.setDescricao("Benefício 1");

        // Cartão
        cartaoId = UUID.randomUUID();
        cartao = new Cartao();
        cartao.setId(cartaoId);
        cartao.setNome("Cartão Teste");
        cartao.setBandeira(Bandeira.MASTERCARD);
        cartao.setNivelCartao(NivelCartao.PLATINUM);

        // CartaoDTO
        cartaoDTO = new CartaoDTO();
        cartaoDTO.setNome("Cartão Teste");
        cartaoDTO.setBandeira(Bandeira.MASTERCARD);
        cartaoDTO.setNivelCartao(NivelCartao.PLATINUM);
        cartaoDTO.setBeneficios(Collections.singletonList(beneficioId));
    }

    @Test
    void testListaCartoes() {
        List<Cartao> cartoes = Arrays.asList(cartao);
        when(cartaoRepo.findAll()).thenReturn(cartoes);

        List<Cartao> result = cartaoService.listaCartoes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cartaoId, result.get(0).getId());
    }

    @Test
    void testCriaCartao() {
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.of(beneficio));
        when(cartaoRepo.save(any(Cartao.class))).thenReturn(cartao);

        cartaoService.criaCartao(cartaoDTO);

        verify(cartaoRepo, times(1)).save(any(Cartao.class));
    }

    @Test
    void testAtualizaCartao() {
        when(cartaoRepo.findById(cartaoId)).thenReturn(Optional.of(cartao));
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.of(beneficio));
        when(cartaoRepo.save(any(Cartao.class))).thenReturn(cartao);

        CartaoDTO cartaoAtualizadoDTO = new CartaoDTO();
        cartaoAtualizadoDTO.setNome("Novo Nome");

        Optional<Cartao> updatedCartao = cartaoService.atualizaCartao(cartaoId, cartaoAtualizadoDTO);

        assertTrue(updatedCartao.isPresent());
        assertEquals("Novo Nome", updatedCartao.get().getNome());
    }

    @Test
    void testAtualizaCartaoQuandoCartaoNaoEncontrado() {
        when(cartaoRepo.findById(cartaoId)).thenReturn(Optional.empty());

        CartaoDTO cartaoAtualizadoDTO = new CartaoDTO();
        cartaoAtualizadoDTO.setNome("Novo Nome");

        Optional<Cartao> updatedCartao = cartaoService.atualizaCartao(cartaoId, cartaoAtualizadoDTO);

        assertFalse(updatedCartao.isPresent());
    }

    @Test
    void testBuscaPorId() {
        when(cartaoRepo.findById(cartaoId)).thenReturn(Optional.of(cartao));

        Optional<Cartao> result = cartaoService.buscaPorId(cartaoId);

        assertTrue(result.isPresent());
        assertEquals(cartaoId, result.get().getId());
    }

    @Test
    void testDeletaCartao() {
        doNothing().when(cartaoRepo).deleteById(cartaoId);

        cartaoService.deletaCartao(cartaoId);

        verify(cartaoRepo, times(1)).deleteById(cartaoId);
    }

    @Test
    void testCartaoDTOToCartaoTranslator() {
        when(beneficioRepo.findById(beneficioId)).thenReturn(Optional.of(beneficio));

        Cartao result = cartaoService.cartaoDTOToCartaoTranslator(cartaoDTO);

        assertNotNull(result);
        assertEquals("Cartão Teste", result.getNome());
        assertEquals(Bandeira.MASTERCARD, result.getBandeira());
        assertEquals(NivelCartao.PLATINUM, result.getNivelCartao());
        assertEquals(1, result.getBeneficios().size());
    }
}
