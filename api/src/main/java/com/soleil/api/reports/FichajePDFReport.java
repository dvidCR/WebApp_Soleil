package com.soleil.api.reports;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.soleil.api.controller.FichajeController;
import com.soleil.api.dto.FichajeDTO;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class FichajePDFReport {

    private boolean filtrarUltimoMes = false;
    private Integer mesFiltrado = null;
    private Integer anioFiltrado = null;

    private final OutputStream outputStream;
    private final String fileName;
    private final FichajeController fichajeController;

    public FichajePDFReport(String fileName, OutputStream outputStream, FichajeController fichajeController) {
        this.fileName = fileName;
        this.outputStream = outputStream;
        this.fichajeController = fichajeController;
    }

    public void setFiltroUltimoMes(boolean filtrarUltimoMes) {
        this.filtrarUltimoMes = filtrarUltimoMes;
    }

    public void setFiltroPorMes(int mes, int anio) {
        this.mesFiltrado = mes;
        this.anioFiltrado = anio;
    }

    public void start() {
        try {
            PdfWriter writer = new PdfWriter(this.outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph(fileName));
            document.add(new Paragraph("Fichajes del periodo especificado:"));

            String[] headers = {"ID", "Empleado", "Fecha", "Hora Entrada", "Hora Salida"};
            Table table = new Table(headers.length);

            for (String h : headers) {
                table.addHeaderCell(h);
            }

            List<FichajeDTO> fichajes = fichajeController.listarFichaje();
            for (FichajeDTO f : fichajes) {
                if (!debeIncluirFecha(f.getFecha())) continue;

                table.addCell(safeString(String.valueOf(f.getId_fichaje())));
                table.addCell(safeString(f.getDni_empleado()));
                table.addCell(safeString(f.getFecha().toString()));
                table.addCell(safeString(f.getHora_entrada() != null ? f.getHora_entrada().toString() : null));
                table.addCell(safeString(f.getHora_salida() != null ? f.getHora_salida().toString() : null));
            }

            document.add(table);
            document.close();
            this.outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean debeIncluirFecha(LocalDate fecha) {
        if (filtrarUltimoMes) {
            YearMonth ultimoMes = YearMonth.now().minusMonths(1);
            return YearMonth.from(fecha).equals(ultimoMes);
        } else if (mesFiltrado != null && anioFiltrado != null) {
            YearMonth mesUsuario = YearMonth.of(anioFiltrado, mesFiltrado);
            return YearMonth.from(fecha).equals(mesUsuario);
        }
        return true;
    }
    
    private String safeString(String input) {
        return input != null ? input : "";
    }
    
}