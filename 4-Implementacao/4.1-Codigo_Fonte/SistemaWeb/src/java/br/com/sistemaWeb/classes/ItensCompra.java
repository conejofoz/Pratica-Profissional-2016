/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.classes;

/**
 *
 * @author geral
 */
public class ItensCompra {
    private Compra compra;
    private Produto produto;
    private double quantidade;
    private double preco;
    private double subTotal;
    private double desconto;
    private double subTotalComDesconto;
    

    public ItensCompra() {
        this.produto = new Produto();
        this.compra = new Compra();
    }
    
    

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
    
    

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    
    
    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getSubTotal() {
        return quantidade * preco;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getSubTotalComDesconto() {
        return ( quantidade * preco) - desconto;
    }

    public void setSubTotalComDesconto(double subTotalComDesconto) {
        this.subTotalComDesconto = subTotalComDesconto;
    }
    
    


    
}
