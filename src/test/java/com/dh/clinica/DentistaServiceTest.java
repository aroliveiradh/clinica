package com.dh.clinica;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;
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
        DentistaRequest dentistaRequest = criaDentistaRequest();
        dentistaService.salvar(dentistaRequest);
        dentistaService.excluir(1);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> dentistaService.buscar(1));
    }

    private static DentistaRequest criaDentistaRequest() {
        return DentistaRequest.builder()
                .nome("Nome")
                .sobrenome("Sobrenome")
                .matricula("123456")
                .build();
    }
}
