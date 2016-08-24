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
public class CondicaoPagamento {
    private int id;
    private String nomeCondicaoPagamento;
    private int quantidadeParcelas=0;
    private int intervaloDias=0;
    private int entrada =0;

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCondicaoPagamento() {
        return nomeCondicaoPagamento;
    }

    public void setNomeCondicaoPagamento(String nomeCondicaoPagamento) {
        this.nomeCondicaoPagamento = nomeCondicaoPagamento;
    }
    
    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public int getIntervaloDias() {
        return intervaloDias;
    }

    public void setIntervaloDias(int intervaloDias) {
        this.intervaloDias = intervaloDias;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.nomeCondicaoPagamento);
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
        final CondicaoPagamento other = (CondicaoPagamento) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nomeCondicaoPagamento, other.nomeCondicaoPagamento)) {
            return false;
        }
        return true;
    }

    
    
    
}
