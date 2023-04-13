package com.dh.clinica;

import com.dh.clinica.controller.dto.DentistaRequestDTO;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class DentistaServiceTest {

    @Autowired
    DentistaServiceImpl dentistaService;

    @Test
    void deveExcluirDentista_comErro() throws ResourceNotFoundException {
        dentistaService.excluir(1);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> dentistaService.buscar(1));
    }

    @Test
    void deveExcluirDentista_comSucesso() throws ResourceNotFoundException {
        DentistaRequestDTO dentistaRequestDTO = criaDentistaRequest();
        dentistaService.salvar(dentistaRequestDTO);
        dentistaService.excluir(1);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> dentistaService.buscar(1));
    }

    private static DentistaRequestDTO criaDentistaRequest() {
        return DentistaRequestDTO.builder()
                .nome("Nome")
                .sobrenome("Sobrenome")
                .matricula("123456")
                .build();
    }
}
