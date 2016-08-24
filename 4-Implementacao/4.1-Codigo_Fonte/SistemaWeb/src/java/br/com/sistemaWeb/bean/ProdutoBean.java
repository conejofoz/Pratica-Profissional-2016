/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Cfop;
import br.com.sistemaWeb.classes.Cst;
import br.com.sistemaWeb.classes.Fornecedor;
import br.com.sistemaWeb.classes.Grupo;
import br.com.sistemaWeb.classes.Marca;
import br.com.sistemaWeb.classes.Ncm;
import br.com.sistemaWeb.classes.Produto;
import br.com.sistemaWeb.classes.Unidade;
import br.com.sistemaWeb.dao.CfopDao;
import br.com.sistemaWeb.dao.CstDao;
import br.com.sistemaWeb.dao.FornecedorDao;
import br.com.sistemaWeb.dao.GrupoDao;
import br.com.sistemaWeb.dao.MarcaDao;
import br.com.sistemaWeb.dao.NcmDao;
import br.com.sistemaWeb.dao.ProdutoDao;
import br.com.sistemaWeb.dao.UnidadeDao;
import java.io.Serializable;
import java.sql.SQLException;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Produto produto = new Produto();
    private List<Produto> listaProduto;
    private ProdutoDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Produto produtoSelecionado;
    private String edtConsultaNome;
  //  private LogEvento logEvento = new LogEvento();

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();
    public String getEdtConsultaNome() {
        return edtConsultaNome;
    }

    public void setEdtConsultaNome(String edtConsultaNome) {
        this.edtConsultaNome = edtConsultaNome;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public ProdutoDao geteDao() {
        return eDao;
    }

    public void seteDao(ProdutoDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new ProdutoDao();
        try {
            eDao.apagar(produtoSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            try {
                this.listar();
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", "Problema ao listar depois de apagar"));
            }
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception {
        boolean retorno = false;
        if (produto != null) {
            eDao = new ProdutoDao();

            try {
                try {
                    if (!eDao.buscaPorNome(produto)) {
                        //carregarLog();
                        eDao.salvar(produto);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        produto = new Produto();
                        retorno = true;

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Produto ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no sql", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro: " + ex2.getMessage()));
                retorno = false;
            }

        }
        return retorno;
    }

    public void salvarAntigo() throws Exception {
        if (produto != null) {
            eDao = new ProdutoDao();
            try {

                eDao.salvar(produto);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            produto = new Produto();
        }
    }

    public void atualizar() {
        eDao = new ProdutoDao();
        try {
            eDao.atualizar(produto);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        ProdutoDao dao;
        try {
            dao = new ProdutoDao();
            listaProduto = dao.todosProdutos();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Produto per) throws Exception {
        ProdutoDao dao;
        Produto temp;
        try {
            dao = new ProdutoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.produto = temp;
                this.accion = "Modificar";
                System.out.println("achou o produto");
            } else {
                System.out.println("nao achou o produto");
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
                    if (this.salvar()) {
                        //this.limpiar();
                        fecharDialogo = true;
                    } else {
                        System.out.println("erro booooooo");
                    }
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
        this.produto.setId(0);
        this.produto.setNomeProduto("");
    }

    public void obterProduto(Produto xProduto) {
        this.accion = "Modificar";
        this.produto = xProduto;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do produto para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um produto no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Produto var_produto) {
        RequestContext.getCurrentInstance().closeDialog(var_produto);
    }

    public void abrirDialogoProduto() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 650);
        opcoes.put("contentWidth", 1100);
        //RequestContext.getCurrentInstance().openDialog("consultaProdutos", opcoes, null);
        RequestContext.getCurrentInstance().openDialog("consultaProdutos2", opcoes, null);
        System.out.println("passou");
    }

    public void handleKeyEvent() {
        produto.setNomeProduto(produto.getNomeProduto().toUpperCase());
    }

    public void setarGrupoSelecionado(SelectEvent event) {
        Grupo grupoNovo = (Grupo) event.getObject();
        grupoNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        produto.setGrupo(grupoNovo);
        //setGrupo(grupoNovo);
    }

    public void setarFornecedorSelecionado(SelectEvent event) {
        Fornecedor fornecedorNovo = (Fornecedor) event.getObject();
        fornecedorNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        produto.setFornecedor(fornecedorNovo);
        //setGrupo(grupoNovo);
    }

    public void setarMarcaSelecionada(SelectEvent event) {
        Marca marcaNova = (Marca) event.getObject();
        marcaNova.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        produto.setMarca(marcaNova);
        //setGrupo(grupoNovo);
    }

    public void setarUnidadeSelecionada(SelectEvent event) {
        Unidade unidadeNova = (Unidade) event.getObject();
        unidadeNova.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        produto.setUnidade(unidadeNova);
        //setGrupo(grupoNovo);
    }

    public Grupo buscaPorID2(Grupo per) throws Exception {
        GrupoDao dao;
        Grupo temp;
        try {
            dao = new GrupoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Grupo encontrado "));
                this.produto.setGrupo(temp);
                // this.accion = "Modificar";
                //System.out.println("codigo do cargo: " + funcionario.getCargo().getId());
                //System.out.println("Nome do cargo  : " + funcionario.getCargo().getNomeCargo());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Grupo n'ao existe "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }

    public Fornecedor buscaPorID3(Fornecedor fornecedor) throws Exception {
        FornecedorDao dao;
        Fornecedor temp;
        try {
            dao = new FornecedorDao();
            temp = dao.buscaPorID(fornecedor);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Fornecedor encontrado "));
                this.produto.setFornecedor(temp);
                // this.accion = "Modificar";
                //System.out.println("codigo do cargo: " + funcionario.getCargo().getId());
                //System.out.println("Nome do cargo  : " + funcionario.getCargo().getNomeCargo());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Fornecedor não encontrado "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }

    public Marca buscaMarcaPorId(Marca marca) throws Exception {
        MarcaDao dao;
        Marca temp;
        try {
            dao = new MarcaDao();
            temp = dao.buscaPorID(marca);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Marca encontrada "));
                this.produto.setMarca(temp);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Marca não encontrada "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }

    public Unidade buscaUnidadePorId(Unidade unidade) throws Exception {
        UnidadeDao dao;
        Unidade temp;
        try {
            dao = new UnidadeDao();
            temp = dao.buscaPorID(unidade);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "unidade encontrada "));
                this.produto.setUnidade(temp);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "unidade não encontrada "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }
    
    public Ncm buscaNcmPorId(Ncm ncm) throws Exception{
        NcmDao dao;
        Ncm temp;
        try {
            dao = new NcmDao();
            temp = dao.buscaPorID(ncm);
            if (temp !=null){
                this.produto.setNcm(temp);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso:", "Ncm não encontrado"));
            }
        } catch (Exception e){
            throw e;
        }
        return temp;
    }
    
    public Cst buscaCstPorId(Cst cst) throws Exception{
        CstDao dao;
        Cst temp;
        try {
            dao = new CstDao();
            temp = dao.buscaPorID(cst);
            if (temp !=null){
                this.produto.setCst(temp);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso:", "Cst não encontrado"));
            }
        } catch (Exception e){
            throw e;
        }
        return temp;
    }
    
    public Cfop buscaCfopPorId(Cfop cfop) throws Exception{
        CfopDao dao;
        Cfop temp;
        try {
            dao = new CfopDao();
            temp = dao.buscaPorID(cfop);
            if (temp !=null){
                this.produto.setCfop(temp);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso:", "Cfop não encontrado"));
            }
        } catch (Exception e){
            throw e;
        }
        return temp;
    }
    

    public void iniciarObjeto() {
        produto = new Produto();
    }

    public void consultar() throws Exception {
        ProdutoDao dao;
        dao = new ProdutoDao();
        try {
            listaProduto = dao.consultaProdutos(this.edtConsultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void limpar() {
        this.produto = new Produto();
    }
}
