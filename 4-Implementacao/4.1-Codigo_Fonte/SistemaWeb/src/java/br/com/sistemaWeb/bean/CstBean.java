/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Cst;
import br.com.sistemaWeb.dao.CstDao;
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
public class CstBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Cst cst = new Cst();
    private List<Cst> listaCst;
    private CstDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Cst cstSelecionado;
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

  

   
 
    public Cst getCstSelecionado() {
        return cstSelecionado;
    }

    public void setCstSelecionado(Cst cstSelecionado) {
        this.cstSelecionado = cstSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Cst getCst() {
        return cst;
    }

    public void setCst(Cst cst) {
        this.cst = cst;
    }

    public List<Cst> getListaCst() {
        return listaCst;
    }

    public void setListaCst(List<Cst> listaCst) {
        this.listaCst = listaCst;
    }

    public CstDao geteDao() {
        return eDao;
    }

    public void seteDao(CstDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new CstDao();
        try {
            eDao.apagar(cstSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
        if (cst != null) {
            eDao = new CstDao();
            try {
                try {
                    if (!eDao.buscaPorNome(cst)) {
                        //carregarLog();
                        eDao.salvar(cst);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Cst ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar cst*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            cst = new Cst();
        }
        return retorno;
    }


    public void atualizar() {
        eDao = new CstDao();
        try {
            eDao.atualizar(cst);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        CstDao dao;
        try {
            dao = new CstDao();
            listaCst = dao.todosCstes();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        System.out.println("consultar");
        CstDao dao;
        dao = new CstDao();
        try {
            listaCst = dao.consultaCstes(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Cst per) throws Exception {
        CstDao dao;
        Cst temp;
        try {
            dao = new CstDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.cst = temp;
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
       // this.cst.setId(0);
        this.cst.setNomeCst("");

    }

    public void obterCst(Cst xCst) {
        this.accion = "Modificar";
        this.cst = xCst;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do cst para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um cst no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Cst var_cst) {
        if (var_cst!=null){
            System.out.println("cst chegou no selecionar");
        } else {
            System.out.println("cst não chegou no selecionar");
        }
        
        try{
        RequestContext.getCurrentInstance().closeDialog(var_cst);
        } catch(Exception e){
            System.out.println("erro ao selecionar cst");
        }
    }

    public void abrirDialogoCst() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 300);
        opcoes.put("contentWidth", 700);
        //opcoes.put("widgetVar", "wconsultaCsts");
        RequestContext.getCurrentInstance().openDialog("consultaCst2", opcoes, null);
        System.out.println("passou no dialogo do cst");
    }
    
    public void abrirDialogoCst2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaCsts2", opcoes, null);
        System.out.println("passou no dialogo do cst2");
    }
    
    public void abrirDialogoCst3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroCstesxxx", opcoes, null);
        System.out.println("passou no dialogo do cst2");
    }

    public void handleKeyEvent() {
        cst.setNomeCst(cst.getNomeCst().toUpperCase());
    }

   

    public void zerarCst() {
        cst = new Cst();
    }
    
 public void nãoFazNada(){
     System.out.println("não faz nada");
 }

    
}
