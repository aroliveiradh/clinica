package com.dh.clinica;

import com.dh.clinica.controller.dto.PacienteRequestDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.model.Endereco;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.impl.EnderecoServiceImpl;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegrationPacienteTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PacienteServiceImpl pacienteService;
    @Autowired
    private EnderecoServiceImpl enderecoService;

    ObjectMapper mapper = new ObjectMapper();

    public void cargarDataSet() throws InvalidDataException {
        Endereco endereco = new Endereco("444", "Av Santa fe", "CABA", "Buenos Aires");
        Paciente paciente = new Paciente("Santiago", "Paz", "88888888", LocalDate.now(), endereco);
        mapper.registerModule(new JavaTimeModule());
        pacienteService.salvar(mapper.convertValue(paciente, PacienteRequestDTO.class));
    }
    @Test
    public void listarPacientes() throws Exception {
        this.cargarDataSet();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
