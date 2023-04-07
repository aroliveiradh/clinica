package com.dh.clinica;

import com.dh.clinica.controller.ConsultaController;
import com.dh.clinica.service.impl.ConsultaServiceImpl;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsultaController.class)
class IntegrationConsultaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteServiceImpl pacienteService;

    @MockBean
    private DentistaServiceImpl dentistaService;

    @MockBean
    private ConsultaServiceImpl consultaService;

    @Test
    void deveBuscarListaDeConsultasExistente() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/consultas")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

//        Assertions.assertEquals("application/json", resultActions.getResponse().getContentType());
    }

    @Test
    void deveBuscarListaDeConsultasInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/consultas")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

//        Assertions.assertEquals("application/json", resultActions.getResponse().getContentType());
    }

}
