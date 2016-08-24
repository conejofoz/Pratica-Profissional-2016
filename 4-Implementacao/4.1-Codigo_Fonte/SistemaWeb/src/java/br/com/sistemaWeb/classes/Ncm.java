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
public class Ncm implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String nomeNcm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   

    public String getNomeNcm() {
        return nomeNcm;
    }

    public void setNomeNcm(String nomeNcm) {
        this.nomeNcm = nomeNcm;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.nomeNcm);
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
        final Ncm other = (Ncm) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nomeNcm, other.nomeNcm)) {
            return false;
        }
        return true;
    }

    
    
    
}
