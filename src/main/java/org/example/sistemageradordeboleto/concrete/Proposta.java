package org.example.sistemageradordeboleto.concrete;

import org.example.sistemageradordeboleto.abstraction.Documento;
import org.example.sistemageradordeboleto.PropostaRequest;
import org.example.sistemageradordeboleto.implementation.GeradorBridge;

public class Proposta extends Documento {

    public Proposta(GeradorBridge gerador) {
        super(gerador);
    }

    @Override
    public void gerar(Object dados) {
        PropostaRequest r = (PropostaRequest) dados;
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: PROPOSTA COMERCIAL\n");
        sb.append("Número da Proposta: ").append(r.numeroProposta()).append("\n");
        sb.append("Data de Emissão: ").append(r.dataEmissao()).append("\n");
        sb.append("Válida até: ").append(r.validade()).append("\n");
        sb.append("Cliente: ").append(r.cliente()).append("\n");
        sb.append("Fornecedor: ").append(r.fornecedor()).append("\n");
        sb.append("---\n");

        double total = 0;
        if (r.itens() != null) {
            int i = 1;
            for (PropostaRequest.ItemPropostaRequest item : r.itens()) {
                total += item.valor();
                sb.append("Item ").append(i++).append(" — ").append(item.descricao())
                        .append(": R$ ").append(String.format("%.2f", item.valor())).append("\n");
            }
        }
        sb.append("---\n");
        sb.append("Valor Total: R$ ").append(String.format("%.2f", total)).append("\n");
        sb.append("Condições de Pagamento: ").append(r.condicoesPagamento()).append("\n");

        gerador.escrever(sb.toString());
    }
}
