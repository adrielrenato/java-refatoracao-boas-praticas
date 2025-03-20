package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.util.ValidacaoUtil;
import br.com.alura.adopet.api.validacoes.ValidacaoAbrigoJaCadastrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ValidacaoAbrigoJaCadastrado validacaoCadastro;

    @Autowired
    private ValidacaoUtil validacaoUtil;

    public List<AbrigoDto> listar() {
        return repository.findAll()
                .stream()
                .map(AbrigoDto::new)
                .toList();
    }

    public void cadastrar(CadastrarAbrigoDto dto) {
        validacaoCadastro.validar(dto);

        repository.save(new Abrigo(dto.nome(), dto.telefone(), dto.email()));
    }

    public List<PetDto> listarPets(String idOuNome) {
        var abrigo = carregaAbrigo(idOuNome);

        return petRepository.findByAbrigo(abrigo)
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public Abrigo carregaAbrigo(String idOuNome) {
        var abrigo = buscarAbrigo(idOuNome);

        if (abrigo.isEmpty()) {
            throw new EntityNotFoundException("Abrigo não encontrado");
        }

        return abrigo.get();
    }


    private Optional<Abrigo> buscarAbrigo(String idOuNome) {
        if (validacaoUtil.isNumeric(idOuNome)) {
            return repository.findById(Long.parseLong(idOuNome));
        }

        return repository.findByNome(idOuNome);
    }
}
