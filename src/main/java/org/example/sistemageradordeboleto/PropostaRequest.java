package org.example.sistemageradordeboleto;

import java.time.LocalDate;
import java.util.List;

public record PropostaRequest(
        String numeroProposta,
        LocalDate dataEmissao,
        LocalDate validade,
        String cliente,
        String fornecedor,
        List<ItemPropostaRequest> itens,
        String condicoesPagamento
) {
    public record ItemPropostaRequest(String descricao, double valor) {}
}