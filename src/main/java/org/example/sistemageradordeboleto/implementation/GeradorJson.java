package org.example.sistemageradordeboleto.implementation;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class GeradorJson implements GeradorBridge {

    private byte[] resultado;

    @Override
    public void escrever(String conteudo) {
        Map<String, Object> documento = new LinkedHashMap<>();

        Map<String, String> metadata = new LinkedHashMap<>();
        metadata.put("gerador", "Verde Forest");
        metadata.put("desenvolvedor", "Paulo Gabriel");
        metadata.put("sistema", "Sistema Gerador de Documentos");
        metadata.put("versao", "1.0");
        metadata.put("tema", "Verde Natureza");
        metadata.put("data_geracao", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        documento.put("_metadata", metadata);

        Map<String, String> campos = new LinkedHashMap<>();
        for (String linha : conteudo.split("\n")) {
            int idx = linha.indexOf(':');
            if (idx > 0 && !linha.startsWith("=") && !linha.startsWith("-")) {
                String chave = linha.substring(0, idx).trim();
                String valor = linha.substring(idx + 1).trim();
                campos.put(chave, valor);
            }
        }
        documento.put("campos", campos);

        StringBuilder sb = new StringBuilder("{\n");

        int count = 0;
        for (Map.Entry<String, Object> e : documento.entrySet()) {
            sb.append("  \"").append(e.getKey()).append("\": ");

            if (e.getValue() instanceof Map) {
                sb.append("{\n");
                Map<String, String> inner = (Map<String, String>) e.getValue();
                int innerCount = 0;
                for (Map.Entry<String, String> ie : inner.entrySet()) {
                    sb.append("    \"").append(ie.getKey()).append("\": \"")
                            .append(ie.getValue()).append("\"");
                    if (++innerCount < inner.size()) sb.append(",");
                    sb.append("\n");
                }
                sb.append("  }");
            } else {
                sb.append("\"").append(e.getValue()).append("\"");
            }

            if (++count < documento.size()) sb.append(",");
            sb.append("\n");
        }
        sb.append("}");

        resultado = sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override public byte[]  getBytes()       { return resultado; }
    @Override public String  getContentType() { return "application/json; charset=UTF-8"; }
    @Override public String  getExtensao()    { return "json"; }
}