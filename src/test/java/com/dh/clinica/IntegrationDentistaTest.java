package com.dh.clinica;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class IntegrationDentistaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DentistaServiceImpl dentistaService;

    void loadDataSet() {
        this.dentistaService.salvar(new DentistaRequest("Ana", "Marta", "123"));
    }

    @Test
    void deveCadastrarDentistaComSucesso() throws Exception {
        DentistaRequest payLoadDto = new DentistaRequest("Ana", "Marta", "123");
        DentistaResponse responseDto = new DentistaResponse("Ana", "Marta");

        Mockito.when(dentistaService.salvar(Mockito.any())).thenReturn(responseDto);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String payLoadJson = writer.writeValueAsString(payLoadDto);
        String responseJson = writer.writeValueAsString(responseDto);

        MvcResult mvcResult = mockMvc.perform(post("/dentistas")
                        .content(payLoadJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        Assertions.assertEquals(responseJson, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void deveBuscarListaDeDentistasExistente() throws Exception {
        this.loadDataSet();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/dentistas").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void deveBuscarDentistasPorId_comSucesso() throws Exception {
        this.loadDataSet();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/dentistas/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void deveBuscarListaDeDentistasInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dentistas")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    private DentistaRequest criaDentistaRequestMock() {
        return DentistaRequest.builder()
                .nome("Ana")
                .sobrenome("Maria")
                .matricula("123456")
                .build();
    }

}
