/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.classes;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author geral
 */
public class Compra implements Serializable{
    private static final long serialVersionUID = 1L;
    private int nota;
    private String modelo;
    private String serie;
    private Date emissao;
    private Date dataEnvio;
    private double totalProdutos;
    private double totalNota;
    private double totalDescontoNota;
    private double totalBaseCalculoIcmsNota;
    private double totalBaseCalculoIpiNota;
    private double totalValorIcmsNota;
    private double totalValorIpiNota;
    private double totalValorFreteNota;
    private double totalParcelas;
    private Fornecedor fornecedor = new Fornecedor();
    private Transportadora transportadora = new Transportadora();
    private CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
    private Ncm ncm = new Ncm();
    private Cst cst = new Cst();
    private Cfop cfop = new Cfop();

    
    
    
    public Compra() {
        this.nota = 0;
        this.modelo  = "NF-e";
        this.serie = "1";
    }

    public double getTotalBaseCalculoIcmsNota() {
        return totalBaseCalculoIcmsNota;
    }

    public void setTotalBaseCalculoIcmsNota(double totalBaseCalculoIcmsNota) {
        this.totalBaseCalculoIcmsNota = totalBaseCalculoIcmsNota;
    }

    public double getTotalBaseCalculoIpiNota() {
        return totalBaseCalculoIpiNota;
    }

    public void setTotalBaseCalculoIpiNota(double totalBaseCalculoIpiNota) {
        this.totalBaseCalculoIpiNota = totalBaseCalculoIpiNota;
    }

    public double getTotalValorIcmsNota() {
        return totalValorIcmsNota;
    }

    public void setTotalValorIcmsNota(double totalValorIcmsNota) {
        this.totalValorIcmsNota = totalValorIcmsNota;
    }

    public double getTotalValorIpiNota() {
        return totalValorIpiNota;
    }

    public void setTotalValorIpiNota(double totalValorIpiNota) {
        this.totalValorIpiNota = totalValorIpiNota;
    }

    public double getTotalValorFreteNota() {
        return totalValorFreteNota;
    }

    public void setTotalValorFreteNota(double totalValorFreteNota) {
        this.totalValorFreteNota = totalValorFreteNota;
    }
    
    

    public Ncm getNcm() {
        return ncm;
    }

    public void setNcm(Ncm ncm) {
        this.ncm = ncm;
    }

    public Cst getCst() {
        return cst;
    }

    public void setCst(Cst cst) {
        this.cst = cst;
    }

    public Cfop getCfop() {
        return cfop;
    }

    public void setCfop(Cfop cfop) {
        this.cfop = cfop;
    }
    
    

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
    
    
    

    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public double getTotalParcelas() {
        return totalParcelas;
    }

    public void setTotalParcelas(double totalParcelas) {
        this.totalParcelas = totalParcelas;
    }
    
    
    
    

    public double getTotalDescontoNota() {
        return totalDescontoNota;
    }

    public void setTotalDescontoNota(double totalDescontoNota) {
        this.totalDescontoNota = totalDescontoNota;
    }

    
    
    public double getTotalNota() {
        return totalNota;
    }

    public void setTotalNota(double totalNota) {
        this.totalNota = totalNota;
    }
    
    

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }


    

    public double getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(double total) {
        this.totalProdutos = total;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    
    
    
}
