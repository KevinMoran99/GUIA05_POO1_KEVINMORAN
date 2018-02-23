/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.model;

import java.util.ArrayList;

/**
 *
 * @author kevin
 */
public class Team {
    private int code;
    private String name, description;
    private ArrayList<Player> playerList;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
        playerList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Añade un jugador al equipo, reemplaza al setter de la lista de jugadores
     * @param player 
     */
    public void addPlayer(Player player) {
        this.playerList.add(player);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    

    @Override
    public String toString() {
        return name;
}
}
