package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.util.ValidacaoUtil;
import br.com.alura.adopet.api.validacoes.ValidacaoAbrigoJaCadastrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private ValidacaoAbrigoJaCadastrado validacaoCadastro;

    @Autowired
    private ValidacaoUtil validacaoUtil;

    public List<Abrigo> listar() {
        return repository.findAll();
    }

    public void cadastrar(CadastrarAbrigoDto dto) {
        validacaoCadastro.validar(dto);

        var abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());

        repository.save(abrigo);
    }

    public List<Pet> listarPets(String idOuNome) {
        List<Pet> pets = buscarPets(idOuNome);

        if (pets.isEmpty()) {
            throw new EntityNotFoundException("Pets não encontrado no abrigo!");
        }

        return pets;
    }

    public void cadastrarPet(String idOuNome, CadastrarPetDto dto) {
        var pets = listarPets(idOuNome);
        var pet = new Pet(dto.tipo(), dto.nome(), dto.raca(), dto.idade(), dto.cor(), dto.peso());

        pet.marcarComoNaoAdotado();
        pets.add(pet);
    }

    private List<Pet> buscarPets(String idOuNome) {
        if (validacaoUtil.isNumeric(idOuNome)) {
            return repository.getReferenceById(Long.parseLong(idOuNome))
                    .getPets();
        }

        return repository.findByNome(idOuNome)
                .getPets();
    }
}
