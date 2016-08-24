/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.DetalheVenda;
import br.com.sistemaWeb.classes.Produto;
import br.com.sistemaWeb.classes.Venda;
import br.com.sistemaWeb.dao.ProdutoDao;
import br.com.sistemaWeb.dao.VendaDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Venda venda = new Venda();

    private List<Venda> listaVenda;
    private VendaDao eDao;
    private double quantidade;
    private double precoUnitario;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Venda vendaSelecionado;
    private DetalheVenda detalheVendaSelecionado;
    private Produto produto;
    private List<DetalheVenda> listaDetalhe;// = new ArrayList();
  //  private LogEvento logEvento = new LogEvento();

    public VendaBean() {
        novaVenda();
    }
   
    public void novaVenda(){
        this.quantidade = 1;
        this.produto = new Produto();
        this.listaDetalhe = new ArrayList();
        this.venda.setData(new Date());
    }

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();

    public DetalheVenda getDetalheVendaSelecionado() {
        return detalheVendaSelecionado;
    }

    public void setDetalheVendaSelecionado(DetalheVenda detalheVendaSelecionado) {
        this.detalheVendaSelecionado = detalheVendaSelecionado;
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

    
    public List<DetalheVenda> getListaDetalhe() {
        return listaDetalhe;
    }

    public void setListaDetalhe(List<DetalheVenda> listaDetalhe) {
        this.listaDetalhe = listaDetalhe;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVendaSelecionado() {
        return vendaSelecionado;
    }

    public void setVendaSelecionado(Venda vendaSelecionado) {
        this.vendaSelecionado = vendaSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getListaVenda() {
        return listaVenda;
    }

    public void setListaVenda(List<Venda> listaVenda) {
        this.listaVenda = listaVenda;
    }

    public VendaDao geteDao() {
        return eDao;
    }

    public void seteDao(VendaDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new VendaDao();
        try {
            eDao.apagar(vendaSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public void salvar() throws Exception{
        if (venda != null) {
            eDao = new VendaDao();

            try {
               //* try {
                //* if (!eDao.buscaPorNome(venda)) {
                //carregarLog();
                eDao.salvar(venda, listaDetalhe);
                        //eDaoLog.salvar(logEvento);
                //*      System.out.println("passou no salvar");
                //*     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                //*  } else {
                //*   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Venda ja existe "));
                //*}
                //*  } catch (SQLException ex1) {
                //* FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                //*  }
                venda = new Venda();
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            
        }
    }

    public void salvarAntigo() throws Exception {
        if (venda != null) {
            eDao = new VendaDao();
            try {

                eDao.salvar(venda, listaDetalhe);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            venda = new Venda();
        }
    }

    public void atualizar() {
        eDao = new VendaDao();
        try {
            eDao.atualizar(venda);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        VendaDao dao;
        try {
            dao = new VendaDao();
            listaVenda = dao.todosVendas();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Venda per) throws Exception {
        VendaDao dao;
        Venda temp;
        try {
            dao = new VendaDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.venda = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void operar(ActionEvent actionEvent) throws Exception {
        System.out.println("entrou no operar");
        System.out.println("valor do accion" + this.accion);
        RequestContext context = RequestContext.getCurrentInstance();
        boolean fecharDialogo = false;
        switch (accion) {
            case "Registrar":
                try {
                    this.salvar();
                    this.limpiar();
                    fecharDialogo = true;
                    System.out.println(fecharDialogo);
                } catch (Exception e) {
                    System.out.println("passou no erro do operar");

                }

                break;
            case "Modificar":
                this.atualizar();
                this.limpiar();
                break;
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
        this.venda.setId(0);
        this.venda.setTotal(0);

    }

    public void obterVenda(Venda xVenda) {
        this.accion = "Modificar";
        this.venda = xVenda;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do venda para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um venda no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Venda var_venda) {
        RequestContext.getCurrentInstance().closeDialog(var_venda);
    }

    public void abrirDialogoVenda() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        RequestContext.getCurrentInstance().openDialog("consultaVendaes", opcoes, null);
        System.out.println("passou");
    }

    public void buscaProdutoByCodigo() throws Exception{
        System.out.println("entrou no busca pais por sigla do estadobean");
        ProdutoDao xdaoProduto;
        Produto temp;
        //String sigla = this.estado.getPais().getSiglaPais();
        int codigo = this.produto.getId();
        //codigo = this.venda.
        //System.out.println(sigla);
        try {
            xdaoProduto = new ProdutoDao();
            temp = xdaoProduto.buscaPorCodigo(codigo);
            if (temp != null) {
                this.produto = temp;
                this.precoUnitario = this.produto.getPrecoVarejo();
                //System.out.println("achou o pais " + this.estado.getPais().getNomePais());
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto encontrado...", " "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Produto não existe....", ""));
                //System.out.println(" nao achou o pais ");
            }
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO NO TRY BUSCA PAIS", "Erro: "));
        }

    }

    public void inserirNaGrid() {
        DetalheVenda detalhe = new DetalheVenda();
        detalhe.setQuantidade(quantidade);
        detalhe.setProduto(produto);
        detalhe.setPreco(precoUnitario);
        this.listaDetalhe.add(detalhe);
        produto = new Produto();//acabei sem querer resolvendo o problema dos codigos repetidos na grid com esse comando
    }
    
    public void calcularTotalLinha(){
        //listaDetalhe.
    }
    
    public void removerDaGrid(DetalheVenda detalhe){
        this.listaDetalhe.remove(detalhe);
    }

}
