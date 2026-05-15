package org.example.sistemageradordeboleto.implementation;

public interface GeradorBridge {
    void escrever(String conteudo);

    byte[]  getBytes();
    String  getContentType();
    String  getExtensao();
}