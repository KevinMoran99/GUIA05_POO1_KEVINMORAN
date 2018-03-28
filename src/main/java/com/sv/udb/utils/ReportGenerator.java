/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.utils;

import com.sv.udb.model.Team;
import com.sv.udb.resources.ConnectionDB;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author kevin
 */
public class ReportGenerator {
    
    public static final int VISOR = 0;
    public static final int EXCEL = 1;
    public static final int WORD = 2;
    
    public static String generateReport(int type, String reportName, HashMap map) {
        String resp = "";
        
        //Conexion
        Connection conn = new ConnectionDB().getConn();
        
        try {
            //Rutas de archivos
            String jrxmlFileName = new File("src/main/java/com/sv/udb/reports/" + reportName + ".jrxml").getAbsolutePath();
            String jasperFileName = new File("src/main/java/com/sv/udb/reports/" + reportName + ".jasper").getAbsolutePath();
            
            //Compilando jasperreport
            JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);
            
            
            //Para generar al reporte directamente con una conexión y query
            JasperPrint print = (JasperPrint)JasperFillManager.fillReport(jasperFileName, map, conn);
            
            switch (type) {
                case VISOR:
                    //Nombre de archivo
                    String pdfFileName = new File("reports/" + reportName + ".pdf").getAbsolutePath();
                    
                    //Exportando a pdf
                    JasperExportManager.exportReportToPdfFile(print, pdfFileName);
                    
                    //Abriendo en visor
                    JasperViewer.viewReport(print, false);
                    
                    resp = "Reporte generado. El reporte se abrirá en un momento.";
                    break;
                    
                case EXCEL:
                    //Nombre del archivo
                    String xlsFileName = new File("reports/" + reportName + ".xls").getAbsolutePath();
                    
                    //Exportando a xls
                    JRXlsExporter xlsExporter = new JRXlsExporter();
            
                    xlsExporter.setExporterInput(new SimpleExporterInput(print));
                    xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsFileName));
                    xlsExporter.exportReport();
                    
                    //mostrando
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().open(new File(xlsFileName));
                            resp = "Reporte generado. El reporte se abrirá en un momento.";
                        } catch (IOException ex) {
                            System.out.println("No abrió xd " + ex);
                            resp = "No se abrió xd";
                        }
                    }
                    else {
                        resp = "Reporte generado en la siguiente ubicación: " + xlsFileName;
                    }
                    break;
                    
                case WORD:
                    //Nombre del archivo
                    String docFileName = new File("reports/" + reportName + ".docx").getAbsolutePath();

                    //Exportando a docx
                    JRDocxExporter docExporter = new JRDocxExporter();

                    docExporter.setExporterInput(new SimpleExporterInput(print));
                    docExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(docFileName));
                    docExporter.exportReport();

                    //mostrando
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().open(new File(docFileName));
                            resp = "Reporte generado. El reporte se abrirá en un momento.";
                        } catch (IOException ex) {
                            System.out.println("No abrió xd " + ex);
                            resp = "No se abrió xd";
                        }
                    }
                    else {
                        resp = "Reporte generado en la siguiente ubicación: " + docFileName;
                    }
                    break;
                    
            }
            
            
        } 
        catch (JRRuntimeException e) {
            System.out.println(e);
            resp = "El reporte no pudo generarse porque otro programa había abierto el archivo.";
        }
        catch (Exception e) {
            System.out.println(e);
            resp = "Ocurrió un error.";
        }
        finally {
            try {
                if (conn != null)
                    if (!conn.isClosed())
                        conn.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar la conexión al guardar jugador: " + e.getMessage());
            }
        }
        
        return resp;
    }
    
    
    public static String generateTeamReport (String param, int type) {
        HashMap map = new HashMap();
        //seteando los parametros que recibe el reporte
        map.put("param",param);
        
        return generateReport(type, "TeamReport", map);
    }
    
    public static String generatePlayerReport (String name, Object team, int type) {
        HashMap map = new HashMap();
        //seteando los parametros que recibe el reporte
        map.put("name",name);
        
        if (team instanceof Team) {
            map.put("team", ((Team)team).getCode());
        }
        else {
            //Si el objeto no era del tipo Team, se omitirá ese filtro
            map.put("team", "jugadores.codi_equi");
        }
        
        return generateReport(type, "PlayerReport", map);
    }
}
