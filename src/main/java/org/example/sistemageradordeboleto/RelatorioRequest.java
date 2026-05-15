package org.example.sistemageradordeboleto;

import java.util.List;

public record RelatorioRequest(
        String titulo,
        String periodo,
        String responsavel,
        List<IndicadorRequest> indicadores,
        String observacoes
) {
    public record IndicadorRequest(String nome, String valor, String resultado) {}
}