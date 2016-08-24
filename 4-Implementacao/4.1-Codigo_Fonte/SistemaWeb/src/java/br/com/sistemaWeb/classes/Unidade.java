/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.classes;

import java.util.Objects;

/**
 *
 * @author geral
 */
public class Unidade {
   
    private String siglaUnidade;
    private String nomeUnidade;

    

    public String getSiglaUnidade() {
        return siglaUnidade;
    }

    public void setSiglaUnidade(String siglaUnidade) {
        this.siglaUnidade = siglaUnidade;
    }

   

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.siglaUnidade);
        hash = 73 * hash + Objects.hashCode(this.nomeUnidade);
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
        final Unidade other = (Unidade) obj;
        if (!Objects.equals(this.siglaUnidade, other.siglaUnidade)) {
            return false;
        }
        if (!Objects.equals(this.nomeUnidade, other.nomeUnidade)) {
            return false;
        }
        return true;
    }

  

   
    
    
    
    
}
