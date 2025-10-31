package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;

@Controller
public class ReportCPController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/cajero/ReporteCP")
    public void ReporteCP(
            HttpServletResponse response) {
        try {
            // ✅ Cargar el archivo .jasper desde resources/reportes
            InputStream reportStream = new ClassPathResource("reportes/ComprobanteDePago.jasper").getInputStream();

            // ✅ Parámetros para Jasper
            Map<String, Object> params = new HashMap<>();
            params.put("REPORT_DIR", new ClassPathResource("/reportes/").getFile().getAbsolutePath() + "/");

         

            // ✅ Llenar el reporte con la conexión del pool de Spring
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, dataSource.getConnection());

            // ✅ Configurar salida PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=ReporteComprobanteDePago.pdf");

            // ✅ Exportar el reporte
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage());
        }
    }
    
}
