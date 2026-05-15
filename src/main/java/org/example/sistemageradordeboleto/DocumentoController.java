package org.example.sistemageradordeboleto;

import org.example.sistemageradordeboleto.abstraction.Documento;
import org.example.sistemageradordeboleto.concrete.*;
import org.example.sistemageradordeboleto.implementation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class DocumentoController {

    @PostMapping("/gerar")
    public ResponseEntity<byte[]> gerarDocumento(@RequestBody DocumentoRequest req) {

        GeradorBridge gerador = switch (req.formato().toUpperCase()) {
            case "PDF"  -> new GeradorPdf();
            case "HTML" -> new GeradorHtml();
            case "JSON" -> new GeradorJson();
            default     -> new GeradorTxt();
        };

        Documento documento = switch (req.tipoDocumento().toUpperCase()) {
            case "RELATORIO"   -> new Relatorio(gerador);
            case "NOTA_FISCAL" -> new Nota(gerador);
            case "PROPOSTA"    -> new Proposta(gerador);
            case "CONTRATO"    -> new Contrato(gerador);
            default -> throw new IllegalArgumentException("Tipo desconhecido: " + req.tipoDocumento());
        };

        Object entrada = switch (req.tipoDocumento().toUpperCase()) {
            case "RELATORIO"   -> req.relatorioData();
            case "NOTA_FISCAL" -> req.notaFiscalData();
            case "PROPOSTA"    -> req.propostaData();
            case "CONTRATO"    -> req.contratoData();
            default -> throw new IllegalArgumentException("Dados ausentes.");
        };

        documento.gerar(entrada);

        String arquivo = req.tipoDocumento().toLowerCase() + "." + documento.getExtensao();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + arquivo)
                .contentType(MediaType.parseMediaType(documento.getContentType()))
                .body(documento.getBytes());
    }
}