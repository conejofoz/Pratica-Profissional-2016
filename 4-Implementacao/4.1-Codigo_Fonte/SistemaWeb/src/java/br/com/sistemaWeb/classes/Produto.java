package br.com.sistemaWeb.classes;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author geral
 */
public class Produto {
    private int id;
    private String nomeProduto;
    private String codigoBarras;
    private String codigoDoFornecedor; //referencia do produto
    private double quantidadePorCaixa;
    private double estoqueMinimo;
    private double estoqueMaximo;
    private double estoqueAtual = 0;
    private double pesoNeto;
    private double pesoBruto;
    private String tamanho;
    private double valorCompra;
    private double icms;
    private double iss;
    private double ipi;
    private double precoCusto;
    private double valorIcms;
    private double valorIss;
    private double valorIpi;
    private double precoMedio;
    private double precoVarejo;
    private double precoAtacado;
    private Date dataUltimaCompra;
    private Date dataUltimaVenda;
    private Unidade unidade = new Unidade();
    private Grupo grupo = new Grupo();
    private Fornecedor fornecedor = new Fornecedor();
    private Marca marca = new Marca();
    private Date dataCadastro;
    private Time horaCadastro;
    private Ncm ncm = new Ncm();
    private Cst cst = new Cst();
    private Cfop cfop = new Cfop();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.nomeProduto);
        hash = 13 * hash + Objects.hashCode(this.codigoBarras);
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
        final Produto other = (Produto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nomeProduto, other.nomeProduto)) {
            return false;
        }
        if (!Objects.equals(this.codigoBarras, other.codigoBarras)) {
            return false;
        }
        return true;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

   

    
    
    

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodigoDoFornecedor() {
        return codigoDoFornecedor;
    }

    public void setCodigoDoFornecedor(String codigoDoFornecedor) {
        this.codigoDoFornecedor = codigoDoFornecedor;
    }

    public double getQuantidadePorCaixa() {
        return quantidadePorCaixa;
    }

    public void setQuantidadePorCaixa(double quantidadePorCaixa) {
        this.quantidadePorCaixa = quantidadePorCaixa;
    }

    public double getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(double estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public double getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public void setEstoqueMaximo(double estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
    }

    public double getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public double getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public double getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getIcms() {
        return icms;
    }

    public void setIcms(double icms) {
        this.icms = icms;
    }

    public double getIss() {
        return iss;
    }

    public void setIss(double iss) {
        this.iss = iss;
    }

    public double getIpi() {
        return ipi;
    }

    public void setIpi(double ipi) {
        this.ipi = ipi;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public double getValorIcms() {
        return valorIcms;
    }

    public void setValorIcms(double valorIcms) {
        this.valorIcms = valorIcms;
    }

    public double getValorIss() {
        return valorIss;
    }

    public void setValorIss(double valorIss) {
        this.valorIss = valorIss;
    }

    public double getValorIpi() {
        return valorIpi;
    }

    public void setValorIpi(double valorIpi) {
        this.valorIpi = valorIpi;
    }

    public double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(double precoMedio) {
        this.precoMedio = precoMedio;
    }

    public double getPrecoVarejo() {
        return precoVarejo;
    }

    public void setPrecoVarejo(double precoVarejo) {
        this.precoVarejo = precoVarejo;
    }

    public double getPrecoAtacado() {
        return precoAtacado;
    }

    public void setPrecoAtacado(double precoAtacado) {
        this.precoAtacado = precoAtacado;
    }

    public Date getDataUltimaCompra() {
        return dataUltimaCompra;
    }

    public void setDataUltimaCompra(Date dataUltimaCompra) {
        this.dataUltimaCompra = dataUltimaCompra;
    }

    public Date getDataUltimaVenda() {
        return dataUltimaVenda;
    }

    public void setDataUltimaVenda(Date dataUltimaVenda) {
        this.dataUltimaVenda = dataUltimaVenda;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Time getHoraCadastro() {
        return horaCadastro;
    }

    public void setHoraCadastro(Time horaCadastro) {
        this.horaCadastro = horaCadastro;
    }
    
    
    
    


    
}
