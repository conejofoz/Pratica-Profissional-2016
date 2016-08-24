/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.classes;

import java.util.Objects;

/**
 *
 * @author conejo
 */
public class FormaPagamento {
    private int id;
    private String nomeFormaPagamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeFormaPagamento() {
        return nomeFormaPagamento;
    }

    public void setNomeFormaPagamento(String nomeFormaPagamento) {
        this.nomeFormaPagamento = nomeFormaPagamento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.nomeFormaPagamento);
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
        final FormaPagamento other = (FormaPagamento) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nomeFormaPagamento, other.nomeFormaPagamento)) {
            return false;
        }
        return true;
    }
    
    
    
}
