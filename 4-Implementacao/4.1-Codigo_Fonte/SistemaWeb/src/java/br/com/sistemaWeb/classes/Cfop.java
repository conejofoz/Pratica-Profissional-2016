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
 * @author geral
 */
public class Cfop implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String nomeCfop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCfop() {
        return nomeCfop;
    }

    public void setNomeCfop(String nomeCfop) {
        this.nomeCfop = nomeCfop;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.nomeCfop);
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
        final Cfop other = (Cfop) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nomeCfop, other.nomeCfop)) {
            return false;
        }
        return true;
    }
    
}
