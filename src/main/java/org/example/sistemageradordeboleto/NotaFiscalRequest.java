package org.example.sistemageradordeboleto;

import java.time.LocalDate;
import java.util.List;

public record NotaFiscalRequest(
        String numeroNf,
        LocalDate dataEmissao,
        String nomeCliente,
        String cpfCnpjCliente,
        String enderecoCliente,
        List<ItemNfRequest> itens
) {
    public record ItemNfRequest(String descricao, int quantidade, double valorUnitario) {}
}
