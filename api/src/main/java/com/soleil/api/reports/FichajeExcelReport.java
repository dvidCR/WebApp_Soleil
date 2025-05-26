package com.soleil.api.reports;

import com.soleil.api.controller.FichajeController;
import com.soleil.api.dto.FichajeDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class FichajeExcelReport {

    private boolean filtrarUltimoMes = false;
    private Integer mesFiltrado = null;
    private Integer anioFiltrado = null;

    private final OutputStream outputStream;
    private final FichajeController fichajeController;

    public FichajeExcelReport(OutputStream outputStream, FichajeController fichajeController) {
        this.outputStream = outputStream;
        this.fichajeController = fichajeController;
    }

    public void setFiltroUltimoMes(boolean filtrarUltimoMes) {
        this.filtrarUltimoMes = filtrarUltimoMes;
        this.mesFiltrado = null;
        this.anioFiltrado = null;
    }

    public void setFiltroMesYAnio(int mes, int anio) {
        this.filtrarUltimoMes = false;
        this.mesFiltrado = mes;
        this.anioFiltrado = anio;
    }

    public void start() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Fichajes");
            String[] headers = {"ID", "Empleado", "Fecha", "Hora Entrada", "Hora Salida"};

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            List<FichajeDTO> fichajes = fichajeController.listarFichaje();
            int rowIdx = 1;

            for (FichajeDTO f : fichajes) {
                if (!debeIncluirFecha(f.getFecha())) continue;

                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(f.getId_fichaje());
                row.createCell(1).setCellValue(f.getDni_empleado());
                row.createCell(2).setCellValue(f.getFecha().toString());
                row.createCell(3).setCellValue(f.getHora_entrada() != null ? f.getHora_entrada().toString() : null);
                row.createCell(4).setCellValue(f.getHora_salida() != null ? f.getHora_salida().toString() : null);
            }

            workbook.write(this.outputStream);
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
}