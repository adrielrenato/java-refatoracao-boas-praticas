package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;

public record PetDto(
        Long id,
        TipoPet tipo,
        String nome,
        String raca,
        Integer idade,
        String cor,
        Float peso,
        Abrigo abrigo,
        Boolean adotado
) {
    public PetDto(Pet pet) {
        this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAbrigo(), pet.getAdotado());
    }
}
