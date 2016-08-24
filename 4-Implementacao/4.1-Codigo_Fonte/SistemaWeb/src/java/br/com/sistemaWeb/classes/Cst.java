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
public class Cst implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String nomeCst;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCst() {
        return nomeCst;
    }

    public void setNomeCst(String nomeCst) {
        this.nomeCst = nomeCst;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.nomeCst);
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
        final Cst other = (Cst) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nomeCst, other.nomeCst)) {
            return false;
        }
        return true;
    }
    
    
    
}
