package org.example.sistemageradordeboleto.abstraction;

import org.example.sistemageradordeboleto.implementation.GeradorBridge;

public abstract class Documento {

    protected GeradorBridge gerador;

    public Documento(GeradorBridge gerador) {
        this.gerador = gerador;
    }
    public abstract void gerar(Object dados);
    public byte[]  getBytes()       { return gerador.getBytes(); }
    public String  getContentType() { return gerador.getContentType(); }
    public String  getExtensao()    { return gerador.getExtensao(); }
}