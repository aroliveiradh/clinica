package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/dentistas")
public class DentistaController {

    final static Logger log = Logger.getLogger(DentistaController.class);

    @Autowired
    private DentistaServiceImpl dentistaServiceImpl;

    @PostMapping
    public ResponseEntity<DentistaResponse> cadastrar(@RequestBody DentistaRequest request) throws InvalidDataException {
        log.info("Inciando método cadastrar...");
        ResponseEntity response = null;
        if (validaDentista(request)) {
            log.info("Cadastrando Dentista: " + request.toString());
            response = ResponseEntity.ok(dentistaServiceImpl.salvar(request));
        } else {
            throw new InvalidDataException("Não foi possível cadastrar o Dentista, pois não foram informados todos os atributos");
        }
        return response;
    }


    @GetMapping
    public ResponseEntity<List<DentistaResponse>> buscarTodos() throws ResourceNotFoundException {
        log.info("Inciando método buscarTodos...");
        List<DentistaResponse> dentistas = dentistaServiceImpl.buscarTodos();
        if (!dentistas.isEmpty()) {
            log.info("Lista de Dentista encontrada: " + dentistas.toString());
            return ResponseEntity.ok(dentistas);
        } else {
            throw new ResourceNotFoundException("Não há registros de Dentistas");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaResponse> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorId...");
        DentistaResponse dentistaResponse = dentistaServiceImpl.buscar(id);
        return ResponseEntity.ok(dentistaResponse);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<DentistaResponse> buscarPorNome (@PathVariable String nome) {
        log.info("Inciando método buscarPorNome...");
        DentistaResponse dentistaResponse = dentistaServiceImpl.buscarPorNome(nome).orElse(null);
        if (Objects.nonNull(dentistaResponse)) {
            log.info("Dentista encontrado: " + dentistaResponse.toString());
            return ResponseEntity.ok(dentistaResponse);
        } else {
            log.info("Nenhum Dentista foi encontrado com o nome: " + nome);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
//        log.info("Inciando método excluir...");
//        ResponseEntity<String> response = null;
//        if (dentistaServiceImpl.buscar(id) != null) {
//            dentistaServiceImpl.excluir(id);
//            response = ResponseEntity.status(HttpStatus.OK).body("Dentista excluído");
//        } else {
//            log.info("Não foi encontrado nenhum Dentista com o id: " + id);
//            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro registro foi encontrado para o id informado");
//        }
        return null;
    }

    @PutMapping
    public ResponseEntity<DentistaResponse> atualizar(@RequestBody DentistaRequest dentista) throws Exception {
        log.info("Inciando método atualizar...");
        ResponseEntity response = null;
        if (dentista.getMatricula() != null && dentistaServiceImpl.buscarPorMatricula(dentista.getMatricula()) != null) {
            log.info("Atualizando dentista com matricula: " + dentista.getMatricula());
            response = ResponseEntity.ok(dentistaServiceImpl.atualizar(dentista));
        }
        else {
            log.info("Não foi encontrado nenhum dentista com a matricula: " + dentista.getMatricula());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    private boolean validaDentista(DentistaRequest request) {
        return Objects.nonNull(request.getNome()) &&
                !request.getNome().isEmpty() &&
                !request.getNome().isBlank() &&
                Objects.nonNull(request.getSobrenome()) &&
                !request.getSobrenome().isEmpty() &&
                !request.getSobrenome().isBlank()&&
                Objects.nonNull(request.getMatricula()) &&
                !request.getMatricula().isEmpty() &&
                !request.getMatricula().isBlank();
    }
}
