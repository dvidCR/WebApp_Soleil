package com.soleil.api.reports;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.soleil.api.controller.GastoController;
import com.soleil.api.controller.ServicioController;
import com.soleil.api.dto.GastoDTO;
import com.soleil.api.dto.ServicioDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReport {

    private String filePath;
    private String fileName;

    private boolean filtrarUltimoMes = false;
    private Integer mesFiltrado = null;
    private Integer anioFiltrado = null;

    private List<Double> prices = new ArrayList<>();

    private ServicioController serviceController;
    private GastoController gastoController;

    public ExcelReport(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.filtrarUltimoMes = true;
    }
    
    public ExcelReport(String filePath, String fileName, int mes, int anio) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.mesFiltrado = mes;
        this.anioFiltrado = anio;
    }

    public void start() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet earningsSheet = workbook.createSheet("Ingresos");
            Sheet expensesSheet = workbook.createSheet("Gastos");

            generateEarnings(earningsSheet);
            generateExpenses(expensesSheet);

            try (FileOutputStream fileOut = new FileOutputStream(filePath + "/" + fileName)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateEarnings(Sheet sheet) {
        String[] headers = {"ID", "Fecha", "Fisioterapeuta", "Paciente", "Tratamiento", "Pago", "Tarifa", "Concepto", "Cantidad"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        List<ServicioDTO> services = serviceController.listarServicio();
        int rowIdx = 1;

        for (ServicioDTO service : services) {
            LocalDate fecha = service.getFecha_cita().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!debeIncluirFecha(fecha)) continue;

            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(service.getId_servicio());
            row.createCell(1).setCellValue(fecha.toString());
            row.createCell(2).setCellValue(service.getDni_empleado());
            row.createCell(3).setCellValue(service.getDni_paciente());
            row.createCell(4).setCellValue(service.getId_tratamiento());
            row.createCell(5).setCellValue(service.getModo_pago());
            row.createCell(6).setCellValue(service.getTarifa());
            row.createCell(7).setCellValue(service.getConcepto());
            row.createCell(8).setCellValue(service.getNum_sesiones());

            prices.add(service.getTarifa());
        }

        Row totalRow = sheet.createRow(rowIdx);
        totalRow.createCell(0).setCellValue("Ingresos:");
        totalRow.createCell(1).setCellValue(calculateOperation());
        prices.clear();
    }

    private void generateExpenses(Sheet sheet) {
        String[] headers = {"ID", "Cantidad", "Motivo", "Proveedor", "Fecha"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        List<GastoDTO> expenses = gastoController.listarGasto();
        int rowIdx = 1;

        for (GastoDTO expense : expenses) {
        	LocalDate fecha = expense.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!debeIncluirFecha(fecha)) continue;

            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(expense.getId_gasto());
            row.createCell(1).setCellValue(expense.getCantidad());
            row.createCell(2).setCellValue(expense.getMotivo());
            row.createCell(3).setCellValue(expense.getProveedor());
            row.createCell(4).setCellValue(fecha.toString());

            prices.add(expense.getCantidad());
        }

        Row totalRow = sheet.createRow(rowIdx);
        totalRow.createCell(0).setCellValue("Gastos:");
        totalRow.createCell(1).setCellValue(calculateOperation());
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