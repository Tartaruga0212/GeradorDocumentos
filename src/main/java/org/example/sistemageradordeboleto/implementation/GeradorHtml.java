package org.example.sistemageradordeboleto.implementation;

import java.nio.charset.StandardCharsets;

public class GeradorHtml implements GeradorBridge {

    private byte[] resultado;

    @Override
    public void escrever(String conteudo) {

        StringBuilder rows = new StringBuilder();

        for (String linha : conteudo.split("\n")) {

            if (linha.isBlank()) continue;

            if (linha.startsWith("===") || linha.startsWith("---")) continue;

            int idx = linha.indexOf(':');

            if (idx > 0) {

                String chave = linha.substring(0, idx).trim();
                String valor = linha.substring(idx + 1).trim();

                rows.append("""
                        <tr>
                            <td class='label'>%s</td>
                            <td class='valor'>%s</td>
                        </tr>
                        """.formatted(chave, valor));

            } else {

                rows.append("""
                        <tr>
                            <td colspan='2' class='secao'>%s</td>
                        </tr>
                        """.formatted(linha));
            }
        }

        String html = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Documento Verde Forest</title>

                    <style>

                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }

                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            background: linear-gradient(
                                180deg,
                                #f4fbf4 0%,
                                #e7f5e6 45%,
                                #e8f7e4 100%
                            );
                            padding: 20px;
                            color: #223826;
                        }

                        .container {
                            max-width: 900px;
                            margin: 0 auto;
                            background: #ffffff;
                            border: 1px solid rgba(72, 104, 72, 0.16);
                            border-radius: 24px;
                            overflow: hidden;
                            box-shadow: 0 18px 40px rgba(83, 114, 82, 0.08);
                        }

                        .header {
                            background: linear-gradient(
                                135deg,
                                #2f6a39,
                                #4b7e4a
                            );

                            color: #ffffff;
                            padding: 24px;
                            border-bottom: 2px solid #2f6a39;
                            text-align: center;
                        }

                        .header h1 {
                            font-size: 28px;
                            font-weight: 800;
                            margin: 0;
                            letter-spacing: 0.5px;
                        }

                        .header p {
                            font-size: 14px;
                            opacity: 0.9;
                            margin-top: 8px;
                            font-weight: 600;
                        }

                        .header .icon {
                            font-size: 32px;
                            margin-bottom: 10px;
                        }

                        .content {
                            padding: 30px;
                        }

                        table {
                            width: 100%;
                            border-collapse: collapse;
                        }

                        tr {
                            transition: background-color 0.2s ease;
                        }

                        tr:nth-child(even) td {
                            background: #f7fcf7;
                        }

                        tr:nth-child(odd) td {
                            background: #eef7ed;
                        }

                        td {
                            padding: 12px 16px;
                            border-bottom: 1px solid #9cc79f;
                        }

                        td.label {
                            font-weight: 700;
                            color: #2f6a39;
                            width: 30%;
                            text-transform: uppercase;
                            font-size: 12px;
                            letter-spacing: 0.5px;
                        }

                        td.valor {
                            color: #223826;
                            word-break: break-word;
                        }

                        td.secao {
                            background: #4b7e4a !important;
                            color: #ffffff;
                            font-weight: 700;
                            font-size: 14px;
                            text-transform: uppercase;
                            letter-spacing: 0.5px;
                            padding: 16px;
                        }

                        .footer {
                            background: #2f6a39;
                            color: #d9edcc;
                            padding: 16px 24px;
                            text-align: center;
                            font-size: 11px;
                            border-top: 2px solid #4b7e4a;
                        }

                        @media print {

                            body {
                                background: white;
                                padding: 0;
                            }

                            .container {
                                box-shadow: none;
                                border-radius: 0;
                            }
                        }

                    </style>

                </head>

                <body>

                    <div class="container">

                        <div class="header">

                            <div class="icon">🌿</div>

                            <h1>Verde Forest</h1>

                            <p>
                                Sistema Gerador de Documentos —
                                Desenvolvido por Paulo Gabriel
                            </p>

                        </div>

                        <div class="content">

                            <table>

                                {{ROWS}}

                            </table>

                        </div>

                        <div class="footer">
                            Gerado por Verde Forest —
                            Inspirado na Natureza,
                            Construído com Precisão
                        </div>

                    </div>

                </body>

                </html>
                """;

        html = html.replace("{{ROWS}}", rows.toString());

        resultado = html.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] getBytes() {
        return resultado;
    }

    @Override
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @Override
    public String getExtensao() {
        return "html";
    }
}

