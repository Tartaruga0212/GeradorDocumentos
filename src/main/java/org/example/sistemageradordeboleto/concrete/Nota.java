package org.example.sistemageradordeboleto.concrete;

import org.example.sistemageradordeboleto.abstraction.Documento;
import org.example.sistemageradordeboleto.NotaFiscalRequest;
import org.example.sistemageradordeboleto.implementation.GeradorBridge;

public class Nota extends Documento {

    public Nota(GeradorBridge gerador) {
        super(gerador);
    }

    @Override
    public void gerar(Object dados) {
        NotaFiscalRequest r = (NotaFiscalRequest) dados;
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: NOTA FISCAL\n");
        sb.append("Número NF: ").append(r.numeroNf()).append("\n");
        sb.append("Data de Emissão: ").append(r.dataEmissao()).append("\n");
        sb.append("Cliente: ").append(r.nomeCliente()).append("\n");
        sb.append("CPF/CNPJ: ").append(r.cpfCnpjCliente()).append("\n");
        sb.append("Endereço: ").append(r.enderecoCliente()).append("\n");
        sb.append("---\n");

        double total = 0;
        if (r.itens() != null) {
            int i = 1;
            for (NotaFiscalRequest.ItemNfRequest item : r.itens()) {
                double subtotal = item.quantidade() * item.valorUnitario();
                total += subtotal;
                sb.append("Item ").append(i++).append(" — ").append(item.descricao())
                        .append(": Qtd: ").append(item.quantidade())
                        .append(" × R$ ").append(String.format("%.2f", item.valorUnitario()))
                        .append(" = R$ ").append(String.format("%.2f", subtotal)).append("\n");
            }
        }
        sb.append("---\n");
        sb.append("Total da Nota Fiscal: R$ ").append(String.format("%.2f", total)).append("\n");

        gerador.escrever(sb.toString());
    }
}
