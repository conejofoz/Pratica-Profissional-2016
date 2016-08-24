/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Cfop;
import br.com.sistemaWeb.classes.ItensCompra;
import br.com.sistemaWeb.classes.Produto;
import br.com.sistemaWeb.classes.Compra;
import br.com.sistemaWeb.classes.CondicaoPagamento;
import br.com.sistemaWeb.classes.Cst;
import br.com.sistemaWeb.classes.Fornecedor;
import br.com.sistemaWeb.classes.Ncm;
import br.com.sistemaWeb.classes.Parcelas;
import br.com.sistemaWeb.classes.Transportadora;
import br.com.sistemaWeb.dao.ProdutoDao;
import br.com.sistemaWeb.dao.CompraDao;
import br.com.sistemaWeb.dao.CondicaoPagamentoDao;
import br.com.sistemaWeb.dao.FornecedorDao;
import br.com.sistemaWeb.dao.ParcelaDao;
import br.com.sistemaWeb.dao.TransportadoraDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class CompraBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Compra compra = new Compra();

    private List<Compra> listaCompra;
    private CompraDao eDao;
    private double quantidade;
    private double precoUnitario;
    private double precoTotal;
    private double desconto;
    private boolean liberaBotaoInserirProduto = false;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Compra compraSelecionado;
    private ItensCompra itensCompraSelecionada;
    private Produto produto;
    private List<ItensCompra> listaItens;// = new ArrayList();
    private List<Parcelas> listaParcelas;

    public CompraBean() {
        novaCompra();
    }

    public void novaCompra() {
        this.quantidade = 1;
        this.produto = new Produto();
        this.listaItens = new ArrayList();
        this.compra.setEmissao(new Date());
        this.compra.setDataEnvio(new Date());
        this.listaParcelas = new ArrayList();
    }

    public List<ItensCompra> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<ItensCompra> listaItens) {
        this.listaItens = listaItens;
    }

    public List<Parcelas> getListaParcelas() {
        return listaParcelas;
    }

    public void setListaParcelas(List<Parcelas> listaParcelas) {
        this.listaParcelas = listaParcelas;
    }

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();
    public boolean isLiberaBotaoInserirProduto() {
        return liberaBotaoInserirProduto;
    }

    public void setLiberaBotaoInserirProduto(boolean liberaBotaoInserirProduto) {
        this.liberaBotaoInserirProduto = liberaBotaoInserirProduto;
    }

    public ItensCompra getItensCompraSelecionada() {
        return itensCompraSelecionada;
    }

    public void setItensCompraSelecionada(ItensCompra itensCompraSelecionada) {
        this.itensCompraSelecionada = itensCompraSelecionada;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public List<ItensCompra> getListaDetalhe() {
        return listaItens;
    }

    public void setListaDetalhe(List<ItensCompra> listaItens) {
        this.listaItens = listaItens;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Compra getCompraSelecionado() {
        return compraSelecionado;
    }

    public void setCompraSelecionado(Compra compraSelecionado) {
        this.compraSelecionado = compraSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public List<Compra> getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(List<Compra> listaCompra) {
        this.listaCompra = listaCompra;
    }

    public CompraDao geteDao() {
        return eDao;
    }

    public void seteDao(CompraDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new CompraDao();
        try {
            eDao.apagar(compraSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public void jaExisteCompra() {
        eDao = new CompraDao();
        Compra compraAux = null;
        if (compra != null) {
            try {
                compraAux = eDao.buscaPorID(compra);
                if (compraAux != null) {
                    System.out.println("passou no if do jaexistecompra");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso:", "Compra ja existe "));
                } else {
                    System.out.println("passou no else do jaexistecompra");
                }
                System.out.println("passou no try do jaexistecompra");
            } catch (Exception ex) {
                System.out.println("passou no catch do jaexistecompra");
            }
        }

        System.out.println("dados do existe compra");
        System.out.println("====================================================");
        System.out.println("Nota      : " + compra.getNota());
        System.out.println("Serie     : " + compra.getSerie());
        System.out.println("Modelo    : " + compra.getModelo());
        System.out.println("Fornecedor: " + compra.getFornecedor().getId());
        System.out.println("====================================================");
    }

    public boolean salvar() throws Exception {
        //validar numero da nota
        if (this.compra.getNota() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Informe o número da nota"));
            return false;
        }

        //validar fornecedor
        if (this.compra.getFornecedor().getId() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Preencha o fornecedor "));
            return false;
        }

        //validar data de emissão
        if (this.compra.getEmissao().after(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Data de emissão não pode ser posterior a data atual "));
            return false;
        }

        //validar data de envio
        if (this.compra.getDataEnvio().before(this.compra.getEmissao())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Data de envio não pode ser anterior a data de emissão"));
            return false;
        }

        //validar desconto maior que total da nota
        if (this.compra.getTotalDescontoNota() > this.compra.getTotalNota()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Valor do desconto não pode ser maior que o valor total"));
            return false;
        }

        //validar total da nota
        if (this.compra.getTotalNota() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Valor total não pode ser zero"));
            return false;
        }

        boolean retorno = false;
        Compra compraAux;
        if (compra != null) {
            eDao = new CompraDao();

            try {
                try {
                    compraAux = eDao.buscaPorID(compra);
                    if (compraAux == null) {
                        //carregarLog();
                        eDao.salvar(compra, listaItens);
                        compra = new Compra();
                        retorno = true;
                        //eDaoLog.salvar(logEvento);
                        //*      System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        retorno = false;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso:", "Compra ja existe "));
                        System.out.println("dados do existe compra");
                        System.out.println("====================================================");
                        System.out.println("Nota      : " + compra.getNota());
                        System.out.println("Serie     : " + compra.getSerie());
                        System.out.println("Modelo    : " + compra.getModelo());
                        System.out.println("Fornecedor: " + compra.getFornecedor().getId());
                        System.out.println("====================================================");
                    }
                } catch (SQLException ex1) {
                    retorno = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!!", "Erro no sql da compra " + ex1.getMessage()));
                }
                //compra = new Compra();
            } catch (Exception ex2) {
                retorno = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!!!", "Erro ao salvar a compra " + ex2.getMessage()));
            }

        }
        //compra = new Compra();
        return retorno;
    }

    public void salvarAntigo() throws Exception {
        if (compra != null) {
            eDao = new CompraDao();
            try {

                eDao.salvar(compra, listaItens);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            compra = new Compra();
        }
    }

    /*public void atualizar() {
     eDao = new CompraDao();
     try {
     eDao.atualizar(compra);
     System.out.println("passou no atualizar");
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
     } catch (SQLException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
     }

     }
     */
    public void listar() throws Exception {
        CompraDao dao;
        try {
            dao = new CompraDao();
            listaCompra = dao.todosCompras();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listaItens() throws Exception {
        CompraDao dao;
        try {
            dao = new CompraDao();
            listaItens = dao.todosItensCompra(compra);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarParcelas() throws Exception {
        ParcelaDao dao;
        try {
            dao = new ParcelaDao();
            listaParcelas = dao.todosParcelas(compra);
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Compra per) throws Exception {
        CompraDao dao;
        Compra temp;
        try {
            dao = new CompraDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.compra = temp;
                this.accion = "Modificar";
                this.listaItens();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public CondicaoPagamento buscaCondicaoByCodigo(CondicaoPagamento condicaoPagamento) throws Exception {
        CondicaoPagamentoDao dao;
        CondicaoPagamento temp;

        try {
            dao = new CondicaoPagamentoDao();
            temp = dao.buscaPorID(condicaoPagamento);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Condicao encontrado "));
                this.compra.setCondicaoPagamento(temp);
                if (compra.getNota() > 0) {
                    this.listarParcelas();
                    this.calculaTotalParcelas();
                } else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Não foi preenchido um número de nota, as parcelas não serão geradas!"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "CondicaoPagamento não encontrado "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }

    public Transportadora buscaTransportadoraByCodigo(Transportadora transportadora) throws Exception {
        TransportadoraDao dao;
        Transportadora temp;
        try {
            dao = new TransportadoraDao();
            temp = dao.buscaPorID(transportadora);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Transportadora encontrada "));
                this.compra.setTransportadora(temp);
                this.listarParcelas();
                this.calculaTotalParcelas();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Transportadora não encontrada "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }

    public void operar(ActionEvent actionEvent) throws Exception {
        System.out.println("entrou no operar");
        System.out.println("valor do accion" + this.accion);
        RequestContext context = RequestContext.getCurrentInstance();
        boolean fecharDialogo = false;
        switch (accion) {
            case "Registrar":
                try {
                    if (this.salvar()) {
                        this.limpiar();
                        fecharDialogo = true;
                    } else {
                        System.out.println("erro booooooo");
                    };
                    System.out.println(fecharDialogo);
                } catch (Exception e) {
                    System.out.println("passou no erro do operar");

                }

                break;
            case "Modificar":
                //this.atualizar();
                this.limpiar();
                break;
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
        this.compra.setNota(0);
        this.compra.setTotalProdutos(0);
        this.precoTotal = 0.00;

    }

    public void obterCompra(Compra xCompra) {
        this.accion = "Modificar";
        this.compra = xCompra;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do compra para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um compra no sistema");
     logEvento.setEmissao(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Compra var_compra) {
        RequestContext.getCurrentInstance().closeDialog(var_compra);
    }

    public void abrirDialogoCompra() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        RequestContext.getCurrentInstance().openDialog("consultaCompraes", opcoes, null);
        System.out.println("passou");
    }

    public void buscaProdutoByCodigo() throws Exception {
        System.out.println("entrou no busca pais por sigla do estadobean");
        ProdutoDao xdaoProduto;
        Produto temp;
        //String sigla = this.estado.getPais().getSiglaPais();
        int codigo = this.produto.getId();
        //codigo = this.compra.
        //System.out.println(sigla);
        try {
            xdaoProduto = new ProdutoDao();
            //temp = xdaoProduto.buscaPorCodigo(produto.getId());
            temp = xdaoProduto.buscaPorID(this.produto);
            if (temp != null) {
                this.liberaBotaoInserirProduto = true;
                this.produto = temp;
                this.precoUnitario = this.produto.getPrecoCusto();

                //RequestContext.getCurrentInstance().execute("setaQuantidadeProduto();");
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Produto encontrado....", ""));
            } else {
                this.liberaBotaoInserirProduto = false;
                this.produto.setNomeProduto(null); //para o foco não pular para quantidade quando não encontrar o produto
                //RequestContext.getCurrentInstance().execute("setaCodigoProduto();");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Produto não existe....", ""));
            }
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO NO TRY BUSCA PRODUTO", "Erro: "));
        }

    }

    public void buscaFornecedorByCodigo() throws Exception {
        this.jaExisteCompra();
        System.out.println("entrou no busca fornecedor do produto");
        FornecedorDao xdaoFornecedor;
        Fornecedor temp;
        int codigo = this.compra.getFornecedor().getId();
        //codigo = this.compra.
        //System.out.println(sigla);
        try {
            xdaoFornecedor = new FornecedorDao();
            //temp = xdaoFornecedor.buscaPorCodigo(codigo);
            temp = xdaoFornecedor.buscaPorID(compra.getFornecedor());
            if (temp != null) {
                this.compra.setFornecedor(temp);
                this.jaExisteCompra();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Fornecedor não existe"));
            }
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO NO TRY BUSCA fornecedor", "Erro: "));
        }

    }

    public void inserirNaGrid() {
        if (this.produto.getId() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Sem produto para adicionar na nota!"));
            return;
        }
        ItensCompra detalhe = new ItensCompra();
        detalhe.setQuantidade(quantidade);
        detalhe.setProduto(produto);
        detalhe.setPreco(precoUnitario);
        detalhe.setDesconto(desconto);
        detalhe.setBaseCalculoIcms(precoUnitario * quantidade - desconto);
        detalhe.setBaseCalculoIpi(precoUnitario * quantidade - desconto);
        detalhe.setValorIcms(detalhe.getBaseCalculoIcms() * produto.getIcms() / 100);
        detalhe.setValorIpi(detalhe.getBaseCalculoIpi() * produto.getIpi() / 100);
        this.listaItens.add(detalhe);
        calcularTotalLinha();
        produto = new Produto();//acabei sem querer resolvendo o problema dos codigos repetidos na grid com esse comando
        this.precoUnitario = 0;
        this.quantidade = 1;
        this.desconto = 0;
    }

    public void calcularTotalLinha() {
        compra.setTotalProdutos(0);
        compra.setTotalNota(0);
        compra.setTotalDescontoNota(0);
        compra.setTotalBaseCalculoIcmsNota(0);
        compra.setTotalBaseCalculoIpiNota(0);
        compra.setTotalValorIcmsNota(0);
        compra.setTotalValorIpiNota(0);
        for (ItensCompra det : listaItens) {
            this.compra.setTotalProdutos(this.compra.getTotalProdutos() + det.getSubTotal() - det.getDesconto());
            this.compra.setTotalDescontoNota(this.compra.getTotalDescontoNota() + det.getDesconto());
            this.compra.setTotalBaseCalculoIcmsNota(this.compra.getTotalBaseCalculoIcmsNota() + det.getBaseCalculoIcms());
            this.compra.setTotalBaseCalculoIpiNota(this.compra.getTotalBaseCalculoIpiNota() + det.getBaseCalculoIpi());
            this.compra.setTotalValorIcmsNota(this.compra.getTotalValorIcmsNota() + det.getValorIcms());
            this.compra.setTotalValorIpiNota(this.compra.getTotalValorIpiNota() + det.getValorIpi());
        }
        //this.compra.setTotalNota(this.compra.getTotalProdutos()
            //    + this.compra.getTotalValorIcmsNota()
            //    + this.compra.getTotalValorIpiNota());
        this.compra.setTotalNota(this.compra.getTotalProdutos()
                               + this.compra.getTotalValorIpiNota());
    }

    public void calculaTotalParcelas() {
        compra.setTotalParcelas(0);
        for (Parcelas parce : listaParcelas) {
            this.compra.setTotalParcelas(compra.getTotalParcelas() + parce.getValorParcela());
        }
    }

    public void removerDaGrid(ItensCompra detalhe) {
        this.listaItens.remove(detalhe);
        this.calcularTotalLinha();
    }

    //não usado
    public void setarFornecedorSelecionado(SelectEvent event) {
        if (event != null) {
            Fornecedor fornecedorNovo = (Fornecedor) event.getObject();
            fornecedorNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
            this.compra.setFornecedor(fornecedorNovo);
        } else {
            System.out.println("erro no selecionar fornecedor");
        }
    }

    public void setarTransportadoraSelecionada(SelectEvent event) {
        if (event != null) {
            System.out.println("=========================================");
            System.out.println("EVENTO TRANSPORTADORA OK");
            System.out.println("=========================================");
        } else {
            System.out.println("=========================================");
            System.out.println("EVENTO TRANSPORTADORA NULA");
            System.out.println("=========================================");
        }

        Transportadora transportadoraNova = (Transportadora) event.getObject();
        transportadoraNova.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        if (transportadoraNova != null) {
            compra.setTransportadora(transportadoraNova);
            System.out.println("=========================================");
            System.out.println("TRANSPORTADORA OK");
            System.out.println("NOME TRANSPORTADORA :" + compra.getTransportadora().getNomeTransportadora());
            System.out.println("=========================================");
        } else {
            System.out.println("=========================================");
            System.out.println("TRANSPORTADORA NULA");
            System.out.println("=========================================");
        }
    }

    //não usado
    public void setarProdutoSelecionado(SelectEvent event) {
        Produto produtoNovo = (Produto) event.getObject();
        produtoNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        if (produtoNovo != null) {
            this.produto = produtoNovo;
            this.liberaBotaoInserirProduto = true;
        } else {
            this.liberaBotaoInserirProduto = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Não foi selecionado um produto!"));
        }
    }

    public void setarNcmSelecionado(SelectEvent event) {
        if (event != null) {
            Ncm ncmNovo = (Ncm) event.getObject();
            ncmNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
            this.produto.setNcm(ncmNovo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", this.produto.getNcm().getNomeNcm()));
        } else {
            System.out.println("erro no selecionar ncm");
        }
    }

    public void setarCstSelecionado(SelectEvent event) {
        if (event != null) {
            Cst cstNovo = (Cst) event.getObject();
            cstNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
            this.produto.setCst(cstNovo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", this.produto.getCst().getId()));
        } else {
            System.out.println("erro no selecionar cst");
        }
    }

    public void setarCfopSelecionado(SelectEvent event) {
        if (event != null) {
            Cfop cfopNovo = (Cfop) event.getObject();
            cfopNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
            this.produto.setCfop(cfopNovo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", this.produto.getCfop().getId()));
        } else {
            System.out.println("erro no selecionar cst");
        }
    }

    public void posicionaNoCampoCodigoDoProduto() {
        // RequestContext.getCurrentInstance().execute("posicionaNoCampoCodigoDoProduto();");
    }

    public Date hoje() {
        return new Date();
    }

    public void verificaData(ValueChangeEvent event) {
        System.out.println("===================================");
        System.out.println("data atual " + compra.getEmissao());
        System.out.println("data noval " + event.getNewValue());
        Date dataInformada = (Date) event.getNewValue();
        if (dataInformada.after(new Date())) {
            System.out.println("data maior");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Foi informado uma data maior que a data atual!!!"));
        }
    }

    public void verificaDataEnvio(ValueChangeEvent event) {
        System.out.println("===================================");
        System.out.println("data de envio atual  " + compra.getDataEnvio());
        System.out.println("data de envio  noval " + event.getNewValue());
        Date dataInformada = (Date) event.getNewValue();
        if (dataInformada.after(new Date())) {
            System.out.println("data de envio maior");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Foi informado uma data maior que a data atual!!!"));
        }
        if (dataInformada.before(compra.getEmissao())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Foi informado uma data menor que a data de emissão!!!"));
        }
    }

    public void verificaDataEnvioNormal() {
        compra.setDataEnvio(compra.getDataEnvio());
        System.out.println("===================================");
        System.out.println("data emissao atual-> " + compra.getEmissao());
        System.out.println("data envio   atual-> " + compra.getDataEnvio());
        if (compra.getEmissao().after(compra.getDataEnvio())) {
            System.out.println("data de envio não pode ser menor que data de emissão->");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "data de envio não pode ser menor que data de emissãol!!!"));
        }
    }

    public void naoFazNada() {
        System.out.println("Passou no método não faz nada");
    }

    public void naoFazNadaClique() {
        System.out.println("-------------------------------------------------");
        System.out.println("clicou");
        System.out.println("-------------------------------------------------");
    }

    public void calculaPrecoTotalEdicaoProdutos() {
        this.precoTotal = 0;
        this.precoTotal = this.quantidade * this.precoUnitario;
        System.out.println("preco total" + this.getPrecoTotal());
    }

}
