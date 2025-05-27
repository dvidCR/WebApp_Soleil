package com.soleil.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.ByteArrayOutputStream;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import com.soleil.api.reports.ExcelReport;
import com.soleil.api.reports.FichajeExcelReport;
import com.soleil.api.reports.FichajePDFReport;
import com.soleil.api.reports.PDFReport;

@Controller
@RequestMapping("/reportes")
public class ReporteController {
	
	@Autowired
	private ServicioController servicioController;

	@Autowired
	private GastoController gastoController;
	
	@Autowired
	private FichajeController fichajeController;

	@GetMapping("/generarReporteUltimoMes/pdf")
	public ResponseEntity<byte[]> generarReporteUltimoMesPDF() {
	    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	        PDFReport pdfReport = new PDFReport("reporte_ultimo_mes.pdf", baos, servicioController, gastoController);
	        pdfReport.setFiltroUltimoMes(true);
	        pdfReport.start();

	        byte[] pdfBytes = baos.toByteArray();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDisposition(ContentDisposition.attachment()
	            .filename("reporte_ultimo_mes.pdf").build());

	        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


    @GetMapping("/generarReporteUltimoMes/excel")
    public ResponseEntity<byte[]> generarReporteUltimoMesExcel() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ExcelReport excelReport = new ExcelReport(baos, servicioController, gastoController);
            excelReport.setFiltroUltimoMes(true);
            excelReport.start();

            byte[] excelBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.attachment()
                .filename("reporte_ultimo_mes.xlsx").build());

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/generarReportePorFecha/pdf")
    public ResponseEntity<byte[]> generarReportePorFechaPDF(@RequestParam int mes, @RequestParam int anio) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        	String fileName = "reporte_" + mes + "_" + anio;
        	PDFReport pdfReport = new PDFReport(fileName, baos, servicioController, gastoController);
            pdfReport.setFiltroPorMes(mes, anio);
            pdfReport.start();

            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment()
                .filename("reporte_" + mes + "_" + anio + ".pdf").build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/generarReportePorFecha/excel")
    public ResponseEntity<byte[]> generarReportePorFechaExcel(@RequestParam int mes, @RequestParam int anio) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ExcelReport excelReport = new ExcelReport(baos, servicioController, gastoController);
            excelReport.setFiltroMesYAnio(mes, anio);
            excelReport.start();

            byte[] excelBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.attachment()
                .filename("reporte_" + mes + "_" + anio + ".xlsx").build());

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/generarReporteFichajeUltimoMes/pdf")
    public ResponseEntity<byte[]> generarReporteFichajeUltimoMesPDF() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            String fileName = "fichajes_ultimo_mes.pdf";
            FichajePDFReport report = new FichajePDFReport(fileName, baos, fichajeController);
            report.setFiltroUltimoMes(true);
            report.start();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/generarReporteFichajePorFecha/pdf")
    public ResponseEntity<byte[]> generarReporteFichajePorFechaPDF(@RequestParam int mes, @RequestParam int anio) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        	String fileName = "fichajes_" + mes + "_" + anio + ".pdf";
        	FichajePDFReport pdfReport = new FichajePDFReport(fileName, baos, fichajeController);
            pdfReport.setFiltroPorMes(mes, anio);
            pdfReport.start();

            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment()
                .filename(fileName).build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/generarReporteFichajeUltimoMes/excel")
    public ResponseEntity<byte[]> generarReporteFichajesExcel() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            FichajeExcelReport report = new FichajeExcelReport(baos, fichajeController);
            report.setFiltroUltimoMes(true);
            report.start();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.attachment().filename("fichajes_ultimo_mes.xlsx").build());
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/generarReporteFichajePorFecha/excel")
    public ResponseEntity<byte[]> generarReporteFichajePorFechaExcel(@RequestParam int mes, @RequestParam int anio) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            FichajeExcelReport excelReport = new FichajeExcelReport(baos, fichajeController);
            excelReport.setFiltroMesYAnio(mes, anio);
            excelReport.start();

            byte[] excelBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.attachment()
                .filename("fichajes_" + mes + "_" + anio + ".xlsx").build());

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
