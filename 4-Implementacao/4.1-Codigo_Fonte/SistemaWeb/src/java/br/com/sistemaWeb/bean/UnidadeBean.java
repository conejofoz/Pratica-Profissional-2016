/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Unidade;
import br.com.sistemaWeb.dao.UnidadeDao;
import java.io.Serializable;
import java.sql.SQLException;
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
public class UnidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Unidade unidade = new Unidade();
    private List<Unidade> listaUnidade;
    private UnidadeDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Unidade unidadeSelecionado;
    private String consultaNome;

    public String getConsultaNome() {
        return consultaNome;
    }

    public void setConsultaNome(String consultaNome) {
        this.consultaNome = consultaNome;
    }
    
    
    
  
    
  //  private LogEvento logEvento = new LogEvento();

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();

  

   
 
    public Unidade getUnidadeSelecionado() {
        return unidadeSelecionado;
    }

    public void setUnidadeSelecionado(Unidade unidadeSelecionado) {
        this.unidadeSelecionado = unidadeSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public List<Unidade> getListaUnidade() {
        return listaUnidade;
    }

    public void setListaUnidade(List<Unidade> listaUnidade) {
        this.listaUnidade = listaUnidade;
    }

    public UnidadeDao geteDao() {
        return eDao;
    }

    public void seteDao(UnidadeDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new UnidadeDao();
        try {
            eDao.apagar(unidadeSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
        if (unidade != null) {
            eDao = new UnidadeDao();
            try {
                try {
                    if (!eDao.buscaPorNome(unidade)) {
                        //carregarLog();
                        eDao.salvar(unidade);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Unidade ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar unidade*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            unidade = new Unidade();
        }
        return retorno;
    }


    public void atualizar() {
        eDao = new UnidadeDao();
        try {
            eDao.atualizar(unidade);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        UnidadeDao dao;
        try {
            dao = new UnidadeDao();
            listaUnidade = dao.todosUnidadees();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        System.out.println("consultar");
        UnidadeDao dao;
        dao = new UnidadeDao();
        try {
            listaUnidade = dao.consultaUnidadees(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Unidade per) throws Exception {
        UnidadeDao dao;
        Unidade temp;
        try {
            dao = new UnidadeDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.unidade = temp;
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
                    if(this.salvar()){
                       this.limpiar();
                       fecharDialogo = true;
                    } else{
                        System.out.println("erro booooooo");
                    };
                    System.out.println("valor do fechardialogo" + fecharDialogo);
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
       // this.unidade.setId(0);
        this.unidade.setNomeUnidade("");

    }

    public void obterUnidade(Unidade xUnidade) {
        this.accion = "Modificar";
        this.unidade = xUnidade;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do unidade para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um unidade no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Unidade var_unidade) {
        if (var_unidade!=null){
            System.out.println("unidade chegou no selecionar");
        } else {
            System.out.println("unidade não chegou no selecionar");
        }
        
        try{
        RequestContext.getCurrentInstance().closeDialog(var_unidade);
        } catch(Exception e){
            System.out.println("erro ao selecionar unidade");
        }
    }

    public void abrirDialogoUnidade() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 400);
        opcoes.put("contentWidth", 700);
        //opcoes.put("widgetVar", "wconsultaUnidades");
        RequestContext.getCurrentInstance().openDialog("consultaUnidade2", opcoes, null);
        System.out.println("passou no dialogo do unidade");
    }
    
    public void abrirDialogoUnidade2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaUnidades2", opcoes, null);
        System.out.println("passou no dialogo do unidade2");
    }
    
    public void abrirDialogoUnidade3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroUnidadeesxxx", opcoes, null);
        System.out.println("passou no dialogo do unidade2");
    }

    public void handleKeyEvent() {
        unidade.setNomeUnidade(unidade.getNomeUnidade().toUpperCase());
    }
    
     public void siglaMaiuscula() {
        unidade.setSiglaUnidade(unidade.getSiglaUnidade().toUpperCase());
    }

   

    public void zerarUnidade() {
        unidade = new Unidade();
    }
    
 public void nãoFazNada(){
     System.out.println("não faz nada");
 }

    
}
