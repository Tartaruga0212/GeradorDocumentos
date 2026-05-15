package org.example.sistemageradordeboleto.concrete;

import org.example.sistemageradordeboleto.abstraction.Documento;
import org.example.sistemageradordeboleto.ContratoRequest;
import org.example.sistemageradordeboleto.implementation.GeradorBridge;

public class Contrato extends Documento {

    public Contrato(GeradorBridge gerador) {
        super(gerador);
    }

    @Override
    public void gerar(Object dados) {
        ContratoRequest r = (ContratoRequest) dados;
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: CONTRATO SIMPLIFICADO\n");
        sb.append("Número do Contrato: ").append(r.numeroContrato()).append("\n");
        sb.append("Data de Assinatura: ").append(r.dataAssinatura()).append("\n");
        sb.append("Parte A (Contratante): ").append(r.parteA()).append("\n");
        sb.append("Parte B (Contratada): ").append(r.parteB()).append("\n");
        sb.append("Objeto do Contrato: ").append(r.objeto()).append("\n");
        sb.append("Prazo: ").append(r.prazo()).append("\n");
        sb.append("---\n");

        if (r.clausulas() != null) {
            int i = 1;
            for (String cl : r.clausulas()) {
                sb.append("Cláusula ").append(i++).append(": ").append(cl).append("\n");
            }
        }
        sb.append("---\n");
        sb.append("Assinatura Parte A: ___________________________\n");
        sb.append("Assinatura Parte B: ___________________________\n");

        gerador.escrever(sb.toString());
    }
}
