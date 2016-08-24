/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.classes;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author conejo
 */
public class Grupo implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String nomeGrupo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.nomeGrupo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grupo other = (Grupo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nomeGrupo, other.nomeGrupo)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
