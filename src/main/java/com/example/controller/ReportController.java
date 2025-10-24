package com.example.controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;

@Controller
public class ReportController {

    @GetMapping("/ProductosReport")
    public void ProductsReport(HttpServletResponse response) {
        try {
            // 🟢 Si quieres usar otra base de datos distinta:
            String url = "jdbc:mysql://localhost:3306/dbacademico"; // <--- tu otra BD
            String user = "root";
            String password = "123456";

            // Conexión manual a la BD del reporte
            Connection conn = DriverManager.getConnection(url, user, password);

            // Cargar el archivo .jasper
            InputStream reportStream = new ClassPathResource("reportes/Cherry.jasper").getInputStream();

            // Parámetros (si tu reporte los usa)
            Map<String, Object> params = new HashMap<>();
            params.put("titulo", "Reporte de Productos");

            // Llenar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, conn);

            // Configurar salida
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=ReporteProductos.pdf");

            // Exportar a PDF
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage());
        }
    }
}
