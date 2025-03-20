package br.com.alura.adopet.api.util;

import org.springframework.stereotype.Component;

@Component
public class ValidacaoUtil {
    public boolean isNumeric(String valor) {
        return valor.matches("\\d+");
    }
}
