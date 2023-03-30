package com.dh.clinica.controller;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    final static Logger log = Logger.getLogger(DentistaController.class);

    @Autowired
    private DentistaServiceImpl dentistaServiceImpl;

    @PostMapping
    public ResponseEntity<Dentista> cadastrar(@RequestBody Dentista dentista) {
        log.info("Inciando método cadastrar...");
        ResponseEntity response = null;
        if (validaDentista(dentista)) {
            log.info("Cadastrando Dentista: " + dentista.toString());
            response = ResponseEntity.ok(dentistaServiceImpl.salvar(dentista));
        } else {
            log.info("Não foi possível cadastrar o Dentista, pois não foram informados todos os atributos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }


    @GetMapping
    public ResponseEntity<List<Dentista>> buscarTodos() {
        log.info("Inciando método buscarTodos...");
        ResponseEntity response = null;
        List<Dentista> dentistas = dentistaServiceImpl.buscarTodos();
        if (!dentistas.isEmpty()) {
            log.info("Lista de Dentista encontrada: " + dentistas.toString());
            return ResponseEntity.ok(dentistas);
        } else {
            log.info("Não há Dentistas cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> buscarPorId(@PathVariable Integer id) {
        log.info("Inciando método buscarPorId...");
        Dentista dentista = dentistaServiceImpl.buscar(id).orElse(null);
        if (Objects.nonNull(dentista)) {
            log.info("Dentista encontrado para o id informado: " + dentista.toString());
            return ResponseEntity.ok(dentista);
        } else {
            log.info("Nenhum Dentista foi encontrado para o id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Dentista> buscarPorNome (@PathVariable String nome) {
        return ResponseEntity.ok(dentistaServiceImpl.buscarPorNome(nome).orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        log.info("Inciando método excluir...");
        ResponseEntity<String> response = null;
        if (dentistaServiceImpl.buscar(id).isPresent()) {
            dentistaServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Dentista excluído");
        } else {
            log.info("Não foi encontrado nenhum Dentista com o id: " + id);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro registro foi encontrado para o id informado");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Dentista> atualizar(@RequestBody Dentista dentista) throws Exception {
        log.info("Inciando método atualizar...");
        ResponseEntity response = null;
        if (dentista.getId() != null && dentistaServiceImpl.buscar(dentista.getId()).isPresent()) {
            log.info("Atualizando dentista com o id: " + dentista.getId());
            response = ResponseEntity.ok(dentistaServiceImpl.atualizar(dentista));
        }
        else {
            log.info("Não foi encontrado nenhum dentista com o id: " + dentista.getId());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    private boolean validaDentista(Dentista dentista) {
        return Objects.nonNull(dentista.getNome()) &&
                !dentista.getNome().isEmpty() &&
                !dentista.getNome().isBlank() &&
                Objects.nonNull(dentista.getSobrenome()) &&
                !dentista.getSobrenome().isEmpty() &&
                !dentista.getSobrenome().isBlank() &&
                Objects.nonNull(dentista.getMatricula()) &&
                !dentista.getMatricula().isEmpty() &&
                !dentista.getMatricula().isBlank();
    }
}
