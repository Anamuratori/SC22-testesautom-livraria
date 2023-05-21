package tech.ada.testesfinal.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.testesfinal.exception.BadRequestException;
import tech.ada.testesfinal.model.dto.LivroDTO;
import tech.ada.testesfinal.model.dto.MensagemDTO;
import tech.ada.testesfinal.service.LivroService;

@Slf4j
@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody @Valid LivroDTO livroDTO){

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(livroService.criar(livroDTO));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MensagemDTO(e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<Object> listar() {
        try {
            return ResponseEntity.ok(livroService.listar());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MensagemDTO(e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") Long id){

        try {
            return ResponseEntity.ok(livroService.buscarPorId(id));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MensagemDTO(e.getMessage()));

        }
    }
}