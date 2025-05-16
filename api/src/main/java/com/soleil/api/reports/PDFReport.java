package com.soleil.api.reports;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.soleil.api.controller.GastoController;
import com.soleil.api.controller.ServicioController;
import com.soleil.api.dto.GastoDTO;
import com.soleil.api.dto.ServicioDTO;

public class PDFReport {

    private String filePath;
    private String fileName;

    private boolean filtrarUltimoMes = false;
    private Integer mesFiltrado = null;
    private Integer anioFiltrado = null;

    private List<Double> prices = new ArrayList<>();

    private ServicioController serviceController;
    private GastoController gastoController;

    public PDFReport(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
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
            PdfWriter writer = new PdfWriter(filePath + "/" + fileName);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Image img = new Image(ImageDataFactory.create("src/main/resources/static/img/logo.jpg"));
            document.add(img);
            document.add(new Paragraph(fileName));

            generateEarnings(document);
            generateExpenses(document);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateEarnings(Document document) {
        String[] headerNames = {"ID", "Fecha", "Fisioterapeuta", "Paciente", "Tratamiento", "Pago", "Tarifa", "Concepto", "Cantidad"};
        List<ServicioDTO> services = serviceController.listarServicio();

        Table table = new Table(headerNames.length);
        for (String name : headerNames) {
            table.addHeaderCell(name);
        }

        for (ServicioDTO service : services) {
            LocalDate fecha = service.getFecha_cita().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!debeIncluirFecha(fecha)) continue;

            table.addCell(String.valueOf(service.getId_servicio()));
            table.addCell(fecha.toString());
            table.addCell(service.getDni_empleado());
            table.addCell(service.getDni_paciente());
            table.addCell(String.valueOf(service.getId_tratamiento()));
            table.addCell(service.getModo_pago());
            table.addCell(String.valueOf(service.getTarifa()));
            table.addCell(service.getConcepto());
            table.addCell(String.valueOf(service.getNum_sesiones()));

            prices.add(service.getTarifa());
        }

        document.add(new Paragraph("\nIngresos: " + calculateOperation()));
        document.add(table);
        prices.clear();
    }

    private void generateExpenses(Document document) {
        String[] headerNames = {"ID", "Cantidad", "Motivo", "Proveedor", "Fecha"};
        List<GastoDTO> expenses = gastoController.listarGasto();

        Table table = new Table(headerNames.length);
        for (String name : headerNames) {
            table.addHeaderCell(name);
        }

        for (GastoDTO expense : expenses) {
            LocalDate fecha = expense.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!debeIncluirFecha(fecha)) continue;

            table.addCell(String.valueOf(expense.getId_gasto()));
            table.addCell(String.valueOf(expense.getCantidad()));
            table.addCell(expense.getMotivo());
            table.addCell(expense.getProveedor());
            table.addCell(fecha.toString());

            prices.add(expense.getCantidad());
        }

        document.add(new Paragraph("\nGastos: " + calculateOperation()));
        document.add(table);
        prices.clear();
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

    private Double calculateOperation() {
        double total = 0.0;
        for (Double price : prices) {
            total += price;
        }
        return total;
    }
}