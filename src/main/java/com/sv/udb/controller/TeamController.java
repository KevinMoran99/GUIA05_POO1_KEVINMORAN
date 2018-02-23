/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controller;

import com.sv.udb.resources.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Estudiante
 */
public class TeamController {
    private final Connection conn;

    public TeamController() {
        this.conn = new ConnectionDB().getConn();
    }
    
    public boolean save(String name, String description) {
        boolean resp = false;
        try {
            PreparedStatement cmd = conn.prepareStatement("INSERT INTO equipos VALUES (NULL, ?, ?)");
            cmd.setString(1, name);
            cmd.setString(2, description);
            cmd.executeUpdate();
            resp = true;
        }
        catch (Exception e) {
            System.err.println("Error al guardar equipo: " + e.getMessage());
        }
        finally {
            try {
                if (conn != null)
                    if (!conn.isClosed())
                        conn.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return resp;
    }
}


//Estructura de CRUDs
//        boolean resp = false;
//        try {
//            
//        }
//        catch (Exception e) {
//            System.err.println("Error al guardar equipo: " + e.getMessage());
//        }
//        finally {
//            try {
//                if (conn != null)
//                    if (!conn.isClosed())
//                        conn.close();
//            } catch (Exception e) {
//                System.err.println("Error al cerrar la conexión: " + e.getMessage());
//            }
//        }
//        return resp;
