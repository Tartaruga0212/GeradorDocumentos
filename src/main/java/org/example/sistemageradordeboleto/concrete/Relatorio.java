package org.example.sistemageradordeboleto.concrete;

import org.example.sistemageradordeboleto.abstraction.Documento;
import org.example.sistemageradordeboleto.RelatorioRequest;
import org.example.sistemageradordeboleto.implementation.GeradorBridge;

public class Relatorio extends Documento {

    public Relatorio(GeradorBridge gerador) {
        super(gerador);
    }

    @Override
    public void gerar(Object dados) {
        RelatorioRequest r = (RelatorioRequest) dados;
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: RELATÓRIO GERENCIAL\n");
        sb.append("Título: ").append(r.titulo()).append("\n");
        sb.append("Período: ").append(r.periodo()).append("\n");
        sb.append("Responsável: ").append(r.responsavel()).append("\n");
        sb.append("---\n");

        if (r.indicadores() != null) {
            int i = 1;
            for (RelatorioRequest.IndicadorRequest ind : r.indicadores()) {
                sb.append("Indicador ").append(i++).append(" — ").append(ind.nome())
                        .append(": ").append(ind.valor())
                        .append(" | Resultado: ").append(ind.resultado()).append("\n");
            }
        }
        sb.append("---\n");
        sb.append("Observações: ").append(r.observacoes()).append("\n");

        gerador.escrever(sb.toString());
    }
}