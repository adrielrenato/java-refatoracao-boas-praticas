package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.CadastrarTutorDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tutores")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String email;

    @OneToMany(mappedBy = "tutor")
    private List<Adocao> adocoes;

    public Tutor() {}

    public Tutor(CadastrarTutorDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
    }

    public void atualizarDados(AtualizarTutorDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(id, tutor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Adocao> getAdocoes() {
        return adocoes;
    }
}
