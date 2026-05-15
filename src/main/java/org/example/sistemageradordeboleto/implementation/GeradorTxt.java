package org.example.sistemageradordeboleto.implementation;

import java.nio.charset.StandardCharsets;

public class GeradorTxt implements GeradorBridge {

    private byte[] resultado;

    @Override
    public void escrever(String conteudo) {
        int largura = 70;
        StringBuilder sb = new StringBuilder();

        sb.append("╔").append("═".repeat(largura - 2)).append("╗\n");
        sb.append("║").append(centralizarTexto("🌿 VERDE FOREST", largura - 2)).append("║\n");
        sb.append("║").append(centralizarTexto("Sistema Gerador de Documentos", largura - 2)).append("║\n");
        sb.append("║").append(centralizarTexto("Desenvolvido por Paulo Gabriel", largura - 2)).append("║\n");
        sb.append("╠").append("═".repeat(largura - 2)).append("╣\n");

        for (String linha : conteudo.split("\n")) {
            if (linha.isBlank()) continue;

            if (linha.startsWith("===") || linha.startsWith("---")) {
                sb.append("├").append("─".repeat(largura - 2)).append("┤\n");
            } else if (linha.contains(":")) {
                String[] partes = linha.split(":", 2);
                String chave = partes[0].trim();
                String valor = partes.length > 1 ? partes[1].trim() : "";

                String linha_formatada = String.format("  %-30s : %s", chave, valor);
                if (linha_formatada.length() > largura - 4) {
                    linha_formatada = linha_formatada.substring(0, largura - 4) + "...";
                }
                sb.append("║ ").append(String.format("%-68s", linha_formatada)).append("║\n");
            } else {
                String linha_formatada = "  " + linha;
                if (linha_formatada.length() > largura - 4) {
                    linha_formatada = linha_formatada.substring(0, largura - 4) + "...";
                }
                sb.append("║ ").append(String.format("%-68s", linha_formatada)).append("║\n");
            }
        }

        sb.append("╠").append("═".repeat(largura - 2)).append("╣\n");
        sb.append("║").append(centralizarTexto("Gerado por Verde Forest — Natureza em Texto", largura - 2)).append("║\n");
        sb.append("╚").append("═".repeat(largura - 2)).append("╝\n");

        resultado = sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String centralizarTexto(String texto, int largura) {
        int espacosAntes = (largura - texto.length()) / 2;
        int espacosDepois = largura - texto.length() - espacosAntes;
        return " ".repeat(Math.max(0, espacosAntes)) + texto +
                " ".repeat(Math.max(0, espacosDepois));
    }

    @Override public byte[]  getBytes()       { return resultado; }
    @Override public String  getContentType() { return "text/plain; charset=UTF-8"; }
    @Override public String  getExtensao()    { return "txt"; }
}