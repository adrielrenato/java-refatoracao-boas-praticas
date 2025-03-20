package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.CadastrarTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTutorJaCadastrado {
    @Autowired
    private TutorRepository repository;

    public void validar(CadastrarTutorDto dto) {
        var telefoneOuEmailJaCadastrado = repository.existsByEmailOrTelefone(dto.email(), dto.telefone());

        if (telefoneOuEmailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        }
    }
}
