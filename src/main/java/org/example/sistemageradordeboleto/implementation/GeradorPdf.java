package org.example.sistemageradordeboleto.implementation;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

public class GeradorPdf implements GeradorBridge {

    private static final Color VERDE_PRIMARIO      = new Color(34, 85, 45);
    private static final Color VERDE_SECUNDARIO    = new Color(62, 120, 74);
    private static final Color VERDE_SUAVE         = new Color(241, 248, 241);
    private static final Color VERDE_SUAVE_2       = new Color(232, 243, 232);
    private static final Color BORDA               = new Color(188, 214, 188);

    private static final Color TEXTO_PRINCIPAL     = new Color(28, 45, 32);
    private static final Color TEXTO_SECUNDARIO    = new Color(90, 110, 90);

    private byte[] resultado;

    @Override
    public void escrever(String conteudo) {

        Document doc = new Document(PageSize.A4, 42, 42, 45, 45);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter writer = PdfWriter.getInstance(doc, out);

            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);

            doc.open();

            Font tituloFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    24,
                    Color.WHITE
            );

            Font subtituloFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    10,
                    new Color(230, 240, 230)
            );

            Font secaoFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    11,
                    Color.WHITE
            );

            Font labelFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    9,
                    VERDE_PRIMARIO
            );

            Font valorFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    9,
                    TEXTO_PRINCIPAL
            );

            Font footerFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    8,
                    TEXTO_SECUNDARIO
            );

            PdfPTable header = new PdfPTable(1);
            header.setWidthPercentage(100);

            PdfPCell top = new PdfPCell();
            top.setBorder(Rectangle.NO_BORDER);
            top.setBackgroundColor(VERDE_PRIMARIO);
            top.setPaddingTop(20f);
            top.setPaddingBottom(22f);
            top.setPaddingLeft(24f);

            Paragraph logo = new Paragraph("VERDE FOREST", tituloFont);
            logo.setSpacingAfter(6f);

            Paragraph sub = new Paragraph(
                    "Sistema Inteligente de Geração de Documentos",
                    subtituloFont
            );

            top.addElement(logo);
            top.addElement(sub);

            header.addCell(top);

            doc.add(header);

            doc.add(new Paragraph(" "));

            PdfPTable card = new PdfPTable(1);
            card.setWidthPercentage(100);

            PdfPCell cardContainer = new PdfPCell();
            cardContainer.setBorderColor(BORDA);
            cardContainer.setBorderWidth(1f);
            cardContainer.setPadding(0f);
            cardContainer.setBackgroundColor(Color.WHITE);


            PdfPTable table = new PdfPTable(new float[]{2f, 4f});
            table.setWidthPercentage(100);


            PdfPCell h1 = new PdfPCell(new Phrase("CAMPO", secaoFont));
            h1.setBackgroundColor(VERDE_SECUNDARIO);
            h1.setBorder(Rectangle.NO_BORDER);
            h1.setPadding(12f);

            PdfPCell h2 = new PdfPCell(new Phrase("INFORMAÇÃO", secaoFont));
            h2.setBackgroundColor(VERDE_SECUNDARIO);
            h2.setBorder(Rectangle.NO_BORDER);
            h2.setPadding(12f);

            table.addCell(h1);
            table.addCell(h2);


            boolean zebra = false;

            for (String linha : conteudo.split("\n")) {

                if (linha.isBlank()) continue;

                if (linha.startsWith("===") || linha.startsWith("---")) {
                    continue;
                }

                int idx = linha.indexOf(':');


                if (idx <= 0) {

                    PdfPCell secao = new PdfPCell(
                            new Phrase(linha.toUpperCase(), secaoFont)
                    );

                    secao.setColspan(2);
                    secao.setPadding(12f);
                    secao.setBorder(Rectangle.NO_BORDER);
                    secao.setBackgroundColor(VERDE_PRIMARIO);

                    table.addCell(secao);

                    zebra = false;

                    continue;
                }

                String chave = linha.substring(0, idx).trim();
                String valor = linha.substring(idx + 1).trim();

                Color bg = zebra ? VERDE_SUAVE : VERDE_SUAVE_2;


                PdfPCell c1 = new PdfPCell(
                        new Phrase(chave.toUpperCase(), labelFont)
                );

                c1.setPaddingTop(11f);
                c1.setPaddingBottom(11f);
                c1.setPaddingLeft(12f);

                c1.setBackgroundColor(bg);

                c1.setBorderColor(BORDA);
                c1.setBorderWidthBottom(0.6f);
                c1.setBorderWidthTop(0f);
                c1.setBorderWidthLeft(0f);
                c1.setBorderWidthRight(0f);


                PdfPCell c2 = new PdfPCell(
                        new Phrase(valor, valorFont)
                );

                c2.setPaddingTop(11f);
                c2.setPaddingBottom(11f);
                c2.setPaddingLeft(12f);

                c2.setBackgroundColor(bg);

                c2.setBorderColor(BORDA);
                c2.setBorderWidthBottom(0.6f);
                c2.setBorderWidthTop(0f);
                c2.setBorderWidthLeft(0f);
                c2.setBorderWidthRight(0f);

                table.addCell(c1);
                table.addCell(c2);

                zebra = !zebra;
            }

            cardContainer.addElement(table);

            card.addCell(cardContainer);

            doc.add(card);

            doc.add(new Paragraph(" "));

            LineSeparator ls = new LineSeparator();
            ls.setLineColor(BORDA);
            doc.add(new Chunk(ls));

            doc.add(new Paragraph(" "));

            Paragraph footer = new Paragraph(
                    "Gerado automaticamente por Verde Forest • Sustentável • Seguro • Inteligente",
                    footerFont
            );

            footer.setAlignment(Element.ALIGN_CENTER);

            doc.add(footer);

            doc.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        resultado = out.toByteArray();
    }

    @Override
    public byte[] getBytes() {
        return resultado;
    }

    @Override
    public String getContentType() {
        return "application/pdf";
    }

    @Override
    public String getExtensao() {
        return "pdf";
    }
}