package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService service;

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<AbrigoDto>> listar() {
        return ResponseEntity.ok(this.service.listar());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarAbrigoDto dto) {
        try {
            this.service.cadastrar(dto);

            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro abrigo!");
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetDto>> listarPets(@PathVariable String idOuNome) {
        try {
            var pets = this.service.listarPets(idOuNome);

            return ResponseEntity.ok(pets);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastrarPetDto dto) {
        try {
            var abrigo = this.service.carregaAbrigo(idOuNome);
            this.petService.cadastrarPet(abrigo, dto);

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
