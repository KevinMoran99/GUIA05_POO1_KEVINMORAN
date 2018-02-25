/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controller;

import com.sv.udb.model.Team;
import com.sv.udb.resources.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Team> getAll () {
        List<Team> resp = new ArrayList<>();
        try {
            PreparedStatement cmd = conn.prepareStatement("SELECT * FROM equipos");
            ResultSet rs = cmd.executeQuery();
            while (rs.next())
                resp.add(new Team(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
    
    public boolean update (int code, String name, String description) {
        boolean resp = false;
        try {
            PreparedStatement cmd = conn.prepareStatement("UPDATE equipos SET nomb_equi = ?, desc_equi = ? WHERE codi_equi = ?");
            cmd.setString(1, name);
            cmd.setString(2, description);
            cmd.setInt(3, code);
            cmd.executeUpdate();
            resp = true;
        }
        catch (Exception e) {
            System.err.println("Error al modificar equipo: " + e.getMessage());
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
    
    public boolean delete (int code) {
        boolean resp = false;
        try {
            PreparedStatement cmd = conn.prepareStatement("DELETE FROM equipos WHERE codi_equi = ?");
            cmd.setInt(1, code);
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
