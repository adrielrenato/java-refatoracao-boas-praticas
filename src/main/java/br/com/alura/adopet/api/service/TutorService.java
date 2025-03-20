package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.CadastrarTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoTutorJaCadastrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {
    @Autowired
    private TutorRepository repository;

    @Autowired
    private ValidacaoTutorJaCadastrado validacao;

    public void cadastrar(CadastrarTutorDto dto) {
        validacao.validar(dto);

        repository.save(new Tutor(dto));
    }

    public void atualizar(AtualizarTutorDto dto) {
        var tutor = repository.getReferenceById(dto.id());

        tutor.atualizarDados(dto);
    }
}
