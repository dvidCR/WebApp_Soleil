package com.soleil.api.reports;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.BorderRadius;
import com.soleil.api.controller.GastoController;
import com.soleil.api.controller.ServicioController;
import com.soleil.api.dto.GastoDTO;
import com.soleil.api.dto.ServicioDTO;

public class PDFReport {

//    private String filePath;
    private String fileName;

    private boolean filtrarUltimoMes = false;
    private Integer mesFiltrado = null;
    private Integer anioFiltrado = null;

    private List<Double> prices = new ArrayList<>();

    private ServicioController serviceController;
    private GastoController gastoController;
    
    private final OutputStream outputStream;

    public PDFReport(String fileName, OutputStream outputStream, ServicioController servicioController, GastoController gastoController) {
        this.outputStream = outputStream;
        this.fileName = fileName;
        this.serviceController = servicioController;
        this.gastoController = gastoController;
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

            InputStream logoStream = getClass().getResourceAsStream("/static/img/logo.png");
            if (logoStream != null) {
                byte[] logoBytes = logoStream.readAllBytes();
                ImageData imageData = ImageDataFactory.create(logoBytes);
                Image img = new Image(imageData).scale(16, 16).setBorderRadius(new BorderRadius(50));
                document.add(img);
            } else {
                System.err.println("Logo no encontrado en /static/img/logo.png");
            }
            
            document.add(new Paragraph(fileName));

            generateEarnings(document);
            generateExpenses(document);

            document.close();
            this.outputStream.flush();

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
            LocalDate fecha = service.getFecha_cita();

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
            LocalDate fecha = expense.getFecha();

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