/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controller;

import com.sv.udb.model.Player;
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
public class PlayerController {
    private final Connection conn;

    public PlayerController() {
        conn = new ConnectionDB().getConn();
    }
    
    public boolean save(Team team, String name, int age, double height, double weight) {
        boolean resp = false;
        try {
            PreparedStatement cmd = conn.prepareStatement("INSERT INTO jugadores VALUES (NULL, ?, ?, ?, ?, ?)");
            cmd.setInt(1, team.getCode());
            cmd.setString(2, name);
            cmd.setInt(3, age);
            cmd.setDouble(4, height);
            cmd.setDouble(5, weight);
            cmd.executeUpdate();
            resp = true;
        }
        catch (Exception e) {
            System.err.println("Error al guardar jugador: " + e.getMessage());
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
    
    public List<Player> getAll () {
        List<Player> resp = new ArrayList<>();
        try {
            PreparedStatement cmd = conn.prepareStatement("SELECT ju.codi_juga, eq.*, ju.nomb_juga, "
                    + "ju.edad_juga, ju.altu_juga, ju.peso_juga FROM jugadores ju INNER JOIN equipos eq "
                    + "ON ju.codi_equi = eq.codi_equi;");
            ResultSet rs = cmd.executeQuery();
            
            while (rs.next()) {
                resp.add(new Player(
                        rs.getInt(1),
                        new Team(rs.getInt(2), rs.getString(3), rs.getString(4)),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8)));
            }
        }
        catch (Exception e) {
            System.err.println("Error al consultar jugadores: " + e.getMessage());
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
    
    public boolean update (int code, Team team, String name, int age, double height, double weight) {
        boolean resp = false;
        try {
            PreparedStatement cmd = conn.prepareStatement("UPDATE jugadores SET codi_equi = ?, "
                    + "nomb_juga = ?, edad_juga = ?, altu_juga = ?, peso_juga = ? WHERE codi_juga = ?");
            cmd.setInt(1, team.getCode());
            cmd.setString(2, name);
            cmd.setInt(3, age);
            cmd.setDouble(4, height);
            cmd.setDouble(5, weight);
            cmd.setInt(6, code);
            cmd.executeUpdate();
            resp = true;
        }
        catch (Exception e) {
            System.err.println("Error al modificar jugador: " + e.getMessage());
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
            PreparedStatement cmd = conn.prepareStatement("DELETE FROM jugadores WHERE codi_juga = ?");
            cmd.setInt(1, code);
            cmd.executeUpdate();
            resp = true;
        }
        catch (Exception e) {
            System.err.println("Error al eliminar jugador: " + e.getMessage());
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
