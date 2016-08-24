/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Cfop;
import br.com.sistemaWeb.dao.CfopDao;
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
public class CfopBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Cfop cfop = new Cfop();
    private List<Cfop> listaCfop;
    private CfopDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Cfop cfopSelecionado;
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

  

   
 
    public Cfop getCfopSelecionado() {
        return cfopSelecionado;
    }

    public void setCfopSelecionado(Cfop cfopSelecionado) {
        this.cfopSelecionado = cfopSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Cfop getCfop() {
        return cfop;
    }

    public void setCfop(Cfop cfop) {
        this.cfop = cfop;
    }

    public List<Cfop> getListaCfop() {
        return listaCfop;
    }

    public void setListaCfop(List<Cfop> listaCfop) {
        this.listaCfop = listaCfop;
    }

    public CfopDao geteDao() {
        return eDao;
    }

    public void seteDao(CfopDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new CfopDao();
        try {
            eDao.apagar(cfopSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
        if (cfop != null) {
            eDao = new CfopDao();
            try {
                try {
                    if (!eDao.buscaPorNome(cfop)) {
                        //carregarLog();
                        eDao.salvar(cfop);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Cfop ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar cfop*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            cfop = new Cfop();
        }
        return retorno;
    }


    public void atualizar() {
        eDao = new CfopDao();
        try {
            eDao.atualizar(cfop);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        CfopDao dao;
        try {
            dao = new CfopDao();
            listaCfop = dao.todosCfopes();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        System.out.println("consultar");
        CfopDao dao;
        dao = new CfopDao();
        try {
            listaCfop = dao.consultaCfopes(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Cfop per) throws Exception {
        CfopDao dao;
        Cfop temp;
        try {
            dao = new CfopDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.cfop = temp;
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
                try{
                this.atualizar();
                this.limpiar();
                fecharDialogo = true;
                break;
                }catch(Exception e1){
                    System.out.println("Erro ao atualizar cfop");
                }
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
       // this.cfop.setId(0);
        this.cfop.setNomeCfop("");

    }

    public void obterCfop(Cfop xCfop) {
        this.accion = "Modificar";
        this.cfop = xCfop;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do cfop para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um cfop no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Cfop var_cfop) {
        if (var_cfop!=null){
            System.out.println("cfop chegou no selecionar");
        } else {
            System.out.println("cfop não chegou no selecionar");
        }
        
        try{
        RequestContext.getCurrentInstance().closeDialog(var_cfop);
        } catch(Exception e){
            System.out.println("erro ao selecionar cfop");
        }
    }

    public void abrirDialogoCfop() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 300);
        opcoes.put("contentWidth", 700);
        //opcoes.put("widgetVar", "wconsultaCfops");
        RequestContext.getCurrentInstance().openDialog("consultaCfop2", opcoes, null);
        System.out.println("passou no dialogo do cfop");
    }
    
    public void abrirDialogoCfop2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaCfops2", opcoes, null);
        System.out.println("passou no dialogo do cfop2");
    }
    
    public void abrirDialogoCfop3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroCfopesxxx", opcoes, null);
        System.out.println("passou no dialogo do cfop2");
    }

    public void handleKeyEvent() {
        cfop.setNomeCfop(cfop.getNomeCfop().toUpperCase());
    }

   

    public void zerarCfop() {
        cfop = new Cfop();
    }
    
 public void nãoFazNada(){
     System.out.println("não faz nada");
 }

    
}
