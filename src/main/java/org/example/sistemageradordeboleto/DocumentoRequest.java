package org.example.sistemageradordeboleto;

public record DocumentoRequest(
        String tipoDocumento,
        String formato,
        RelatorioRequest  relatorioData,
        NotaFiscalRequest notaFiscalData,
        PropostaRequest   propostaData,
        ContratoRequest   contratoData
) {}