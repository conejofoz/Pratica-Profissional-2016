/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Moeda;
import br.com.sistemaWeb.dao.MoedaDao;
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
public class MoedaBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Moeda moeda = new Moeda();
    private List<Moeda> listaMoeda;
    private MoedaDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Moeda moedaSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();

    public Moeda getMoedaSelecionado() {
        return moedaSelecionado;
    }

    public void setMoedaSelecionado(Moeda moedaSelecionado) {
        this.moedaSelecionado = moedaSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public List<Moeda> getListaMoeda() {
        return listaMoeda;
    }

    public void setListaMoeda(List<Moeda> listaMoeda) {
        this.listaMoeda = listaMoeda;
    }

    public MoedaDao geteDao() {
        return eDao;
    }

    public void seteDao(MoedaDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new MoedaDao();
            try {
                eDao.apagar(moedaSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public void salvar() {
        if (moeda != null) {
            eDao = new MoedaDao();
            
            try {
                try {
                    if (!eDao.buscaPorNome(moeda)) {
                        //carregarLog();
                        eDao.salvar(moeda);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Moeda ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            moeda = new Moeda();
        }
    }

    
    public void salvarAntigo() throws Exception {
        if (moeda != null) {
            eDao = new MoedaDao();
            try {
                
                eDao.salvar(moeda);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            moeda = new Moeda();
        }
    }
    
    
    public void atualizar() {
        eDao = new MoedaDao();
        try {
            eDao.atualizar(moeda);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        MoedaDao dao;
        try {
            dao = new MoedaDao();
            listaMoeda = dao.todosMoedas();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Moeda per) throws Exception {
        MoedaDao dao;
        Moeda temp;
        try {
            dao = new MoedaDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.moeda = temp;
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
                try{
                   this.salvar();
                   this.limpiar();
                   fecharDialogo = true;
                   System.out.println(fecharDialogo);
                }catch(Exception e){
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
        this.moeda.setId(0);
        this.moeda.setNomeMoeda("");
   
    }
    
    public void obterMoeda(Moeda xMoeda){
        this.accion = "Modificar";
        this.moeda = xMoeda;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do moeda para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um moeda no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    
public void selecionar(Moeda var_moeda){
    RequestContext.getCurrentInstance().closeDialog(var_moeda);
}    
    

   public void abrirDialogoMoeda(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaMoedas", opcoes, null);
       System.out.println("passou");
   } 
   
   public void handleKeyEvent() {
       moeda.setNomeMoeda(moeda.getNomeMoeda().toUpperCase());
    }
   
    public void handleKeyEventSiglaMoeda() {
       moeda.setSiglaMoeda(moeda.getSiglaMoeda().toUpperCase());
    }
   
  
   
   public void zerarMoeda(){
       moeda = new Moeda();
   }
    
}
