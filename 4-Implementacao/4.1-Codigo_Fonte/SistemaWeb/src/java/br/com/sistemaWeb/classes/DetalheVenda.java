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
public class DetalheVenda {
    private int id;
    private Produto produto = new Produto();
    private double quantidade;
    private double preco;
    private double total;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getTotal() {
        return preco * quantidade;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
}
